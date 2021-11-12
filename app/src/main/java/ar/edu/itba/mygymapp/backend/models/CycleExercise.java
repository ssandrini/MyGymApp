package ar.edu.itba.mygymapp.backend.models;

public class CycleExercise {
    private String name, type, detail;
    private boolean isExpanded = false;

    private int id;
    private int order;
    private int duration;
    private int repetitions;
    private Object metadata;

    public CycleExercise(String name, String type, String detail, int id, int order, int duration, int repetitions, Object metadata) {
        this.name = name;
        this.type = type;
        this.detail = detail;
        this.id = id;
        this.order = order;
        this.duration = duration;
        this.repetitions = repetitions;
        this.metadata = metadata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    @Override
    public String toString() {
        return "CycleExercise{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", detail='" + detail + '\'' +
                ", isExpanded=" + isExpanded +
                ", id=" + id +
                ", order=" + order +
                ", duration=" + duration +
                ", repetitions=" + repetitions +
                ", metadata=" + metadata +
                '}';
    }
}
