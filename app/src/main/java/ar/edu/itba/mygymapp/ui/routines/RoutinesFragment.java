package ar.edu.itba.mygymapp.ui.routines;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;

import ar.edu.itba.mygymapp.MainActivity;
import ar.edu.itba.mygymapp.QrScannerActivity;
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
    private int sortIndex;
    private int direction;
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem filter = menu.findItem(R.id.action_filter);
        filter.setOnMenuItemClickListener(item -> {
            showFilterDialog(item);
            return true;
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void showFilterDialog(MenuItem item) {
        Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.filter_dialog);
        Button searchButton = dialog.findViewById(R.id.button5);
        Button cancelButton = dialog.findViewById(R.id.button4);

        Spinner sortSpinner = (Spinner) dialog.findViewById(R.id.sort_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_by, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        Spinner viewSpinner = (Spinner) dialog.findViewById(R.id.view_spinner);
        ArrayAdapter<CharSequence> viewAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.direction, android.R.layout.simple_spinner_item);
        viewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewSpinner.setAdapter(viewAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (sortIndex) {
                    case 0:
                        if(direction == 0) {
                            routinesAdapter.sort(Routine.getDateComparatorAsc());
                        } else {
                            routinesAdapter.sort(Routine.getDateComparatorDesc());
                        }
                        break;
                    case 1:
                        if(direction == 0) {
                            routinesAdapter.sort(Routine.getScoreComparatorAsc());
                        } else {
                            routinesAdapter.sort(Routine.getScoreComparatorDesc());
                        }
                        break;
                    case 2:
                        if(direction == 0) {
                            routinesAdapter.sort(Routine.getDifficultyComparatorAsc());
                        } else {
                            routinesAdapter.sort(Routine.getDifficultyComparatorDesc());
                        }
                        break;
                    default:
                        if(direction == 0) {
                            routinesAdapter.sort(Routine.getCategoryComparatorAsc());
                        } else {
                            routinesAdapter.sort(Routine.getCategoryComparatorDesc());
                        }
                        break;
                }
                dialog.cancel();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        sortSpinner.setOnItemSelectedListener(new sortSpinnerActivity());
        viewSpinner.setOnItemSelectedListener(new directionSpinnerActivity());

        dialog.show();
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
        MenuItem optionsItem = menu.findItem(R.id.action_filter);
        optionsItem.setVisible(true);

        MenuItem scanQrItem = menu.findItem(R.id.action_scan_qr);
        scanQrItem.setVisible(true);
        scanQrItem.setOnMenuItemClickListener(menuItem -> {
                Intent i = new Intent(getContext(), QrScannerActivity.class);
                startActivity(i);
                return true;
        });
    }

    public void initRoutines() {
        app.getRoutineRepository().getRoutines().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                binding.loadingRoutines.setVisibility(View.GONE);
                binding.noRoutinesYet.setVisibility(View.GONE);
                assert r.getData() != null;
                for (FullRoutine fr :r.getData().getContent()) {
                    routines.add(fr.toRoutine());
                };
                routinesAdapter.setRoutines(routines);
                if (routines.isEmpty()) {
                    binding.noRoutinesYet.setVisibility(View.VISIBLE);
//                    binding.routinesRecView.setVisibility(View.GONE);
                }
                sortIndex = 0;
                direction = 0;
            } else if (r.getStatus() == Status.LOADING){
                binding.loadingRoutines.setVisibility(View.VISIBLE);
            }
        });
    }

    public class sortSpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            sortIndex = pos;
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    public class directionSpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            direction = pos;
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

}