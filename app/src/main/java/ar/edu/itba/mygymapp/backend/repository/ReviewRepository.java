package ar.edu.itba.mygymapp.backend.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.api.ApiClient;
import ar.edu.itba.mygymapp.backend.api.ApiResponse;
import ar.edu.itba.mygymapp.backend.api.ApiReviewService;
import ar.edu.itba.mygymapp.backend.apimodels.Review;

public class ReviewRepository {

    private final ApiReviewService apiService;

    public ReviewRepository(App application) {
        this.apiService = ApiClient.create(application, ApiReviewService.class);
    }

    public LiveData<Resource<Void>> addReview(int routineId, Review review) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.addReview(routineId, review);
            }
        }.asLiveData();
    }

}
