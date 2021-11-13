package ar.edu.itba.mygymapp.backend.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.api.ApiClient;
import ar.edu.itba.mygymapp.backend.api.ApiCycleService;
import ar.edu.itba.mygymapp.backend.api.ApiResponse;
import ar.edu.itba.mygymapp.backend.apimodels.FullCycle;
import ar.edu.itba.mygymapp.backend.apimodels.FullCycleExercise;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;
import retrofit2.http.Path;

public class CycleRepository {

    private final ApiCycleService apiService;

    public CycleRepository(App application) {
        this.apiService = ApiClient.create(application, ApiCycleService.class);
    }


    public LiveData<Resource<PagedList<FullCycle>>> getCycles(int routineId) {
        return new NetworkBoundResource<PagedList<FullCycle>, PagedList<FullCycle>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullCycle>>> createCall() {
                return apiService.getCycles(routineId);
            }
        }.asLiveData();
    }


    public LiveData<Resource<PagedList<FullCycle>>> getCycles(int routineId,
                                                              int page,
                                                              int size,
                                                              String orderBy,
                                                              String direction) {
        return new NetworkBoundResource<PagedList<FullCycle>, PagedList<FullCycle>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullCycle>>> createCall() {
                Map<String, String> options = new LinkedHashMap<>();
                options.put("page", String.valueOf(page));
                options.put("size", String.valueOf(size));
                options.put("orderBy", orderBy);
                options.put("direction", direction);
                return apiService.getCycles(routineId, options);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<FullCycleExercise>>> getCycleExercises(int cycleId) {
        return new NetworkBoundResource<PagedList<FullCycleExercise>, PagedList<FullCycleExercise>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullCycleExercise>>> createCall() {
                return apiService.getCycleExercises(cycleId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<FullCycleExercise>>> getCycleExercises(int cycleId,
                                                                              int page,
                                                                              int size,
                                                                              String orderBy,
                                                                              String direction) {
        return new NetworkBoundResource<PagedList<FullCycleExercise>, PagedList<FullCycleExercise>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullCycleExercise>>> createCall() {
                Map<String, String> options = new LinkedHashMap<>();
                options.put("page", String.valueOf(page));
                options.put("size", String.valueOf(size));
                options.put("orderBy", orderBy);
                options.put("direction", direction);
                return apiService.getCycleExercises(cycleId, options);
            }
        }.asLiveData();
    }

}
