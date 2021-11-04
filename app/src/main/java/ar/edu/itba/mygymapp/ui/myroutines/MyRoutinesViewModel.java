package ar.edu.itba.mygymapp.ui.myroutines;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyRoutinesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyRoutinesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("My routines");
    }

    public LiveData<String> getText() {
        return mText;
    }
}