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
import ar.edu.itba.mygymapp.ui.routines.Routine;
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

        adapter = new RoutinesAdapter();
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
        routines.add(new Routine("Calistenia", "5 out of 5", "12-15min", "r1.png"));
        routines.add(new Routine("Home", "5 out of 5", "12-15min", "r2.png"));
        routines.add(new Routine("Pecs killer", "5 out of 5", "12-15min", "r3.png"));
        routines.add(new Routine("Legs", "5 out of 5", "12-15min", "r4.png"));
        routines.add(new Routine("Calistenia", "5 out of 5", "12-15min", "r1.png"));
        routines.add(new Routine("Home", "5 out of 5", "12-15min", "r2.png"));
        routines.add(new Routine("Pecs killer", "5 out of 5", "12-15min", "r3.png"));
        routines.add(new Routine("Legs", "5 out of 5", "12-15min", "r4.png"));
    }
}
