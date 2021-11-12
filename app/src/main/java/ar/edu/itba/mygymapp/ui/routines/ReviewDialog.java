package ar.edu.itba.mygymapp.ui.routines;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.bumptech.glide.Glide;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.App;

public class ReviewDialog extends AppCompatDialogFragment {
    private App app;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.review_layout, null);

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
                //Aca deberiamos crear la review y mandarla a la API.
            }
        });
        return builder.create();
    }
}
