package ar.edu.itba.mygymapp.ui.cycles;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.ui.exercises.Exercise;

public class Cycle {
    private String name, detail, type;
    private ArrayList<Exercise> exercises;
    private boolean isExpanded;

    public Cycle(String name, ArrayList<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
        this.isExpanded = false;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }
}
