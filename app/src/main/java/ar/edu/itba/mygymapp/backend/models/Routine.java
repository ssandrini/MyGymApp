package ar.edu.itba.mygymapp.backend.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import ar.edu.itba.mygymapp.backend.apimodels.Category;
import ar.edu.itba.mygymapp.backend.apimodels.PublicUser;

public class Routine implements Serializable {
    private int id;
    private String name;
    private String detail;
    private Integer score;
    private boolean isPublic;
    private String difficulty;
    private PublicUser user;
    private Category category;
    private Object metadata;
    private boolean favourite;
    private int[] duration;
    private String routineImageUrl;
    private ArrayList<Cycle> cycles;

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

    public static ArrayList<String> routineImages = new ArrayList<>();

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

    public Routine(int id, String name, String detail, Integer score, boolean isPublic, String difficulty, PublicUser user, Category category, Object metadata, boolean favourite) {
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
        this.duration = new int[]{15, 10};
        this.routineImageUrl = chooseImage();

    }

    public Routine(int id, String name, String detail, Integer score, boolean isPublic, String difficulty, PublicUser user, Category category, Object metadata, boolean favourite, ArrayList<Cycle> cycles) {
        this(id, name, detail, score, isPublic, difficulty, user, category, metadata, favourite);
        this.cycles = cycles;
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

    private String chooseImage() {
        routineImages.add("https://imgur.com/oASE3GD");
        routineImages.add("https://i.imgur.com/oASE3GD.png");
        routineImages.add("https://i.imgur.com/ai0es5T.png");
        routineImages.add("https://i.imgur.com/H9oKNDZ.png");
        routineImages.add("https://i.imgur.com/UHka8EZ.png");
        routineImages.add("https://i.imgur.com/UZaGvjH.png");
        Random random = new Random();
        return routineImages.get(random.nextInt(routineImages.size()));
    }

    public String getRoutineImageUrl() {
        return routineImageUrl;
    }

    public void setRoutineImageUrl(String routineImageUrl) {
        this.routineImageUrl = routineImageUrl;
    }

    public static ArrayList<String> getRoutineImages() {
        return routineImages;
    }

    public static void setRoutineImages(ArrayList<String> routineImages) {
        Routine.routineImages = routineImages;
    }

    public ArrayList<Cycle> getCycles() {
        return cycles;
    }

    public void setCycles(ArrayList<Cycle> cycles) {
        this.cycles = cycles;
    }

    public void addCycle(Cycle cycle) {
        this.cycles.add(cycle);
    }

    public String getDurationStr() {
        int baseDuration = getDuration()[0];
        int endDuration = baseDuration + 10;
        StringBuilder res = new StringBuilder();
        res.append(baseDuration);
        res.append(" - ");
        res.append(endDuration);
        res.append(" min");
        return res.toString();
    }

    public int[] getDuration() {

        return duration;
    }

    public void setDuration(int[] duration) {
        this.duration = duration;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
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

    public PublicUser getUser() {
        return user;
    }

    public void setUser(PublicUser user) {
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

    @Override
    public String toString() {
        return "Routine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", score=" + score +
                ", isPublic=" + isPublic +
                ", difficulty='" + difficulty + '\'' +
                ", user=" + user +
                ", category=" + category +
                ", metadata=" + metadata +
                ", favourite=" + favourite +
                ", duration=" + Arrays.toString(duration) +
                ", cycles=" + cycles +
                '}';
    }
}
