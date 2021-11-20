package ar.edu.itba.mygymapp.ui.routines;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.apimodels.Review;
import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.backend.repository.Status;
import ar.edu.itba.mygymapp.databinding.ActivityRoutineBinding;
import io.github.muddz.styleabletoast.StyleableToast;

public class ReviewDialog extends AppCompatDialogFragment {
    private App app;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        FragmentActivity fragmentActivity = getActivity();
        RoutineActivity routineActivity = (RoutineActivity) fragmentActivity;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.review_layout, null);

        RatingBar score = view.findViewById(R.id.reviewScore);
        EditText review = view.findViewById(R.id.reviewText);

        TextView usernameReview = view.findViewById(R.id.usernameReview);
        usernameReview.setText(app.getUserRepository().getUser().getUsername());
        ImageView userAvatarReview = view.findViewById(R.id.userAvatarReview);
        Glide.with(view.getContext()).load(app.getUserRepository().getUser().getAvatarUrl()).placeholder(R.drawable.avatar).into(userAvatarReview);

        Button cancel = view.findViewById(R.id.cancelBtn);
        Button post = view.findViewById(R.id.postBtn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                assert getArguments() != null;
                int routineId = getArguments().getInt("routineId");
                Review body = new Review((int) (score.getRating() * 2), review.getText().toString());
                app.getReviewRepository().addReview(routineId, body).observe(getActivity(), r -> {
                    if (r.getStatus() == Status.SUCCESS) {
                        app.getRoutineRepository().getRoutine(routineId).observe(fragmentActivity, r2 -> {
                            if (r2.getStatus() == Status.SUCCESS) {
                                routineActivity.setRaiting(r2.getData().getScore() / 2.0f);
                                String text = fragmentActivity.getText(R.string.review_success).toString();
                                StyleableToast.makeText(fragmentActivity, text, Toast.LENGTH_LONG, R.style.successToast).show();
                                dismiss();
                            }
                        });

                    }
                });

            }
        });

        builder.setView(view).setTitle(getText(R.string.review));
        return builder.create();
    }


}
