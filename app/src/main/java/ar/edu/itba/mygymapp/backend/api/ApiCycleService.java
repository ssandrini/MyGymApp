package ar.edu.itba.mygymapp.backend.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.mygymapp.backend.apimodels.FullCycle;
import ar.edu.itba.mygymapp.backend.apimodels.FullCycleExercise;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCycleService {

    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<FullCycle>>> getCycles(@Path("routineId") int routineId);

    @GET("cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<FullCycleExercise>>> getCycleExercises(@Path("cycleId") int cycleId);


}
