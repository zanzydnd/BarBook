package Entities;

import java.sql.Date;

public class Comment {

    private Cocktail cocktail;
    private User user;
    private Integer id;
    private String comm;
    private Date date;


    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getComm() {
        return comm;
    }
}
