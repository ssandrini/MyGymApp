package ar.edu.itba.mygymapp.ui.cycles;

import static java.security.AccessController.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.ui.exercises.ExercisesAdapter;

public class CyclesAdapter extends RecyclerView.Adapter<CyclesAdapter.ViewHolder> {

    private ArrayList<Cycle> cycles = new ArrayList<>();

    public CyclesAdapter(ArrayList<Cycle> cycles) {
        this.cycles = cycles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cycle_list_item, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.cycleName.setText(cycles.get(position).getName());

        ExercisesAdapter adapter = new ExercisesAdapter(cycles.get(position).getExercises());
        holder.cycleExsRecView.setLayoutManager(new LinearLayoutManager(holder.parent.getContext()));
        holder.cycleExsRecView.setAdapter(adapter);

        if (cycles.get(position).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelativeLayout.setVisibility(View.VISIBLE);
            holder.expandCycle.setVisibility(View.GONE);
        } else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelativeLayout.setVisibility(View.GONE);
            holder.expandCycle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return cycles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView parent;
        private TextView cycleName;
        private RecyclerView cycleExsRecView;
        private RelativeLayout expandedRelativeLayout;
        private ImageView expandCycle, collapseCycle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cycleName = itemView.findViewById(R.id.cycleName);
            parent = itemView.findViewById(R.id.cycleCard);
            cycleExsRecView = itemView.findViewById(R.id.cycleExsRecView);
            expandedRelativeLayout = itemView.findViewById(R.id.expandedRelativeLayout);
            expandCycle = itemView.findViewById(R.id.expandCycle);
            collapseCycle = itemView.findViewById(R.id.collapseCycle);



            expandCycle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cycle cycle = cycles.get(getAdapterPosition());
                    cycle.setExpanded(!cycle.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            collapseCycle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cycle cycle = cycles.get(getAdapterPosition());
                    cycle.setExpanded(!cycle.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
