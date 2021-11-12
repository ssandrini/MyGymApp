package ar.edu.itba.mygymapp.backend.apimodels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullUser {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthdate")
    @Expose
    private long birthdate;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("metadata")
    @Expose
    private Object metadata;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("lastActivity")
    @Expose
    private long lastActivity;
    @SerializedName("verified")
    @Expose
    private boolean verified;

    /**
     * No args constructor for use in serialization
     *
     */
    public FullUser() {
    }

    /**
     *
     * @param date
     * @param lastName
     * @param metadata
     * @param birthdate
     * @param gender
     * @param avatarUrl
     * @param verified
     * @param firstName
     * @param phone
     * @param lastActivity
     * @param id
     * @param email
     * @param username
     */
    public FullUser(int id, String username, String firstName, String lastName, String gender, long birthdate, String email, String phone, String avatarUrl, Object metadata, long date, long lastActivity, boolean verified) {
        super();
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.metadata = metadata;
        this.date = date;
        this.lastActivity = lastActivity;
        this.verified = verified;
        /*
        StringBuilder s = new StringBuilder();
        s.append("https://ui-avatars.com/api/?name=");
        s.append(firstName);
        s.append("+");
        s.append(lastName);
        this.avatarUrl = s.toString();
         */
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
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

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

}