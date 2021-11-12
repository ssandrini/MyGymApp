package ar.edu.itba.mygymapp.backend.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import ar.edu.itba.mygymapp.backend.api.ApiResponse;


public abstract class NetworkBoundResource<Model, Domain> {

    private final MediatorLiveData<Resource<Domain>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource() {

        setValue(Resource.loading(null));

        LiveData<ApiResponse<Model>> apiResponse = createCall();
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);

            if (response.getError() != null) {
                onFetchFailed();
                setValue(Resource.error(response.getError(), null));
            } else {
                Domain data = processResponse(response.getData());
                setValue(Resource.success(data));
            }
        });
    }

    @MainThread
    private void setValue(Resource<Domain> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    protected void onFetchFailed() {
    }

    @WorkerThread
    protected Domain processResponse(Model response)
    {
        return (Domain) response;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<Model>> createCall();

    public LiveData<Resource<Domain>> asLiveData() {
        return result;
    }
}
