package ar.edu.itba.mygymapp.backend.models;

import java.util.Comparator;

public class Category implements Comparable<Category> {

    private int id;
    private String name;
    private String detail;

    public Category(int id, String name, String detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
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

    @Override
    public int compareTo(Category category) {
        return this.getName().compareTo(category.getName());
    }
}
