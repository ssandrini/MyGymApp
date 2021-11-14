package ar.edu.itba.mygymapp.ui.routines;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.apimodels.Review;
import ar.edu.itba.mygymapp.backend.repository.Status;

public class ReviewDialog extends AppCompatDialogFragment {
    private App app;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.review_layout, null);

        RatingBar score = view.findViewById(R.id.reviewScore);
        EditText review = view.findViewById(R.id.reviewText);

        TextView usernameReview = view.findViewById(R.id.usernameReview);
        usernameReview.setText(app.getUserRepository().getUser().getUsername());
        ImageView userAvatarReview = view.findViewById(R.id.userAvatarReview);
        Glide.with(view.getContext()).load(app.getUserRepository().getUser().getAvatarUrl()).placeholder(R.drawable.avatar).into(userAvatarReview);


        builder.setView(view).setTitle("Review").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Es el que cierra (creo no hay que hacer nada aca)
            }
        }).setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                assert getArguments() != null;
                int routineId = getArguments().getInt("routineId");
                Review body = new Review( (int) (score.getRating() * 2), review.getText().toString()  ) ;
                app.getReviewRepository().addReview(routineId, body).observe(getActivity(), r -> {
                    if (r.getStatus() == Status.SUCCESS) {
//                        Toast.makeText(getContext(), R.string.review_success, Toast.LENGTH_SHORT).show();
                    }
                });
                //Aca deberiamos crear la review y mandarla a la API.
            }
        });
        return builder.create();
    }


}
