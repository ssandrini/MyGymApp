package ar.edu.itba.mygymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.databinding.ActivityHomeBinding;
import ar.edu.itba.mygymapp.databinding.ActivityMainBinding;

public class home extends AppCompatActivity {

    ActivityHomeBinding binding;
//    ArrayList<Routine> destacadas = new ArrayList<>();
    DestacadasRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new DestacadasRecViewAdapter();
//        adapter.setDestacadas(destacadas);
        binding.destacadasRecView.setLayoutManager(new LinearLayoutManager(this));
        binding.destacadasRecView.setAdapter(adapter);

    }
}