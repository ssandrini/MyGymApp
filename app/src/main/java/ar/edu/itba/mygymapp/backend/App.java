package ar.edu.itba.mygymapp.backend;

import android.app.Application;

import ar.edu.itba.mygymapp.backend.repository.CycleRepository;
import ar.edu.itba.mygymapp.backend.repository.ExerciseImageRepository;
import ar.edu.itba.mygymapp.backend.repository.FavouriteRepository;
import ar.edu.itba.mygymapp.backend.repository.ReviewRepository;
import ar.edu.itba.mygymapp.backend.repository.RoutineRepository;
import ar.edu.itba.mygymapp.backend.repository.UserRepository;


public class App extends Application {

    private AppPreferences preferences;
    private UserRepository userRepository;
    private RoutineRepository routineRepository;
    private FavouriteRepository favouriteRepository;
    private CycleRepository cycleRepository;
    private ExerciseImageRepository exerciseImageRepository;
    private ReviewRepository reviewRepository;
    public AppPreferences getPreferences() { return preferences; }
    public UserRepository getUserRepository() {
        return userRepository;
    }
    public FavouriteRepository getFavouriteRepository() {
        return favouriteRepository;
    }
    public RoutineRepository getRoutineRepository() {
        return routineRepository;
    }
    public CycleRepository getCycleRepository() {
        return cycleRepository;
    }
    public ExerciseImageRepository getExerciseImageRepository() {
        return exerciseImageRepository;
    }
    public ReviewRepository getReviewRepository() {
        return reviewRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new AppPreferences(this);
        userRepository = new UserRepository(this);
        routineRepository = new RoutineRepository(this);
        favouriteRepository = new FavouriteRepository(this);
        cycleRepository = new CycleRepository(this);
        exerciseImageRepository = new ExerciseImageRepository(this);
        reviewRepository = new ReviewRepository(this);
    }
}
