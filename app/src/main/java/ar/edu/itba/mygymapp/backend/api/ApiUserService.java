package ar.edu.itba.mygymapp.backend.api;

import androidx.lifecycle.LiveData;

import ar.edu.itba.mygymapp.backend.apimodels.Credentials;
import ar.edu.itba.mygymapp.backend.apimodels.EmailConfirmation;
import ar.edu.itba.mygymapp.backend.apimodels.FullRoutine;
import ar.edu.itba.mygymapp.backend.apimodels.FullUser;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;
import ar.edu.itba.mygymapp.backend.apimodels.Token;
import ar.edu.itba.mygymapp.backend.apimodels.RegisterCredentials;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiUserService {

    @POST("users/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("users/logout")
    LiveData<ApiResponse<Void>> logout();

    @POST("users")
    LiveData<ApiResponse<FullUser>> register(@Body RegisterCredentials credentials);

    @POST("users/verify_email")
    LiveData<ApiResponse<Void>> verifyEmail(@Body EmailConfirmation credentials);

    @GET("users/current")
    LiveData<ApiResponse<FullUser>> getCurrentUser();

    @GET("users/current/routines/")
    LiveData<ApiResponse<PagedList<FullRoutine>>> getUserRoutines();

    @PUT("users/current")
    LiveData<ApiResponse<FullUser>> editCurrentUser(@Body FullUser user);

}
