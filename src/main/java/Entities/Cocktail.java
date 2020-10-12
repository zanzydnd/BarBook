package Entities;

public class Cocktail {
    private String img;
    private Integer id;
    private String name;
    private String inf;
    private int rating;
    private String recipie;


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
