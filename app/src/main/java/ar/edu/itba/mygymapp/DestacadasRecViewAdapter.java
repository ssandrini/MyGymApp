package ar.edu.itba.mygymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class DestacadasRecViewAdapter extends RecyclerView.Adapter<DestacadasRecViewAdapter.ViewHolder> {

    private ArrayList<Routine> destacadas = new ArrayList<>();

    public DestacadasRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.routineName.setText(destacadas.get(position).getName());
        holder.routineScore.setText(destacadas.get(position).getScore());
        holder.routineUser.setText(destacadas.get(position).getUser());
//        holder.parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, destacadas.get(position).getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return destacadas.size();
    }

    public void setDestacadas(ArrayList<Routine> destacadas) {
        this.destacadas = destacadas;
        // notifyDataSetChanged();  para notificarle al view adaptor que la data proveniente de la api ya cambio
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView routineName, routineScore, routineUser;
        private MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            routineName = itemView.findViewById(R.id.routineName);
            routineScore = itemView.findViewById(R.id.routineScore);
            routineUser = itemView.findViewById(R.id.routineUser);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
