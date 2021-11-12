package ar.edu.itba.mygymapp.backend.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.mygymapp.backend.apimodels.Review;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiReviewService {

    @POST("reviews/{routineId}/")
    LiveData<ApiResponse<Void>> addReview(@Path("routineId") int routineId, @Body Review review);
}
