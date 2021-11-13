package ar.edu.itba.mygymapp.backend.api;

import androidx.lifecycle.LiveData;

import java.util.Map;

import ar.edu.itba.mygymapp.backend.apimodels.FullCycle;
import ar.edu.itba.mygymapp.backend.apimodels.FullCycleExercise;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiCycleService {

    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<FullCycle>>> getCycles(@Path("routineId") int routineId);

    ///{routineId}/cycles?page={page}&size={size}&orderBy={orderBy}&direction={direction}
    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<FullCycle>>> getCycles(@Path("routineId") int routineId,
                                                          @QueryMap Map<String, String> options);
    @GET("cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<FullCycleExercise>>> getCycleExercises(@Path("cycleId") int cycleId);

    @GET("cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<FullCycleExercise>>> getCycleExercises(@Path("cycleId") int cycleId,
                                                                          @QueryMap Map<String, String> options);


}
