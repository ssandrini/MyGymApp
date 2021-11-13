package ar.edu.itba.mygymapp.backend.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import ar.edu.itba.mygymapp.backend.models.Routine;

public class FullRoutine {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("isPublic")
    @Expose
    private boolean isPublic;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("user")
    @Expose
    private PublicUser publicUser;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("metadata")
    @Expose
    private Object metadata;

    /**
     * No args constructor for use in serialization
     *
     */
    public FullRoutine() {
    }

    /**
     *
     * @param date
     * @param difficulty
     * @param metadata
     * @param score
     * @param name
     * @param isPublic
     * @param id
     * @param detail
     * @param category
     * @param publicUser
     */
    public FullRoutine(int id, String name, String detail, long date, int score, boolean isPublic, String difficulty, PublicUser publicUser, Category category, Object metadata) {
        super();
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.date = date;
        this.score = score;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.publicUser = publicUser;
        this.category = category;
        this.metadata = metadata;
    }


    public int getId() {
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public PublicUser getPublicUser() {
        return publicUser;
    }

    public void setPublicUser(PublicUser publicUser) {
        this.publicUser = publicUser;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }



    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullRoutine that = (FullRoutine) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Routine toRoutine() {
        return new Routine(this.getId(),this.getName(), this.getDetail(), this.getScore(),
                this.isIsPublic(), this.getDifficulty(), this.getPublicUser(),
                this.getCategory(), this.getMetadata());
    }
}