package ar.edu.itba.mygymapp.ui.routines;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ar.edu.itba.mygymapp.databinding.ActivityMainBinding;
import ar.edu.itba.mygymapp.databinding.ActivityRoutineBinding;

public class RoutineActivity extends AppCompatActivity {

    private ActivityRoutineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRoutineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
