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

import ar.edu.itba.mygymapp.R;


public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder> {

    private ArrayList<Exercise> exercises;

    public ExercisesAdapter(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(exercises.get(position).getName());
        holder.type.setText(exercises.get(position).getType());
        holder.detail.setText(exercises.get(position).getDetail());

        if (exercises.get(position).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedConstraintLayout.setVisibility(View.VISIBLE);
            holder.expandMoreBtn.setVisibility(View.GONE);
        } else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedConstraintLayout.setVisibility(View.GONE);
            holder.expandMoreBtn.setVisibility(View.VISIBLE);
        }

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

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
        // notifyDataSetChanged();  para notificarle al view adaptor que la data proveniente de la api ya cambio
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, type, detail;
        private MaterialCardView parent;

        private ImageView expandMoreBtn, expandLessBtn;
        private ConstraintLayout expandedConstraintLayout, collapsedRelLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            detail = itemView.findViewById(R.id.detail);
            parent = itemView.findViewById(R.id.parent);
            expandMoreBtn = itemView.findViewById(R.id.expandMoreBtn);
            expandLessBtn = itemView.findViewById(R.id.expandLessBtn);
            expandedConstraintLayout = itemView.findViewById(R.id.expandedConstraintLayout);
            collapsedRelLayout = itemView.findViewById(R.id.collapsedRelLayout);

            collapsedRelLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Exercise exercise = exercises.get(getAdapterPosition());
                    exercise.setExpanded(!exercise.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            expandMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Exercise exercise = exercises.get(getAdapterPosition());
                    exercise.setExpanded(!exercise.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            expandLessBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Exercise exercise = exercises.get(getAdapterPosition());
                    exercise.setExpanded(!exercise.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
