package ar.edu.itba.mygymapp.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ar.edu.itba.mygymapp.MainActivity;
import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.databinding.ActivityRegisterBinding;

public class register extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.registerBtn.setOnClickListener(this::registration);
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

        goToConfirmationActivity();
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToConfirmationActivity() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }
}
