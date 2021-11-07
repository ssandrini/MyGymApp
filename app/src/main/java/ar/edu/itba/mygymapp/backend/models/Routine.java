package ar.edu.itba.mygymapp.backend.models;

import java.util.Comparator;

public class Routine {
    private int id;
    private String name;
    private String detail;
    private Double score;
    private boolean isPublic;
    private String difficulty;
    private User user;
    private Category category;
    private Object metadata;
    private boolean favourite;
    private static final Comparator<Routine> scoreComparator = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return routine.getScore().compareTo(t1.getScore());
        }
    };
    private static final Comparator<Routine> dateComparator = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return 0;
        }
    };
    private static final Comparator<Routine> categoryComparator = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return routine.getCategory().compareTo(t1.getCategory());
        }
    };
    private static final Comparator<Routine> difficultyComparator = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return routine.mapDifficulty().compareTo(t1.mapDifficulty());
        }
    };

    public static Comparator<Routine> getScoreComparator() {
        return scoreComparator;
    }

    public static Comparator<Routine> getDateComparator() {
        return dateComparator;
    }

    public static Comparator<Routine> getDifficultyComparator() {
        return difficultyComparator;
    }

    public static Comparator<Routine> getCategoryComparator() {
        return categoryComparator;
    }

    public Routine(int id, String name, String detail, Double score, boolean isPublic, String difficulty, User user, Category category, Object metadata, boolean favourite) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.score = score;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.user = user;
        this.category = category;
        this.metadata = metadata;
        this.favourite = favourite;
    }

    public Integer mapDifficulty() {
        switch (this.getDifficulty()) {
            case "rookie":
                return 1;
            case "beginner":
                return 2;
            case "intermediate":
                return 3;
            case "advanced":
                return 4;
            case "expert":
                return 5;
            default:
                return 0;
        }
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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
