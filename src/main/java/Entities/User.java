package Entities;

import java.util.List;

public class User {
    private int id;

    private String information;

    private String img;

    private Integer rating;

    private String login;

    private String email;

    private String name;

    private String password;

    private Integer favCockt;

    private List<Integer> participated;

    private List<Integer> wins;

    public void setInformation(String inf){information = inf;}

    public String getInformation(){return information;}

    public void addWin(Integer id) {
        wins.add(id);
    }

    public void addParticipaitng(Integer id) {
        participated.add(id);
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(int rate) {
        rating = rate;
    }

    public List<Integer> getParticipated() {
        return participated;
    }

    public void setParticipated(List<Integer> list) {
        participated = list;
    }

    public List<Integer> getWins() {
        return wins;
    }

    public void setWins(List<Integer> list) {
        wins = list;
    }

    public Integer getFavCockt() {
        return favCockt;
    }

    public void setFavCockt(int id) {
        favCockt = id;
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
