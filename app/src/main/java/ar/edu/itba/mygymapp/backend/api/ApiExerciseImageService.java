package ar.edu.itba.mygymapp.backend.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.mygymapp.backend.apimodels.FullImage;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiExerciseImageService {
    @GET("exercises/{exerciseId}/images")
    LiveData<ApiResponse<PagedList<FullImage>>> getExerciseImages(@Path("exerciseId") int exerciseId);
}
