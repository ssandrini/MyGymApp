package ar.edu.itba.mygymapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import ar.edu.itba.mygymapp.MainActivity;
import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.databinding.ActivityLoginBinding;
import ar.edu.itba.mygymapp.ui.register.register;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginBtn.setOnClickListener(this::login);
        binding.registerBtn.setOnClickListener(this::goToRegister);
    }

    public void login(View view) {
        EditText userView, passView;
        String username, password;
        boolean error = false;

        userView = binding.username;
        passView = binding.password;
        username = userView.getText().toString();
        password = passView.getText().toString();

        if (username.trim().length() == 0) {
            userView.setError(getText(R.string.invalid_username));
            error = true;
        }
        if (password.trim().length() == 0) {
            passView.setError(getText(R.string.invalid_password));
            error = true;
        }
        if (error) {
            Toast.makeText(getApplicationContext(), getText(R.string.invalid_login), Toast.LENGTH_SHORT).show();
            return ;
        }

        goToMainActivity();
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }
}