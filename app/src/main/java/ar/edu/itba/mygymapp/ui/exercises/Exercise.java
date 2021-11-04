package ar.edu.itba.mygymapp.ui.exercises;

public class Exercise {
    private String name, type, detail;

    public Exercise(String name, String type, String detail) {
        this.name = name;
        this.type = type;
        this.detail = detail;
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
}
