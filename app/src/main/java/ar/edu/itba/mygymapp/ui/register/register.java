package ar.edu.itba.mygymapp.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ar.edu.itba.mygymapp.MainActivity;
import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.apimodels.RegisterCredentials;
import ar.edu.itba.mygymapp.backend.repository.Resource;
import ar.edu.itba.mygymapp.backend.repository.Status;
import ar.edu.itba.mygymapp.databinding.ActivityRegisterBinding;

public class register extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    View root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.registerBtn.setOnClickListener(this::registration);
        root=binding.getRoot();
        DisplayMetrics displayMetrics= root.getContext().getResources().getDisplayMetrics();
        float dpWidth=displayMetrics.widthPixels/displayMetrics.density;
        float dpHeight=displayMetrics.heightPixels/displayMetrics.density;
        if(dpWidth>=700){
            binding.registerBtn.setTextSize(23);
            binding.username.setTextSize(23);
            binding.editTextTextEmailAddress.setTextSize(23);
            binding.password.setTextSize(23);
            binding.password2.setTextSize(23);

        }
    }

    private void registration(View view) {
        EditText userView,mailView, passView, confirmPass;
        String username, password, password2,mail;
        boolean error=false;
        userView=binding.username;
        passView=binding.password;
        mailView= binding.editTextTextEmailAddress;
        confirmPass=binding.password2;
        username=userView.getText().toString();
        mail=mailView.getText().toString();
        password=passView.getText().toString();
        password2=confirmPass.getText().toString();

        if(username.trim().length()==0){
            userView.setError(getText(R.string.invalid_username));
            error = true;
        }
        if(mail.trim().length()==0|| !Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            mailView.setError(getText(R.string.invalid_email));
            error = true;
        }
        if (password.trim().length() == 0) {
            passView.setError(getText(R.string.invalid_password));
            error = true;
        }
        if(password2.trim().length()==0){
            confirmPass.setError(getText(R.string.invalid_password));
        }else {
            if (password.compareTo(password2) != 0) {
                confirmPass.setError(getText(R.string.invalid_password_confirmation));
                error = true;
            }
        }
        if (error) {
            Toast.makeText(getApplicationContext(), getText(R.string.invalid_registration), Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterCredentials registerCredentials = new RegisterCredentials(username, password,"-","-","other",mail,"https://flic.kr/p/3ntH2u");
        App app = (App) getApplication();
        app.getUserRepository().register(registerCredentials).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                Toast.makeText(getApplicationContext(),getText(R.string.register_success),Toast.LENGTH_LONG).show();
                goToConfirmationActivity();
            } else {
                Resource.defaultResourceHandler(r);
                if (r.getStatus() == Status.ERROR) {
                    Toast.makeText(getApplicationContext(), getText(R.string.existing_account), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void goToConfirmationActivity() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }
}
