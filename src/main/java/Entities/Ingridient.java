package Entities;

public class Ingridient {
    private String img;
    private Integer id;
    private String name;
    private String inf;

    public Ingridient(){
    }

    public Ingridient(int id , String name, String inf, String img){
        this.id = id;
        this.name = name;
        this.inf = inf;
        this.img = img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg(){
        return img;
    }

    public Integer getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setInf(String inf){
        this.inf = inf;
    }

    public String getName(){
        return name;
    }

    public String getInf(){
        return inf;
    }
}
