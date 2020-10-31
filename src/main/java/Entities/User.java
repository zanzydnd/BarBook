package Entities;

import java.util.List;

public class User {
    private Integer id;

    private String information;

    private String img;

    private String login;

    private String email;

    private String name;

    private String password;

    private List<Cocktail> favCocktails;

    private List<Cocktail> createdCocktails;

    public List<Cocktail> getCreatedCocktails() {
        return createdCocktails;
    }

    public void setCreatedCocktails(List<Cocktail> createdCocktails) {
        this.createdCocktails = createdCocktails;
    }

    public List<Cocktail> getFavCocktails() {
        return favCocktails;
    }

    public void setFavCocktails(List<Cocktail> favCocktails) {
        this.favCocktails = favCocktails;
    }

    public void setInformation(String inf){information = inf;}

    public String getInformation(){return information;}

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
