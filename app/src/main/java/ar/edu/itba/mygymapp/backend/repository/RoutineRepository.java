package ar.edu.itba.mygymapp.backend.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.api.ApiClient;
import ar.edu.itba.mygymapp.backend.api.ApiResponse;
import ar.edu.itba.mygymapp.backend.api.ApiRoutineService;
import ar.edu.itba.mygymapp.backend.apimodels.FullRoutine;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;

public class RoutineRepository {

    private final ApiRoutineService apiService;

    public RoutineRepository(App application) {
        this.apiService = ApiClient.create(application, ApiRoutineService.class);
    }

    public LiveData<Resource<PagedList<FullRoutine>>> getRoutines() {
        return new NetworkBoundResource<PagedList<FullRoutine>, PagedList<FullRoutine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullRoutine>>> createCall() {
                return apiService.getRoutines();
            }
        }.asLiveData();
    }


    public LiveData<Resource<PagedList<FullRoutine>>> getRoutinesByDiff(String difficulty) {
        return new NetworkBoundResource<PagedList<FullRoutine>, PagedList<FullRoutine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullRoutine>>> createCall() {
                return apiService.getRoutinesByDiff(difficulty);
            }
        }.asLiveData();
    }

    public LiveData<Resource<FullRoutine>> getRoutine(int routineId) {
        return new NetworkBoundResource<FullRoutine, FullRoutine>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<FullRoutine>> createCall() {
                return apiService.getRoutine(routineId);
            }
        }.asLiveData();
    }


}
