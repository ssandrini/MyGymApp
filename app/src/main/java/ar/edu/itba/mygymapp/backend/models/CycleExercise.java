package ar.edu.itba.mygymapp.backend.models;

public class CycleExercise {
    // debe ser comparble? creo que si.
    private int id;
    private int order;
    private int duration;
    private int repetitions;
    private Object metadata;

    private int set;

    public CycleExercise(int id, int order, int duration, int repetitions, Object metadata) {
        this.id = id;
        this.order = order;
        this.duration = duration;
        this.repetitions = repetitions;
        this.metadata = metadata;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

}
