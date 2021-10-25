package ar.edu.itba.mygymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.databinding.ActivityHomeBinding;
import ar.edu.itba.mygymapp.databinding.ActivityMainBinding;

public class home extends AppCompatActivity {

    private RecyclerView destacadasRecView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        destacadasRecView = findViewById(R.id.destacadasRecView);

        setContentView(R.layout.activity_home);

        ArrayList<Routine> destacadas = new ArrayList<>();
        destacadas.add(new Routine("Calistenia", "5 out of 5", "Dagos", "r1.png"));
        destacadas.add(new Routine("Home workout", "5 out of 5", "Dax", "r2.png"));
        destacadas.add(new Routine("Pecs killer", "5 out of 5", "Santi", "r3.png"));
        destacadas.add(new Routine("Booty", "5 out of 5", "Solcha", "r4.png"));

//        DestacadasRecViewAdapter adapter = new DestacadasRecViewAdapter();
//        adapter.setDestacadas(destacadas);
//
//        destacadasRecView.setAdapter(adapter);
//        destacadasRecView.setLayoutManager(new LinearLayoutManager(this));
    }
}