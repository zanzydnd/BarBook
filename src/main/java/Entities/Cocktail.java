package Entities;

import java.util.List;

public class Cocktail {
    private String img;
    private Integer id;
    private String name;
    private String inf;
    private int rating;
    private String recipie;
    private String smallImg;
    private List<Ingridient> ingridients;
    private String content_type;
    private Integer author_id;

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public List<Ingridient> getIngridients(){
        return ingridients;
    }

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public void setIngridients(List<Ingridient> list){
        ingridients = list;
    }

    public String getRecipie() {
        return recipie;
    }

    public void setRecipie(String rec) {
        this.recipie = rec;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rate) {
        rating = rate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInf(String inf) {
        this.inf = inf;
    }

    public String getInf() {
        return inf;
    }
}
