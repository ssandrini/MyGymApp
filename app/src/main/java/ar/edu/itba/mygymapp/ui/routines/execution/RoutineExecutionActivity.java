package ar.edu.itba.mygymapp.ui.routines.execution;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
//        Intent i = getIntent();
//        routine = (Routine) i.getSerializableExtra("abc");


        binding.previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (exerciseIndex == 0) {
                    if (cycleIndex == 0) {
                        return ;
                    }
                    cycleIndex--;
                    setCycle(routine.getCycles().get(cycleIndex));
                    exerciseIndex = routine.getCycles().get(cycleIndex).getExercises().size() - 1;
                } else {
                    exerciseIndex--;

                }
                setExercise(routine.getCycles().get(cycleIndex).getExercises().get(exerciseIndex));

            }
        });

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cycleExercisesLength = routine.getCycles().get(cycleIndex).getExercises().size();
                int cyclesLimit = cyclesLength - 1;

                if (exerciseIndex == cycleExercisesLength - 1) {
                    if (cycleIndex == cyclesLimit) {
                        return ;
                    }
                    cycleIndex++;
                    setCycle(routine.getCycles().get(cycleIndex));
                    exerciseIndex = 0;
                } else {
                    exerciseIndex++;
                }
                setExercise(routine.getCycles().get(cycleIndex).getExercises().get(exerciseIndex));
            }
        });
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
