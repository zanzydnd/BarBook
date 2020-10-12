package Entities;

public class Comment {

    private Cocktail cocktail;
    private User user;
    private Integer id;
    private String comm;

    public Comment() {
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
