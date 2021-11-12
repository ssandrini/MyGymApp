package ar.edu.itba.mygymapp.backend.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.api.ApiClient;
import ar.edu.itba.mygymapp.backend.api.ApiExerciseImageService;
import ar.edu.itba.mygymapp.backend.api.ApiResponse;
import ar.edu.itba.mygymapp.backend.apimodels.FullImage;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;

public class ExerciseImageRepository {

    private final ApiExerciseImageService apiService;

    public ExerciseImageRepository(App application) {
        this.apiService = ApiClient.create(application, ApiExerciseImageService.class);
    }

    public LiveData<Resource<PagedList<FullImage>>> getExerciseImages(int exerciseId) {
        return new NetworkBoundResource<PagedList<FullImage>, PagedList<FullImage>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullImage>>> createCall() {
                return apiService.getExerciseImages(exerciseId);
            }
        }.asLiveData();
    }

}
