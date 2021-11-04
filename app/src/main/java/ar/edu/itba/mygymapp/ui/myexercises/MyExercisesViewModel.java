package ar.edu.itba.mygymapp.ui.myexercises;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyExercisesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyExercisesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("My exercises");
    }

    public LiveData<String> getText() {
        return mText;
    }
}