package ar.edu.itba.mygymapp.backend.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullExecution {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("wasModified")
    @Expose
    private boolean wasModified;

    /**
     * No args constructor for use in serialization
     *
     */
    public FullExecution() {
    }

    /**
     *
     * @param date
     * @param duration
     * @param wasModified
     * @param id
     */
    public FullExecution(int id, long date, int duration, boolean wasModified) {
        super();
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.wasModified = wasModified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isWasModified() {
        return wasModified;
    }

    public void setWasModified(boolean wasModified) {
        this.wasModified = wasModified;
    }

}