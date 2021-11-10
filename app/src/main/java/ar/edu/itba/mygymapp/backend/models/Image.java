package ar.edu.itba.mygymapp.backend.models;

public class Image {
    
    private int id;
    private int number;
    private String url;

    public Image(int id, int number, String url) {
        this.id = id;
        this.number = number;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}