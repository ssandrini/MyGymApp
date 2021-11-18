package ar.edu.itba.mygymapp.backend.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;
import ar.edu.itba.mygymapp.backend.apimodels.Category;
import ar.edu.itba.mygymapp.backend.apimodels.PublicUser;

public class Routine implements Serializable {
    private int id;
    private String name;
    private String detail;
    private int score;
    private boolean isPublic;
    private String difficulty;
    private PublicUser user;
    private Category category;
    private Object metadata;
    private long date;
    private int[] duration;
    private String routineImageUrl;
    private ArrayList<Cycle> cycles;

    private static final Comparator<Routine> scoreComparatorAsc = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return routine.getScore().compareTo(t1.getScore());
        }
    };
    private static final Comparator<Routine> scoreComparatorDesc = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return t1.getScore().compareTo(routine.getScore());
        }
    };

    private static final Comparator<Routine> dateComparatorAsc = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return Long.compare(routine.getDate(), t1.getDate());
        }
    };
    private static final Comparator<Routine> dateComparatorDesc = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return Long.compare(t1.getDate(), routine.getDate());
        }
    };

    private static final Comparator<Routine> categoryComparatorAsc = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return routine.getCategory().compareTo(t1.getCategory());
        }
    };
    private static final Comparator<Routine> categoryComparatorDesc = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return t1.getCategory().compareTo(routine.getCategory());
        }
    };

    private static final Comparator<Routine> difficultyComparatorAsc = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return routine.mapDifficulty().compareTo(t1.mapDifficulty());
        }
    };
    private static final Comparator<Routine> difficultyComparatorDesc = new Comparator<Routine>() {
        @Override
        public int compare(Routine routine, Routine t1) {
            return t1.mapDifficulty().compareTo(routine.mapDifficulty());
        }
    };

    public static ArrayList<String> routineImages = new ArrayList<>();

    public Routine(int id, String name, String detail, int score,
                   boolean isPublic, String difficulty, PublicUser user,
                   Category category, Object metadata, long date) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.score = score;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.user = user;
        this.category = category;
        this.metadata = metadata;
        this.duration = new int[]{15, 10};
        this.routineImageUrl = routineImageUrl != null ? routineImageUrl : chooseImage();
        this.date = date;
    }

    public Routine(int id, String name, String detail, Integer score, boolean isPublic, String difficulty, PublicUser user, Category category, Object metadata,long date, boolean favourite, ArrayList<Cycle> cycles) {
        this(id, name, detail, score, isPublic, difficulty, user, category, metadata,date);
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

        routineImages.add("https://i.imgur.com/oASE3GD.png");
        routineImages.add("https://i.imgur.com/ai0es5T.png");
        routineImages.add("https://i.imgur.com/H9oKNDZ.png");
        routineImages.add("https://i.imgur.com/UHka8EZ.png");
        routineImages.add("https://i.imgur.com/UZaGvjH.png");
        routineImages.add("https://i.imgur.com/XlVqftC.png");
        routineImages.add("https://i.imgur.com/A7HOKir.png");
        routineImages.add("https://i.imgur.com/GCzizlz.png");
        Random random = new Random();
        int i = routineImages.size();

        return routineImages.get(random.nextInt(i));
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

    public void sortCycles() {
        this.cycles.sort(new Comparator<Cycle>() {
            @Override
            public int compare(Cycle c1, Cycle c2) {
                return c1.getId() - c2.getId();
            }
        });
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

    public void setScore(int score) {
        this.score = score;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public static Comparator<Routine> getScoreComparatorAsc() {
        return scoreComparatorAsc;
    }

    public static Comparator<Routine> getScoreComparatorDesc() {
        return scoreComparatorDesc;
    }

    public static Comparator<Routine> getDateComparatorAsc() {
        return dateComparatorAsc;
    }

    public static Comparator<Routine> getDateComparatorDesc() {
        return dateComparatorDesc;
    }

    public static Comparator<Routine> getCategoryComparatorAsc() {
        return categoryComparatorAsc;
    }

    public static Comparator<Routine> getCategoryComparatorDesc() {
        return categoryComparatorDesc;
    }

    public static Comparator<Routine> getDifficultyComparatorAsc() {
        return difficultyComparatorAsc;
    }

    public static Comparator<Routine> getDifficultyComparatorDesc() {
        return difficultyComparatorDesc;
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
                ", duration=" + Arrays.toString(duration) +
                ", cycles=" + cycles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Routine routine = (Routine) o;
        return id == routine.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
