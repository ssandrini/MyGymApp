package ar.edu.itba.mygymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import ar.edu.itba.mygymapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
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

        goToHome();
    }

    public void goToHome() {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }
}