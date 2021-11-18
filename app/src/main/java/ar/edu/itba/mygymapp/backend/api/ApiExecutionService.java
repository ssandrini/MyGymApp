package ar.edu.itba.mygymapp.backend.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.mygymapp.backend.apimodels.Execution;
import ar.edu.itba.mygymapp.backend.apimodels.FullExecution;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiExecutionService {
    @POST("executions/{routineId}/")
    LiveData<ApiResponse<Void>> addExecution(@Path("routineId") int routineId, @Body Execution execution);

    @GET("executions/{routineId}?page=0&size=50&orderBy=date&direction=desc")
    LiveData<ApiResponse<PagedList<FullExecution>>> getExecutions(@Path("routineId") int routineId);
}
