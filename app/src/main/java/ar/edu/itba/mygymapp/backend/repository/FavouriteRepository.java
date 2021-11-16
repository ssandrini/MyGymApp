package ar.edu.itba.mygymapp.backend.repository;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.api.ApiClient;
import ar.edu.itba.mygymapp.backend.api.ApiFavouritesService;
import ar.edu.itba.mygymapp.backend.api.ApiResponse;
import ar.edu.itba.mygymapp.backend.apimodels.FullRoutine;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;
import ar.edu.itba.mygymapp.backend.models.Routine;


public class FavouriteRepository {

    private final ApiFavouritesService apiService;
    private ArrayList<Routine> cacheRoutines = new ArrayList<>();

    public FavouriteRepository(App application) {
        this.apiService = ApiClient.create(application, ApiFavouritesService.class);
    }

    public LiveData<Resource<PagedList<FullRoutine>>> getFavourites() {
        return new NetworkBoundResource<PagedList<FullRoutine>, PagedList<FullRoutine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullRoutine>>> createCall() {
                return apiService.getFavourites();
            }
        }.asLiveData();
    }


    public LiveData<Resource<Void>> addFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.addFavourite(routineId);
            }
        }.asLiveData();
    }


    public LiveData<Resource<Void>> deleteFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.deleteFavourite(routineId);
            }
        }.asLiveData();
    }

    public ArrayList<Routine> getFavRoutines() {
        return cacheRoutines;
    }

    public void addFavourite(Routine routine) {
        if(!cacheRoutines.contains(routine))
            cacheRoutines.add(routine);
    }
}
