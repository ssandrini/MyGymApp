package ar.edu.itba.mygymapp.backend.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.api.ApiClient;
import ar.edu.itba.mygymapp.backend.api.ApiResponse;
import ar.edu.itba.mygymapp.backend.api.ApiUserService;
import ar.edu.itba.mygymapp.backend.apimodels.Credentials;
import ar.edu.itba.mygymapp.backend.apimodels.EmailConfirmation;
import ar.edu.itba.mygymapp.backend.apimodels.FullUser;
import ar.edu.itba.mygymapp.backend.apimodels.Token;
import ar.edu.itba.mygymapp.backend.apimodels.RegisterCredentials;


public class UserRepository {

    private FullUser user;
    private final ApiUserService apiService;
    private ArrayList<Integer> ids = new ArrayList<>();
    public UserRepository(App app) {
        this.apiService = ApiClient.create(app, ApiUserService.class);
        user = null;
    }

    public FullUser getUser() {
        return user;
    }

    public void setUser(FullUser user) {
        this.user = user;
    }

    public LiveData<Resource<Token>> login(Credentials credentials) {
        return new NetworkBoundResource<Token, Token>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                return apiService.login(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<FullUser>> register(RegisterCredentials credentials) {
        return new NetworkBoundResource<FullUser, FullUser>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<FullUser>> createCall() {
                return apiService.register(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> verifyEmail(EmailConfirmation credentials) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.verifyEmail(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<FullUser>> getCurrentUser() {
        return new NetworkBoundResource<FullUser, FullUser>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<FullUser>> createCall() {
                return apiService.getCurrentUser();
            }
        }.asLiveData();
    }

    public LiveData<Resource<FullUser>> editCurrentUser(FullUser user) {
        return new NetworkBoundResource<FullUser, FullUser>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<FullUser>> createCall() {
                return apiService.editCurrentUser(user);
            }
        }.asLiveData();
    }

    public void addExecution(Integer id) {
        ids.add(id);
    }
}