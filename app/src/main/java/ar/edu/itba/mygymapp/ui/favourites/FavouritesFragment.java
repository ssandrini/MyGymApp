package ar.edu.itba.mygymapp.ui.favourites;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.apimodels.FullRoutine;
import ar.edu.itba.mygymapp.backend.repository.Resource;
import ar.edu.itba.mygymapp.backend.repository.Status;
import ar.edu.itba.mygymapp.databinding.FragmentFavouritesBinding;
import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.ui.routines.RoutinesAdapter;

public class FavouritesFragment extends Fragment {
    private FavouritesViewModel favouritesViewModel;
    private FragmentFavouritesBinding binding;
    private App app;
    RoutinesAdapter adapter;
    private ArrayList<Routine> routines = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        favouritesViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new RoutinesAdapter(getContext());
        initRoutines();
        adapter.setRoutines(routines);

        DisplayMetrics displayMetrics= root.getContext().getResources().getDisplayMetrics();
        float dpWidth=displayMetrics.widthPixels/displayMetrics.density;
        Log.d("tamaño:",String.valueOf(dpWidth));
        if(dpWidth>=700){
            binding.favsRecView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }else {
            binding.favsRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        binding.favsRecView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initRoutines() {
        app.getFavouriteRepository().getFavourites().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                binding.loadingRoutines.setVisibility(View.GONE);
                binding.noRoutinesYet.setVisibility(View.GONE);
                assert r.getData() != null;
                for (FullRoutine fr :r.getData().getContent()) {
                    routines.add(fr.toRoutine());
                };
                if (routines.isEmpty()) {
                    binding.noRoutinesYet.setVisibility(View.VISIBLE);
//                    binding.routinesRecView.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();

            } else if (r.getStatus() == Status.LOADING){
                binding.loadingRoutines.setVisibility(View.VISIBLE);
            }
        });
    }
}
