package ar.edu.itba.mygymapp.ui.routines;

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
import java.util.Collections;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.apimodels.FullRoutine;
import ar.edu.itba.mygymapp.backend.repository.Resource;
import ar.edu.itba.mygymapp.backend.repository.Status;
import ar.edu.itba.mygymapp.databinding.FragmentRoutinesBinding;
import ar.edu.itba.mygymapp.backend.models.Routine;

public class RoutinesFragment extends Fragment {
    private FragmentRoutinesBinding binding;
    private RoutinesViewModel routinesViewModel;
    private RoutinesAdapter routinesAdapter;
    private ArrayList<Routine> routines = new ArrayList<>();
    private App app;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        routinesViewModel = new ViewModelProvider(this).get(RoutinesViewModel.class);
        app = (App) getActivity().getApplication();
        binding = FragmentRoutinesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        routinesAdapter = new RoutinesAdapter(getContext());
        routinesAdapter.setRoutines(routines);

        binding.routinesRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.routinesRecView.setAdapter(routinesAdapter);
        DisplayMetrics displayMetrics= root.getContext().getResources().getDisplayMetrics();
        float dpWidth=displayMetrics.widthPixels/displayMetrics.density;
        Log.d("tamaño:",String.valueOf(dpWidth));
        if(dpWidth>=700){
            binding.routinesRecView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }else {
            binding.routinesRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        setHasOptionsMenu(true);

        initRoutines();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                // Do something when action item collapses
                return true;  // Return true to collapse action view
            }
        });
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                routinesAdapter.getFilter().filter(newText);
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                searchItem.collapseActionView();
                return true;
            }
        });
        searchItem.setVisible(true);
        MenuItem optionsItem = menu.findItem(R.id.action_options);
        optionsItem.setVisible(true);
        //super.onPrepareOptionsMenu(menu);  CREO que esta línea no va.
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.score:
                routinesAdapter.sort(Routine.getScoreComparator());
                if (item.isChecked()) {
                    item.setChecked(true);
                } else
                    item.setChecked(false);
                return true;
            case R.id.date:
                if (item.isChecked()) {
                    item.setChecked(true);
                } else
                    item.setChecked(false);
            case R.id.difficulty:
                if (item.isChecked()) {
                    item.setChecked(true);
                } else
                    item.setChecked(false);
            case R.id.category:
                if (item.isChecked()) {
                    item.setChecked(true);
                } else
                    item.setChecked(false);
        }
        return false;

    }

    public void initRoutines() {
        app.getRoutineRepository().getRoutines().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                assert r.getData() != null;
                for (FullRoutine fr :r.getData().getContent()) {
                    routines.add(fr.toRoutine());
                };
                routinesAdapter.setRoutines(routines);
            } else {
                Resource.defaultResourceHandler(r);
            }
        });
    }

}