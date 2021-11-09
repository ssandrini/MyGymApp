package ar.edu.itba.mygymapp.ui.routines;

import java.io.Serializable;
import java.util.Comparator;

public class Routine implements Serializable {
    private String name, detail, score, difficulty, user, category, image;

    public Routine(String name, String score, String user, String image) {
        this.name = name;
        this.score = score;
        this.user = user;
        this.image = image;
    }

    private static Comparator<Routine> scoreComparator = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            Double r1Score = Double.parseDouble(routine.getScore());
            Double r2Score = Double.parseDouble(t1.getScore());
            return r1Score.compareTo(r2Score);
        }
    };

    public static Comparator<Routine> getScoreComparator() {
        return scoreComparator;
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
