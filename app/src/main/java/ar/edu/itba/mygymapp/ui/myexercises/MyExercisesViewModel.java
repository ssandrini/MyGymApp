package ar.edu.itba.mygymapp.ui.myexercises;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyExercisesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyExercisesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}