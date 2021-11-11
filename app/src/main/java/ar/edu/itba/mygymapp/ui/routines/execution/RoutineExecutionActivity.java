package ar.edu.itba.mygymapp.ui.routines.execution;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mut_jaeryo.circletimer.CircleTimer;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.models.Cycle;
import ar.edu.itba.mygymapp.backend.models.CycleExercise;
import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.backend.store.RoutineStore;
import ar.edu.itba.mygymapp.databinding.RoutineExecutionBinding;

public class RoutineExecutionActivity extends AppCompatActivity {

    private RoutineExecutionBinding binding;
    private View root;
    private Routine routine;
    private int cycleIndex = 0;
    private int exerciseIndex = 0;
    private int cyclesLength;
    private CircleTimer timer;
    private boolean runningTimer = true;
    private boolean started = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = RoutineExecutionBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);


        routine = RoutineStore.getRoutines().get(0);
        cyclesLength = routine.getCycles().size();

        setExercise(routine.getCycles().get(0).getExercises().get(0));
        setCycle(routine.getCycles().get(0));
        timer = findViewById(R.id.timer);

        timer.setBaseTimerEndedListener(new CircleTimer.baseTimerEndedListener() {
            @Override
            public void OnEnded() {
                new Next().onClick(null);
            }
        });

        startTimer(routine.getCycles().get(0).getExercises().get(0));

        binding.timerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (runningTimer) {
                    pauseTimer();
                } else {
                    resumeTimer();
                }
                runningTimer = !runningTimer;

            }


        });


//        Intent i = getIntent();
//        routine = (Routine) i.getSerializableExtra("abc");


        binding.closeBtn.setOnClickListener(view -> {
            new AlertDialog.Builder(this,  R.style.AlertDialogStyle).setTitle("Are you sure you want to exit?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        binding.previousBtn.setOnClickListener(new Back());

        binding.nextBtn.setOnClickListener(new Next());
    }

    private class Next implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int cycleExercisesLength = routine.getCycles().get(cycleIndex).getExercises().size();
            int cyclesLimit = cyclesLength - 1;

            if (exerciseIndex == cycleExercisesLength - 1) {
                if (cycleIndex == cyclesLimit) {
                    return;
                }
                cycleIndex++;
                setCycle(routine.getCycles().get(cycleIndex));
                exerciseIndex = 0;
            } else {
                exerciseIndex++;
            }
            setExercise(routine.getCycles().get(cycleIndex).getExercises().get(exerciseIndex));
            startTimer(routine.getCycles().get(cycleIndex).getExercises().get(exerciseIndex));
        }
    }

    private class Back implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            if (exerciseIndex == 0) {
                if (cycleIndex == 0) {
                    return;
                }
                cycleIndex--;
                setCycle(routine.getCycles().get(cycleIndex));
                exerciseIndex = routine.getCycles().get(cycleIndex).getExercises().size() - 1;
            } else {
                exerciseIndex--;

            }
            setExercise(routine.getCycles().get(cycleIndex).getExercises().get(exerciseIndex));
            startTimer(routine.getCycles().get(cycleIndex).getExercises().get(exerciseIndex));
        }
    }


    private void resumeTimer() {
        binding.playBtn.setVisibility(View.GONE);
        binding.pauseBtn.setVisibility(View.VISIBLE);
        timer.start();
    }

    private void pauseTimer() {
        binding.pauseBtn.setVisibility(View.GONE);
        binding.playBtn.setVisibility(View.VISIBLE);
        timer.stop();
    }

    private void startTimer(CycleExercise cycleExercise) {
        timer.setMaximumTime(cycleExercise.getDuration());
        timer.setInitPosition(cycleExercise.getDuration());
        timer.start();
        binding.playBtn.setVisibility(View.GONE);
        binding.pauseBtn.setVisibility(View.VISIBLE);
    }

    private void setExercise(CycleExercise exercise) {
        binding.exerciseName.setText(exercise.getName());
        binding.exerciseDescription.setText(exercise.getDetail());
        StringBuilder durationSb = new StringBuilder();
        durationSb.append(String.valueOf(exercise.getDuration()));
        durationSb.append("''");
        binding.exerciseDuration.setText(durationSb.toString());

        StringBuilder repsSb = new StringBuilder();
        repsSb.append('x');
        repsSb.append(String.valueOf(exercise.getRepetitions()));
        binding.exerciseReps.setText(repsSb.toString());

//        startTimer(exercise);
    }

    private void setCycle(Cycle cycle) {
        binding.cycleName.setText(cycle.getName());
        StringBuilder s = new StringBuilder();
        s.append(String.valueOf(cycle.getOrder()));
        s.append(" / ");
        s.append(String.valueOf(cyclesLength));
        binding.cycleIter.setText(s.toString());
    }
}
