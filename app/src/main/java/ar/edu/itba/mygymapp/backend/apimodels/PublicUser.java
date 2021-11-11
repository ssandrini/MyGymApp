package ar.edu.itba.mygymapp.backend.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PublicUser {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("gender\"")
    @Expose
    private String gender;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("lastActivity")
    @Expose
    private long lastActivity;

    /**
     * No args constructor for use in serialization
     *
     */
    public PublicUser() {
    }

    /**
     *
     * @param date
     * @param gender
     * @param avatarUrl
     * @param lastActivity
     * @param id
     * @param username
     */
    public PublicUser(int id, String username, String gender, String avatarUrl, long date, long lastActivity) {
        super();
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.date = date;
        this.lastActivity = lastActivity;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
    }

}