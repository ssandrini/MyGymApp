package ar.edu.itba.mygymapp.backend.models;

public class User {
    private int id;
    private String username;
    private String gender;
    private String avatarUrl;

    public User(int id, String username, String gender, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
