package ar.edu.itba.mygymapp;

public class Routine {
    private String name, detail, score, difficulty, user, category, image;

    public Routine(String name, String score, String user, String image) {
        this.name = name;
        this.score = score;
        this.user = user;
        this.image = image;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Routine{" +
                "name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", score='" + score + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", user='" + user + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
