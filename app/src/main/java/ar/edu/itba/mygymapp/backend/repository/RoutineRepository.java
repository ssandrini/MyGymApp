package ar.edu.itba.mygymapp.backend.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.api.ApiClient;
import ar.edu.itba.mygymapp.backend.api.ApiResponse;
import ar.edu.itba.mygymapp.backend.api.ApiRoutineService;
import ar.edu.itba.mygymapp.backend.apimodels.FullRoutine;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;
import ar.edu.itba.mygymapp.backend.models.Routine;

public class RoutineRepository {

    private final ApiRoutineService apiService;
    private ArrayList<Routine> cacheRoutines = new ArrayList<>();
    private ArrayList<Routine> favRoutines = new ArrayList<>();

    public RoutineRepository(App application) {
        this.apiService = ApiClient.create(application, ApiRoutineService.class);
    }

    public ArrayList<Routine> getCacheRoutines() {
        return this.cacheRoutines;
    }

    public void addCacheRoutine(Routine routine) {
        this.cacheRoutines.add(routine);
    }

    public Routine getCacheRoutineById(Integer id) {
        for (Routine r: cacheRoutines) {
            if(r.getId() == id)
                return r;
        }
        return null;
    }

    public ArrayList<Routine> getFavRoutines() {
        return favRoutines;
    }

    public void setFavRoutines(ArrayList<Routine> favRoutines) {
        this.favRoutines = favRoutines;
    }

    public void addFavRoutine(Routine routine) {
        this.favRoutines.add(routine);
    }

    public void removeFavRoutine(int routineId) {
        this.favRoutines.removeIf(r -> r.getId() == routineId);
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
