package ar.edu.itba.mygymapp.backend.models;

import java.util.ArrayList;

public class Cycle {

    private int id;
    private String name;
    private String detail;
    private String type;
    private int order;
    private int repetitions;
    private Object metadata;
    private ArrayList<CycleExercise> exercises;
    private boolean isExpanded;

    public Cycle(int id, String name, String detail, String type, int order, int repetitions, Object metadata) {
        this(id, name, detail, type, order, repetitions, metadata, new ArrayList<CycleExercise>());
    }

    public Cycle(int id, String name, String detail, String type, int order, int repetitions, Object metadata, ArrayList<CycleExercise> exercises) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.order = order;
        this.repetitions = repetitions;
        this.metadata = metadata;
        this.exercises = exercises;
        this.isExpanded = false;
    }

    public ArrayList<CycleExercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<CycleExercise> exercises) {
        this.exercises = exercises;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

}