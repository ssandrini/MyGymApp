package ar.edu.itba.mygymapp.backend.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ar.edu.itba.mygymapp.backend.apimodels.Error;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ApiResponse<T> {

    private T data;
    private Error error;

    public T getData() {
        return data;
    }

    public Error getError() {
        return error;
    }

    public ApiResponse(Response<T> response) {
        parseResponse(response);
    }

    public ApiResponse(Throwable throwable) {
        this.error = buildError(throwable.getMessage());
    }

    private void parseResponse(Response<T> response) {
        if (response.isSuccessful()) {
            this.data = response.body();
            return;
        }

        if (response.errorBody() == null) {
            this.error = buildError("Missing error body");
            return;
        }

        String message;

        try {
            message = response.errorBody().string();
        } catch (IOException exception) {
            Log.e("API", exception.toString());
            this.error = buildError(exception.getMessage());
            return;
        }

        if (message != null && message.trim().length() > 0) {
            Gson gson = new Gson();
            this.error =  gson.fromJson(message, new TypeToken<Error>() {}.getType());
        }
    }

    private static Error buildError(String message) {
        Error error = new Error(Error.LOCAL_UNEXPECTED_ERROR, "Unexpected error");
        if (message != null) {
            List<String> details = new ArrayList<>();
            details.add(message);
            error.setDetails(details);
        }
        return error;
    }
}