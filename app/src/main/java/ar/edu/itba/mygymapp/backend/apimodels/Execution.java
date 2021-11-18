package ar.edu.itba.mygymapp.backend.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Execution {

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
    public Execution() {
    }

    /**
     *
     * @param duration
     * @param wasModified
     */
    public Execution(int duration, boolean wasModified) {
        super();
        this.duration = duration;
        this.wasModified = wasModified;
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
