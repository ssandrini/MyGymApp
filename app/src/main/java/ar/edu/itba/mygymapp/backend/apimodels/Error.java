package ar.edu.itba.mygymapp.backend.apimodels;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {

    public static final int LOCAL_UNEXPECTED_ERROR = 10;

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("details")
    @Expose
    private List<String> details = null;

    public Error()  {
    }

    public Error(Integer code, String description) {
        this(code, description, null);
    }

    public Error(Integer code, String description, List<String> details) {
        this.code = code;
        this.description = description;
        this.details = details;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}