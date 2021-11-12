package ar.edu.itba.mygymapp.backend.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.mygymapp.backend.apimodels.FullRoutine;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRoutineService {

    @GET("routines?size=50")
    LiveData<ApiResponse<PagedList<FullRoutine>>> getRoutines();

    @GET("routines?size=50")
    LiveData<ApiResponse<PagedList<FullRoutine>>> getRoutinesByDiff(@Query("difficulty") String difficulty);

    @GET("routines/{routineId}")
    LiveData<ApiResponse<FullRoutine>> getRoutine(@Path("routineId") int routineId);


}
