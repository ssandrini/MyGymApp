package ar.edu.itba.mygymapp.ui.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.databinding.FragmentFavouritesBinding;
import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.ui.routines.RoutinesAdapter;

public class FavouritesFragment extends Fragment {
    private FavouritesViewModel favouritesViewModel;
    private FragmentFavouritesBinding binding;
    RoutinesAdapter adapter;

    private ArrayList<Routine> routines = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favouritesViewModel =
                new ViewModelProvider(this).get(FavouritesViewModel.class);

        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new RoutinesAdapter(getContext());
        populateRoutines();
        adapter.setRoutines(routines);

        binding.favsRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.favsRecView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void populateRoutines() {
        routines.add(new Routine(0, "Home", "Get those moves", 2.7, true, "rookie", null, null, null, true));
        routines.add(new Routine(0, "HIIT", "Get those moves", 1.5, true, "rookie", null, null, null, true));

        routines.add(new Routine(0, "Calistenia", "Get those moves", 4.3, true, "rookie", null, null, null, true));
        routines.add(new Routine(0, "Boxeo", "Get those moves", 3.3, true, "rookie", null, null, null, true));


        //        routines.add(new Routine("Home", "5 out of 5", "12-15min", "r2.png"));
//        routines.add(new Routine("Pecs killer", "5 out of 5", "12-15min", "r3.png"));
//        routines.add(new Routine("Legs", "5 out of 5", "12-15min", "r4.png"));
//        routines.add(new Routine("Calistenia", "5 out of 5", "12-15min", "r1.png"));
//        routines.add(new Routine("Home", "5 out of 5", "12-15min", "r2.png"));
//        routines.add(new Routine("Pecs killer", "5 out of 5", "12-15min", "r3.png"));
//        routines.add(new Routine("Legs", "5 out of 5", "12-15min", "r4.png"));
    }
}
