package ORM;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DB {
    public interface Param {
        void set(PreparedStatement preparedStatement, int index) throws SQLException;
    }

    public static abstract class FN<I, O> {
        public abstract O apply(I input) throws Exception;

        public static <I, O> FN<I, O> constant(final O constValue) {
            return new FN<I, O>() {
                public O apply(I input) throws Exception {
                    return constValue;
                }
            };
        }
    }

    public static Param paramDate(final Date value) {
        return new Param() {
            public void set(PreparedStatement preparedStatement, int index) throws SQLException {
                if (value != null) preparedStatement.setDate(index, value);
                else preparedStatement.setNull(index, Types.DATE);
            }
        };
    }

    public static Param paramTime(final Time value) {
        return new Param() {
            public void set(PreparedStatement preparedStatement, int index) throws SQLException {
                if (value != null) preparedStatement.setTime(index, value);
                else preparedStatement.setNull(index, Types.TIME);

            }
        };
    }

    public static Param paramTimestamp(final Timestamp value) {
        return new Param() {
            public void set(PreparedStatement preparedStatement, int index) throws SQLException {
                if (value != null) preparedStatement.setTimestamp(index, value);
                else preparedStatement.setNull(index, Types.TIMESTAMP);
            }
        };
    }

    public static Param paramString(final String value) {
        return new Param() {
            public void set(PreparedStatement preparedStatement, int index) throws SQLException {
                preparedStatement.setString(index, value);
            }
        };
    }

    public static Param paramInteger(final Integer value) {
        return new Param() {
            public void set(PreparedStatement preparedStatement, int index) throws SQLException {
                if (value != null) preparedStatement.setInt(index, value);
                else preparedStatement.setNull(index, Types.INTEGER);
            }
        };
    }

    public static Param paramLong(final Long value) {
        return new Param() {
            public void set(PreparedStatement preparedStatement, int index) throws SQLException {
                if (value != null) preparedStatement.setLong(index, value);
                else preparedStatement.setNull(index, Types.BIGINT);
            }
        };
    }

    public static Param paramShort(final Short value) {
        return new Param() {
            public void set(PreparedStatement preparedStatement, int index) throws SQLException {
                if (value != null) preparedStatement.setShort(index, value);
                else preparedStatement.setNull(index, Types.SMALLINT);
            }
        };
    }

    public static Param paramByte(final Byte value) {
        return new Param() {
            public void set(PreparedStatement preparedStatement, int index) throws SQLException {
                if (value != null) preparedStatement.setByte(index, value);
                else preparedStatement.setNull(index, Types.TINYINT);
            }
        };
    }

    public static Param paramXML(final String value) {
        return new Param() {
            SQLXML sqlxml = null;

            public void set(PreparedStatement preparedStatement, int index) throws SQLException {
                if (value != null) {
                    if (sqlxml == null) {
                        sqlxml = preparedStatement.getConnection().createSQLXML();
                        sqlxml.setString(value);
                    }
                    preparedStatement.setSQLXML(index, sqlxml);
                } else {
                    preparedStatement.setNull(index, Types.SQLXML);
                }
            }
        };
    }

    public static Param paramXML(final SQLXML value) {
        return new Param() {
            public void set(PreparedStatement preparedStatement, int index) throws SQLException {
                if (value == null) preparedStatement.setNull(index, Types.SQLXML);
                else preparedStatement.setSQLXML(index, value);
            }
        };
    }


    private final static Pattern INDEX_PATTERN = Pattern.compile("((?<!\\{)\\{)(\\d+)(\\}(?!\\}))");
    private final static Pattern ESCAPE_OPEN = Pattern.compile("\\{\\{");
    private final static Pattern ESCAPE_CLOSE = Pattern.compile("}}", Pattern.LITERAL);


    private static String object2xml(Object obj) {
        StringWriter stringWriter = new StringWriter();
        try {
            try {
                JAXB.marshal(obj, stringWriter);
            } finally {
                stringWriter.close();
            }
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    private final static Map<Class, FN> paramFactories = new ConcurrentHashMap<Class, FN>() {
        {
            put(SQLXML.class, new FN<SQLXML, Param>() {
                public Param apply(SQLXML input) throws Exception {
                    return paramXML(input);
                }
            });
            put(Collection.class, new FN<Collection, Param>() {
                public Param apply(Collection input) throws Exception {
                    return paramXML(object2xml(input.toArray()));
                }
            });
            put(String.class, new FN<String, Param>() {
                public Param apply(String input) throws Exception {
                    return paramString(input);
                }
            });
            put(java.sql.Date.class, new FN<java.sql.Date, Param>() {
                public Param apply(java.sql.Date input) throws Exception {
                    return paramDate(input);
                }
            });
            put(Time.class, new FN<Time, Param>() {
                public Param apply(Time input) throws Exception {
                    return paramTime(input);
                }
            });
            put(Timestamp.class, new FN<Timestamp, Param>() {
                public Param apply(Timestamp input) throws Exception {
                    return paramTimestamp(input);
                }
            });
            put(Boolean.class, emptyFN);
            put(Long.class, emptyFN);
            put(Integer.class, emptyFN);
            put(Short.class, emptyFN);
            put(byte.class, emptyFN);
        }
    };

    public static <E> void registerParamFactory(Class<E> forClass, FN<E, Param> factory) {
        paramFactories.put(forClass, factory);
        for (Map.Entry<Class, FN> pair : paramFactories.entrySet()) {
            if (pair.getValue() == emptyFN) pair.setValue(null);
        }
    }

    private static FN getParamFactory(Class paramClass) {
        FN v = getParamFactory(paramClass, paramClass);
        if (v != null) return v;
        paramFactories.put(paramClass, emptyFN);
        return emptyFN;
    }

    private static FN getParamFactory(Class paramClass, Class targetClass) {
        FN val;
        for (Class current = paramClass; current != Object.class && current != null; current = current.getSuperclass()) {
            val = paramFactories.get(current);
            if (val != null) {
                if (current != targetClass) paramFactories.put(targetClass, val);
                return val;
            }
            for (Class intf : current.getInterfaces()) {
                val = getParamFactory(intf, targetClass);
                if (val != null) return val;
            }
        }
        return null;
    }


    private static final FN emptyFN = new FN() {
        public Object apply(Object input) throws Exception {
            return null;
        }
    };

    @SuppressWarnings("unchecked")
    public static PreparedStatement prepareStatement(final Connection connection, final CharSequence query, final Object... args) throws SQLException {
        int argc = args.length;
        String[] strings = new String[argc];
        Param[] params = new Param[argc];
        Object[] indexes = new Object[argc];

        for (int i = 0; i < argc; ++i) {
            Object arg = args[i];
            if (null == arg) {
                strings[i] = "NULL";
            } else if (arg instanceof Param) {
                params[i] = (Param) arg;
            } else {
                Class paramClass = arg.getClass();
                FN factory = getParamFactory(paramClass);
                if (factory != null && factory != emptyFN) {
                    try {
                        params[i] = (Param) factory.apply(arg);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    if (arg instanceof Boolean) {
                        strings[i] = ((Boolean) arg) ? "1" : "0";
                    } else if (arg instanceof Integer) {
                        strings[i] = Integer.toString((Integer) arg);
                    } else if (arg instanceof Long) {
                        strings[i] = Long.toString((Long) arg);
                    } else if (arg instanceof Short) {
                        strings[i] = Short.toString((Short) arg);
                    } else if (arg instanceof Byte) {
                        strings[i] = Byte.toString((Byte) arg);
                    } else {
                        params[i] = paramXML(object2xml(arg));
                    }
                }
            }
        }

        Matcher matcher = INDEX_PATTERN.matcher(query);
        StringBuffer stringBuffer = new StringBuffer();
        int index = 0;
        while (matcher.find()) {
            ++index;
            int reference = Integer.parseInt(matcher.group(2));
            if (strings[reference] != null) {
                matcher.appendReplacement(stringBuffer, strings[reference]);
            } else {
                List<Integer> lst = (List<Integer>) (null == indexes[reference] ? (indexes[reference] = new ArrayList<Integer>()) : indexes[reference]);
                lst.add(index);
                matcher.appendReplacement(stringBuffer, "?");
            }
        }
        matcher.appendTail(stringBuffer);
        String sql = ESCAPE_CLOSE.matcher(ESCAPE_OPEN.matcher(stringBuffer).replaceAll("{")).replaceAll("}");

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < argc; ++i) {
            List<Integer> lst = (List<Integer>) indexes[i];
            if (lst == null) continue;
            Param param = params[i];
            for (Integer pos : lst)
                param.set(preparedStatement, pos);
        }

        return preparedStatement;
    }

    public static ResultSet executeQuery(final Connection connection, final CharSequence query, final Object... args) throws SQLException {
        PreparedStatement preparedStatement = prepareStatement(connection, query, args);
        try {
            return preparedStatement.executeQuery();
        } finally {
            preparedStatement.close();
        }
    }

    public static boolean execute(final Connection connection, final CharSequence query, final Object... args) throws SQLException {
        PreparedStatement preparedStatement = prepareStatement(connection, query, args);
        try {
            return preparedStatement.execute();
        } finally {
            preparedStatement.close();
        }
    }

    public static int executeUpdate(final Connection connection, final CharSequence query, final Object... args) throws SQLException {
        PreparedStatement preparedStatement = prepareStatement(connection, query, args);
        try {
            return preparedStatement.executeUpdate();
        } finally {
            preparedStatement.close();
        }
    }
}