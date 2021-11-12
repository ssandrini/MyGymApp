package ar.edu.itba.mygymapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;

import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.databinding.FragmentHomeBinding;

import ar.edu.itba.mygymapp.ui.routines.RoutinesAdapter;
import ar.edu.itba.mygymapp.ui.routines.RoutineActivity;

import ar.edu.itba.mygymapp.backend.models.Routine;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private App app;
    private RoutinesAdapter myRoutinesAdapter;
    private RoutinesAdapter highlightsAdapter;
    private RoutinesAdapter recentsAdapter;

    private ArrayList<Routine> routines = new ArrayList<>();
    /*
        En realidad voy a tener 3 arrayList que voy a obtener del store/api
     */

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        app = (App) getActivity().getApplication();
        StringBuilder sb = new StringBuilder();
        sb.append("Good ");
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if(timeOfDay >= 0 && timeOfDay < 12){
            sb.append("morning");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            sb.append("afternoon");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            sb.append("evening");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            sb.append("night");
        }
        sb.append(", ");
        sb.append(app.getUserRepository().getUser().getUsername());
        sb.append("! \uD83D\uDC4B ");
        binding.welcomeHeader.setText(sb.toString());

        myRoutinesAdapter = new RoutinesAdapter(getContext());
        highlightsAdapter = new RoutinesAdapter(getContext());
        recentsAdapter = new RoutinesAdapter(getContext());

        populateRoutines();
        myRoutinesAdapter.setRoutines(routines);
        highlightsAdapter.setRoutines(routines);
        recentsAdapter.setRoutines(routines);


        binding.myroutinesRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.myroutinesRecView.setAdapter(myRoutinesAdapter);

        binding.highlightsRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.highlightsRecView.setAdapter(highlightsAdapter);

        binding.recentsRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recentsRecView.setAdapter(recentsAdapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void goToRoutineActivity() {
        Intent intent = new Intent(getContext(), RoutineActivity.class);
        startActivity(intent);
    }

    private void populateRoutines() {
        routines.add(new Routine(0, "Boxeo", "Get those moves", 3.3, true, "rookie", null, null, null, true));
        routines.add(new Routine(0, "Home", "Get those moves", 2.7, true, "rookie", null, null, null, true));

        routines.add(new Routine(0, "Calistenia", "Get those moves", 4.3, true, "rookie", null, null, null, true));
           routines.add(new Routine(0, "HIIT", "Get those moves", 1.5, true, "rookie", null, null, null, true));

    }
}