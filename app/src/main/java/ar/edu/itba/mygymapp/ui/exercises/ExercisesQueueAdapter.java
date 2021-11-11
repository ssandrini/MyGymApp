package ar.edu.itba.mygymapp.ui.exercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Arrays;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.models.CycleExercise;

public class ExercisesQueueAdapter extends RecyclerView.Adapter<ExercisesQueueAdapter.ViewHolder> {

    private ArrayList<CycleExercise> exercises;

    public ExercisesQueueAdapter(ArrayList<CycleExercise> exercises) {
        this.exercises = new ArrayList<>();
        this.exercises.addAll(exercises);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_queue_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(exercises.get(position).getName());

        StringBuilder s1 = new StringBuilder();
        s1.append(String.valueOf(exercises.get(position).getDuration()));
        s1.append("''");

        StringBuilder s2 = new StringBuilder();
        s2.append("x");
        s2.append(exercises.get(position).getRepetitions());

        holder.exerciseDuration.setText(s1.toString());
        holder.exerciseReps.setText(s2.toString());


//        holder.parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, destacadas.get(position).getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public CycleExercise next() {
        CycleExercise res = exercises.get(0);
        exercises.remove(0);
        notifyItemRemoved(0);
        return res;
    }

    public void previous(CycleExercise exercise) {
        exercises.add(0, exercise);
        notifyItemInserted(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private MaterialCardView parent;
        private TextView exerciseReps, exerciseDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            parent = itemView.findViewById(R.id.parent);
            exerciseReps = itemView.findViewById(R.id.exerciseReps);
            exerciseDuration = itemView.findViewById(R.id.exerciseDuration);
        }
    }
}
