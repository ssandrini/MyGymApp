package ar.edu.itba.mygymapp.ui.routines;

import static ar.edu.itba.mygymapp.backend.repository.Resource.defaultResourceHandler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.apimodels.Execution;
import ar.edu.itba.mygymapp.backend.apimodels.FullCycle;
import ar.edu.itba.mygymapp.backend.apimodels.FullCycleExercise;
import ar.edu.itba.mygymapp.backend.apimodels.FullRoutine;
import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.repository.Status;
import ar.edu.itba.mygymapp.databinding.ActivityRoutineBinding;
import ar.edu.itba.mygymapp.backend.models.Cycle;
import ar.edu.itba.mygymapp.ui.cycles.CyclesAdapter;
import ar.edu.itba.mygymapp.backend.models.CycleExercise;
import ar.edu.itba.mygymapp.ui.exercises.ExercisesAdapter;
import ar.edu.itba.mygymapp.ui.routines.execution.RoutineExecutionActivity;
import ar.edu.itba.mygymapp.ui.routines.execution.RoutineExecutionActivityAlt;
import ar.edu.itba.mygymapp.ui.scheduler.SchedulerActivity;
import io.github.muddz.styleabletoast.StyleableToast;

public class RoutineActivity extends AppCompatActivity {

    static final private String ID_PARENT_EXTRA = "ar.edu.itba.mygymapp.ID_PARENT";
    public static final String EXTRA_ID = "ar.edu.itba.mygymapp.EXTRA_ID";
    static final public String EXTRA_TITLE = "ar.edu.itba.mygymapp.EXTRA_TITLE";
    private App app;
    private ActivityRoutineBinding binding;
    private CyclesAdapter cyclesAdapter;
    private Routine routine;
    private boolean isFav = false;
    private int routineId;
    private String routineImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplication();
        binding = ActivityRoutineBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        CollapsingToolbarLayout cToolbar = binding.collapsingToolbarLayout;
        Toolbar toolbar = binding.toolbarMain;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(root);

        Intent i = getIntent();

        routineId = (i.getIntExtra("routineId", -1));
        if (routineId == -1) {
            routineId = Integer.parseInt(i.getData().getQueryParameter("id"));
        }
        routineImageUrl = i.getStringExtra("routineImageUrl");


        app.getRoutineRepository().getRoutine(routineId).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                routine = r.getData().toRoutine();
                binding.rName.setText(routine.getName());
                binding.rDifficulty.setText(routine.getDifficulty());
                binding.rScore.setRating(routine.getScore().floatValue() / 2);
                binding.rDetail.setText(routine.getDetail());
                String[] difficulty = getResources().getStringArray(R.array.difficulties);

                binding.rDifficulty.setText(difficulty[routine.mapDifficulty() - 1]);
                binding.rCategory.setText(routine.getCategory().getName());

                binding.loadingRoutines.setVisibility(View.GONE);
//                binding.collapsingToolbarLayout.setTitle("\uD83C\uDFCB" + "  " + routine.getName());
                binding.collapsingToolbarLayout.setTitle(routine.getName());
                Glide.with(this).asBitmap().load(routineImageUrl).placeholder(R.drawable.r1).into(binding.routineImageView);


                app.getCycleRepository().getCycles(routineId, 0, 10, "order", "asc").observe(this, rCycle -> {
                    if (rCycle.getStatus() == Status.SUCCESS) {
                        ArrayList<Cycle> cycles = new ArrayList<>();
                        cyclesAdapter = new CyclesAdapter(cycles);
                        binding.cyclesRecView.setLayoutManager(new LinearLayoutManager(this));
                        binding.cyclesRecView.setAdapter(cyclesAdapter);

                        for (FullCycle fullCycle : rCycle.getData().getContent()) {
                            Cycle aux = fullCycle.toCycle();
                            cycles.add(aux);
                            cyclesAdapter.notifyDataSetChanged();
                            app.getCycleRepository().getCycleExercises(fullCycle.getId(), 0, 10, "order", "asc").observe(this, rEx -> {
                                if (rEx.getStatus() == Status.SUCCESS) {
                                    ArrayList<CycleExercise> cycleExercises = new ArrayList<>();
                                    for (FullCycleExercise fullCycleExercise : rEx.getData().getContent()) {
                                        cycleExercises.add(fullCycleExercise.toCycleExercise());
                                    }
                                    aux.setExercises(cycleExercises);
                                    routine.setCycles(cycles);

                                    cyclesAdapter.notifyDataSetChanged();
                                } else {
                                    defaultResourceHandler(r);
                                    if (r.getStatus() == Status.ERROR) {
                                        StyleableToast.makeText(getApplicationContext(), getText(R.string.error_exercise).toString(), Toast.LENGTH_LONG, R.style.errorToast).show();
                                    }
                                }
                            });
                        }

                    } else {
                        defaultResourceHandler(r);
                        if (r.getStatus() == Status.ERROR)
                            StyleableToast.makeText(getApplicationContext(), getText(R.string.error_cycle).toString(), Toast.LENGTH_LONG, R.style.errorToast).show();
                    }
                });

            } else if (r.getStatus() == Status.LOADING) {

                binding.loadingRoutines.setVisibility(View.VISIBLE);


            } else if (r.getStatus() == Status.ERROR) {
                StyleableToast.makeText(getApplicationContext(), getText(R.string.invalid_login).toString(), Toast.LENGTH_LONG, R.style.errorToast).show();
            }
            });


        binding.calendarBtn.setOnClickListener(view -> {
            Intent calIntent = new Intent(this, SchedulerActivity.class);
            calIntent.putExtra(ID_PARENT_EXTRA, routineId);
            startActivity(calIntent);
        });

        binding.normalFab.setOnClickListener(view -> {
            if (routine != null && routine.getCycles() != null && !routine.getCycles().isEmpty()) {
                routine.sortCycles();
                app.getRoutineRepository().addCacheRoutine(routine);
                Execution aux = new Execution(app.getUserRepository().getUser().getId(), false);
                app.getExecutionRepository().addExecution(routineId, aux).observe(this, ex -> {
                });
                Intent exIntent = new Intent(this, RoutineExecutionActivity.class);
                exIntent.putExtra("routineId", routine.getId());
                startActivity(exIntent);
            } else {
                StyleableToast.makeText(getApplicationContext(), getText(R.string.missing_routine).toString(), Toast.LENGTH_SHORT, R.style.errorToast).show();
            }
        });

        binding.queueFab.setOnClickListener(view -> {
            if (routine != null && routine.getCycles() != null && !routine.getCycles().isEmpty()) {
                routine.sortCycles();
                app.getRoutineRepository().addCacheRoutine(routine);
                Execution aux = new Execution(app.getUserRepository().getUser().getId(), false);
                app.getExecutionRepository().addExecution(routineId, aux).observe(this, ex -> {
                });
                Intent exIntent = new Intent(this, RoutineExecutionActivityAlt.class);
                exIntent.putExtra("routineId", routine.getId());
                startActivity(exIntent);
            } else {
                StyleableToast.makeText(getApplicationContext(), getText(R.string.missing_routine).toString(), Toast.LENGTH_SHORT, R.style.errorToast).show();
            }
        });

        binding.reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void isFavourite(int routineId) {
        app.getFavouriteRepository().getFavourites().observe(this, f -> {
            if (f.getStatus() == Status.SUCCESS) {
                for (FullRoutine fr : f.getData().getContent()) {
                    if (fr.getId() == routineId) {
                        isFav = true;
                    }
                }
                MenuItem favItem = binding.toolbarMain.getMenu().findItem(R.id.action_fav);
                if (isFav) {
                    favItem.setIcon(R.drawable.ic_yes_fav);
                } else {
                    favItem.setIcon(R.drawable.ic_not_fav);
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    public void openDialog() {
        ReviewDialog reviewDialog = new ReviewDialog();
        Bundle args = new Bundle();
        args.putInt("routineId", routine.getId());

        reviewDialog.setArguments(args);
        reviewDialog.show(getSupportFragmentManager(), "Review Dialog");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_routine, menu);

        MenuItem favItem = menu.findItem(R.id.action_fav);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        MenuItem qrItem = menu.findItem(R.id.action_take_qr);


        favItem.setVisible(true);
        shareItem.setVisible(true);
        qrItem.setVisible(true);

        isFavourite(routineId);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MyGym");
            StringBuilder sb = new StringBuilder();
            sb.append("\n \uD83D\uDCAA Hey, take a look at this routine  \uD83D\uDD25\n\n");
            sb.append(routine.getName() + " by " + routine.getUser().getUsername() + "\n");
            sb.append(routine.getDetail() + "\n\n");
            sb.append("http://mygym.com/routine?id=" + routine.getId() + "\n\n");

            String shareMessage = sb.toString();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        } else if (item.getItemId() == R.id.action_fav) {
            if (!isFav) {
                app.getFavouriteRepository().addFavourite(routine.getId()).observe(this, r -> {
                    if (r.getStatus() == Status.SUCCESS) {
                        isFav = !isFav;
                        item.setIcon(R.drawable.ic_yes_fav);
                    }
                });
            } else {
                app.getFavouriteRepository().deleteFavourite(routine.getId()).observe(this, r -> {
                    if (r.getStatus() == Status.SUCCESS) {
                        isFav = !isFav;
                        item.setIcon(R.drawable.ic_not_fav);
                        ;
                    }
                });
            }
        } else if ((item.getItemId() == R.id.action_take_qr)) {
            Intent i = new Intent(this, QrActivity.class);
            i.putExtra(EXTRA_ID, routineId);
            i.putExtra(EXTRA_TITLE, routine.getName());
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    public void setRaiting(float value) {
        binding.rScore.setRating(value);
    }
}
