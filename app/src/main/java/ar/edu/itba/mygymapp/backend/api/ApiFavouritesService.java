package ar.edu.itba.mygymapp.backend.api;

import androidx.lifecycle.LiveData;


import ar.edu.itba.mygymapp.backend.apimodels.FullRoutine;
import ar.edu.itba.mygymapp.backend.apimodels.PagedList;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiFavouritesService {

    @GET("favourites?size=50")
    LiveData<ApiResponse<PagedList<FullRoutine>>> getFavourites();

    @POST("favourites/{routineId}/")
    LiveData<ApiResponse<Void>> addFavourite(@Path("routineId") int routineId);

    @DELETE("favourites/{routineId}/")
    LiveData<ApiResponse<Void>> deleteFavourite(@Path("routineId") int routineId);

}
