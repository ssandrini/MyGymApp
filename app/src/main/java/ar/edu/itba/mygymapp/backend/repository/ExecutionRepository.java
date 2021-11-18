package ar.edu.itba.mygymapp.backend.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.api.ApiClient;
import ar.edu.itba.mygymapp.backend.api.ApiExecutionService;
import ar.edu.itba.mygymapp.backend.api.ApiResponse;
import ar.edu.itba.mygymapp.backend.apimodels.Execution;
import ar.edu.itba.mygymapp.backend.apimodels.FullExecution;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;

public class ExecutionRepository {
    private final ApiExecutionService apiService;

    public ExecutionRepository(App application) {
        this.apiService = ApiClient.create(application, ApiExecutionService.class);
    }

    public LiveData<Resource<Void>> addExecution(int routineId, Execution execution) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.addExecution(routineId, execution);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<FullExecution>>> getExecutions(int routineId) {
        return new NetworkBoundResource<PagedList<FullExecution>, PagedList<FullExecution>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullExecution>>> createCall() {
                return apiService.getExecutions(routineId);
            }
        }.asLiveData();
    }
}
