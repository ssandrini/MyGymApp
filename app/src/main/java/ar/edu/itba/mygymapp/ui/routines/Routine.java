package ar.edu.itba.mygymapp.ui.routines;

import java.io.Serializable;
import java.util.Comparator;

public class Routine implements Serializable {
    private int id;
    private String name, detail, difficulty, user, category, image;
    private double score;
    private int[] duration;

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


}
