package ar.edu.itba.mygymapp.ui.routines;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoutinesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RoutinesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("My routines");
    }

    public LiveData<String> getText() {
        return mText;
    }
}