package ar.edu.itba.mygymapp.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import ar.edu.itba.mygymapp.MainActivity;
import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.apimodels.EmailConfirmation;
import ar.edu.itba.mygymapp.backend.repository.Resource;
import ar.edu.itba.mygymapp.backend.repository.Status;
import ar.edu.itba.mygymapp.databinding.ActivityConfirmationBinding;
import ar.edu.itba.mygymapp.ui.login.LoginActivity;

public class ConfirmationActivity extends AppCompatActivity {

    private ActivityConfirmationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityConfirmationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.confirmButton.setOnClickListener(this::confirm);
    }

    public void confirm(View view) {
        EditText emailView, codeView;
        String email, code;
        boolean error = false;

        emailView = binding.email;
        codeView = binding.code;
        email = emailView.getText().toString();
        code = codeView.getText().toString();


        if (email.trim().length() == 0) {
            emailView.setError(getText(R.string.invalid_email));
            error = true;
        }
        if (code.trim().length() == 0) {
            codeView.setError(getText(R.string.invalid_code));
            error = true;
        }

        if (error) {
            Toast.makeText(getApplicationContext(), getText(R.string.invalid_confirmation), Toast.LENGTH_SHORT).show();
            return ;
        }

        App app = (App)getApplication();
        app.getUserRepository().verifyEmail(new EmailConfirmation(email,code)).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                Toast.makeText(getApplicationContext(),getText(R.string.success_verification),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
            } else {
                Resource.defaultResourceHandler(r);
                if (r.getStatus() == Status.ERROR) {
                    Toast.makeText(getApplicationContext(), getText(R.string.fail_verification), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

}
