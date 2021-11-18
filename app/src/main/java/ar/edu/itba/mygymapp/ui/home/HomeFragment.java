package ar.edu.itba.mygymapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.apimodels.Execution;
import ar.edu.itba.mygymapp.backend.apimodels.FullExecution;
import ar.edu.itba.mygymapp.backend.apimodels.FullRoutine;
import ar.edu.itba.mygymapp.backend.repository.Resource;
import ar.edu.itba.mygymapp.backend.repository.Status;
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
    private View root;

    private ArrayList<Routine> highlights = new ArrayList<>();
    private ArrayList<Routine> myRoutines = new ArrayList<>();
    private ArrayList<Routine> recents = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        app = (App) getActivity().getApplication();
        StringBuilder sb = new StringBuilder();
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            sb.append(getResources().getString(R.string.morning));
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            sb.append(getResources().getString(R.string.afternoon));
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            sb.append(getResources().getString(R.string.evening));
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            sb.append(getResources().getString(R.string.night));
        }

        sb.append(", ");
        sb.append(app.getUserRepository().getUser().getUsername());
        sb.append("! \uD83D\uDC4B ");
        binding.welcomeHeader.setText(sb.toString());
        DisplayMetrics displayMetrics = root.getContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        Log.d("tamaño:", String.valueOf(dpWidth));
        if (dpWidth >= 700) {
            binding.welcomeHeader.setTextSize(30);
            binding.recomendedRoutine.setTextSize(17);

        }
        myRoutinesAdapter = new RoutinesAdapter(getContext());
        highlightsAdapter = new RoutinesAdapter(getContext());
        recentsAdapter = new RoutinesAdapter(getContext());

        myRoutinesAdapter.setRoutines(myRoutines);
        highlightsAdapter.setRoutines(highlights);
        recentsAdapter.setRoutines(recents);

        binding.consoleBtn.setVisibility(View.GONE);

        binding.myroutinesRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.myroutinesRecView.setAdapter(myRoutinesAdapter);

        binding.highlightsRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.highlightsRecView.setAdapter(highlightsAdapter);

        binding.recentsRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recentsRecView.setAdapter(recentsAdapter);

        binding.consoleBtn.setOnClickListener(view -> {

            StringBuilder s = new StringBuilder();
            for (Routine favRoutine : app.getFavouriteRepository().getFavRoutines()) {
                s.append(favRoutine.getId() + favRoutine.getName());
                s.append('\n');
            }
            Log.d("FAVS", s.toString());

            StringBuilder s2 = new StringBuilder();
            for (Routine cacheR : app.getRoutineRepository().getCacheRoutines()) {
                s2.append(cacheR.getId() + cacheR.getName());
                s2.append('\n');
            }
            Log.d("ALL ROUTINES", s2.toString());
        });

        setHasOptionsMenu(true);
        initRoutines();
        initFavourites();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        recents.clear();
        initRoutines();
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


    public void initRoutines() {
        app.getRoutineRepository().getRoutines().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                assert r.getData() != null;
                for (FullRoutine fr : r.getData().getContent()) {

                    Routine aux = fr.toRoutine();

                    app.getExecutionRepository().getExecutions(fr.getId()).observe(getViewLifecycleOwner(), ex -> {
                        if(ex.getStatus() == Status.SUCCESS) {
                            assert ex.getData() != null;
                            for(FullExecution exec : ex.getData().getContent()) {
                                if(exec.getDuration() == app.getUserRepository().getUser().getId()) {
                                    aux.setLastActivity(exec.getDate());
                                    if(!recents.contains(aux))
                                        recents.add(aux);
                                    break;
                                }
                            }
                            if(recents.isEmpty()) {
                                binding.noRecents.setVisibility(View.VISIBLE);
                            } else {
                                binding.noRecents.setVisibility(View.GONE);
                                recents.sort(new Comparator<Routine>() {
                                    @Override
                                    public int compare(Routine routine, Routine t1) {
                                        return Long.compare(t1.getLastActivity(), routine.getLastActivity());
                                    }
                                });
                                recentsAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                    if(aux.getScore() >= 7 && !highlights.contains(aux))
                        highlights.add(aux);
                    if(fr.getPublicUser().getId() == app.getUserRepository().getUser().getId() && !myRoutines.contains(aux))
                        myRoutines.add(aux);
                }

                if(highlights.isEmpty()) {
                    binding.noHighlights.setVisibility(View.VISIBLE);
                } else {
                    binding.noHighlights.setVisibility(View.GONE);
                    highlightsAdapter.notifyDataSetChanged();
                }

                if(myRoutines.isEmpty()) {
                    binding.noMyRoutines.setVisibility(View.VISIBLE);
                } else {
                    binding.noMyRoutines.setVisibility(View.GONE);
                    myRoutinesAdapter.notifyDataSetChanged();
                }

            } else {
                Resource.defaultResourceHandler(r);
            }
        });
    }

    private void initFavourites() {
        app.getFavouriteRepository().getFavourites().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                for (FullRoutine fullRoutine : r.getData().getContent()) {
                    app.getFavouriteRepository().addFavourite(fullRoutine.toRoutine());
                }
            }
        });
    }

    /*
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.explore_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem urlItem = menu.findItem(R.id.action_url);
        urlItem.setVisible(true);
    }

     */


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem urlItem = menu.findItem(R.id.action_url);
        urlItem.setVisible(true);
    }

}



//    @Override
//    public void onPrepareOptionsMenu(@NonNull Menu menu) {
//        MenuItem urlItem = menu.findItem(R.id.action_url);
//        urlItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem menuItem) {
//                // Do something when expanded
//                return true;  // Return true to expand action view
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
//                // Do something when action item collapses
//                return true;  // Return true to collapse action view
//            }
//        });
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            public boolean onQueryTextChange(String newText) {
//                routinesAdapter.getFilter().filter(newText);
//                return false;
//            }
//
//            public boolean onQueryTextSubmit(String query) {
//                searchItem.collapseActionView();
//                return true;
//            }
//        });
//        searchItem.setVisible(true);
//        MenuItem optionsItem = menu.findItem(R.id.action_options);
//        urlItem.setVisible(true);
        //super.onPrepareOptionsMenu(menu);  CREO que esta línea no va.

//        super.onPrepareOptionsMenu(menu);
//    }
//}