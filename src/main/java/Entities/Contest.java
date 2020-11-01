package Entities;

import java.util.Date;
import java.text.DateFormatSymbols;

public class Contest {
    private String name;
    private Integer id;
    private String img;
    private String statement;
    private String href;
    private Date date;
    private String info;
    private Integer day;
    private String month;
    private String html_id;

    public String getHtml_id() {
        return html_id;
    }

    public void setHtml_id(String html_id) {
        this.html_id = html_id;
    }

    public Integer getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public void parsingDay(Date date){
        day = date.getDay();
    }

    public void parsingMonth(Date date){
        int m = date.getMonth();
        month = new DateFormatSymbols().getMonths()[m-1];
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public String getHref() {
        return href;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getStatement() {
        return statement;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setDate(Date date) {
        this.date = date;
        this.parsingDay(date);
        this.parsingMonth(date);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
