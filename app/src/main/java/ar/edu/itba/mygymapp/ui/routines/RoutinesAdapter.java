package ar.edu.itba.mygymapp.ui.routines;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import ar.edu.itba.mygymapp.MainActivity;
import ar.edu.itba.mygymapp.R;

import ar.edu.itba.mygymapp.backend.models.Routine;

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.ViewHolder> implements Filterable {

    private ArrayList<Routine> routines = new ArrayList<>();
    private ArrayList<Routine> allRoutines;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.routineName.setText(routines.get(position).getName());
        holder.routineScore.setRating(routines.get(position).getScore().floatValue());
        holder.routineDuration.setText(routines.get(position).getDurationStr());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RoutineActivity.class);
                intent.putExtra("routineObject", routines.get(holder.getAdapterPosition()));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public void setRoutines(ArrayList<Routine> destacadas) {
        this.routines = destacadas;
        this.allRoutines = new ArrayList<>(destacadas);
        // notifyDataSetChanged();  para notificarle al view adaptor que la data proveniente de la api ya cambio
    }

    // Este filter es para la búsqueda de rutinas:
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Routine> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()) {
                filteredList.addAll(allRoutines);
            } else {
                for(Routine routine : allRoutines) {
                    if(routine.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        filteredList.add(routine);
                    else if( Soundex.similarity(routine.getName().toUpperCase(), charSequence.toString().toUpperCase()) > 0.5)
                        filteredList.add(routine);
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            routines.clear();
            if(filterResults != null && filterResults.values != null)
                routines.addAll((Collection<? extends Routine>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    @SuppressLint("NotifyDataSetChanged")
    public void sort(Comparator<Routine> comparator) {
        routines.sort(comparator);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView routineName,  routineDuration;
        private RatingBar routineScore;
        private MaterialCardView parent;
        private ImageView routineImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            routineName = itemView.findViewById(R.id.routineName);
            routineScore = itemView.findViewById(R.id.routineScore);
            routineDuration = itemView.findViewById(R.id.routineDuration);
            parent = itemView.findViewById(R.id.card);
        }
    }

    private static class Soundex {
        //                                   A,  B,  C,  D,  E,  F,  G,  H,  I,  J,  K,  L,  M,  N,  O,  P,  Q,  R,  S,  T,  U,  V,  W,  X,  Y,  Z
        private static final char data[] = {'0','1','2','3','0','1','2','0','0','2','2','4','5','5','0','1','2','6','2','3','0','1','0','2','0','2'};

        public static String encode (String input) {

            char [] ans = {'0','0','0','0'};
            ans[0] = input.charAt(0);

            char lastCode = data[ input.charAt(0) - 65 ];
            int index = 1;
            char current;

            for(int i = 1; i < input.length() && index < 4; i++, lastCode = current) {
                current = data [ input.charAt(i) - 65 ];
                if( current != '0' && current != lastCode ) {
                    ans[index++] = current;
                }
            }

            return new String(ans);
        }

        public static double similarity(String in1, String in2) {

            String in1Enc = encode(in1);
            String in2Enc = encode(in2);
            double ans = 0;
            for(int i = 0; i < 4 ; i++) {
                if (in1Enc.charAt(i) == in2Enc.charAt(i)) {
                    ans += 0.25;
                }
            }
            return ans;
        }
    }
}
