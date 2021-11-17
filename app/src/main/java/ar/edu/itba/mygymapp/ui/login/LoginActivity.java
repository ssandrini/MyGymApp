package ar.edu.itba.mygymapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import ar.edu.itba.mygymapp.backend.apimodels.Error;
import ar.edu.itba.mygymapp.MainActivity;
import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.apimodels.Credentials;
import ar.edu.itba.mygymapp.backend.apimodels.FullUser;
import ar.edu.itba.mygymapp.backend.repository.Resource;
import ar.edu.itba.mygymapp.backend.repository.Status;
import ar.edu.itba.mygymapp.databinding.ActivityLoginBinding;
import ar.edu.itba.mygymapp.ui.register.register;
import io.github.muddz.styleabletoast.StyleableToast;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SharedPreferences sharedPreferences;
    private View root;
    private App app;
    private EditText passEditText;
    private boolean passwordVisible = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        root=binding.getRoot();

        app = (App)getApplication();


        if (sharedPreferences.getBoolean("logged", false)) {
            getCurrentUser();
        }

        DisplayMetrics displayMetrics= root.getContext().getResources().getDisplayMetrics();
        float dpWidth=displayMetrics.widthPixels/displayMetrics.density;
        float dpHeight=displayMetrics.heightPixels/displayMetrics.density;
        Log.d("tamaño:",String.valueOf(dpWidth));
        if(dpWidth>=700){
            binding.loginBtn.setTextSize(23);
            binding.registerBtn.setTextSize(23);
            binding.textView.setTextSize(20);
            binding.username.setTextSize(23);
            binding.password.setTextSize(23);

        }
        binding.loginBtn.setOnClickListener(this::login);
        binding.registerBtn.setOnClickListener(this::goToRegister);

        passEditText = binding.password;
        passEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= passEditText.getRight() - passEditText.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = passEditText.getSelectionEnd();
                        if(passwordVisible) {
                            passEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            passEditText.setTransformationMethod(new PasswordTransformationMethod());
                            passwordVisible=false;
                        }else {
                            passEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            passEditText.setTransformationMethod(null);
                            passwordVisible=true;
                        }
                        passEditText.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
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
        if (password.trim().length() == 0 ) {
            passView.setError(getText(R.string.invalid_password));
            error = true;
        }

        if (error) {
            StyleableToast.makeText(getApplicationContext(), getText(R.string.invalid_login).toString(), Toast.LENGTH_LONG, R.style.errorToast).show();
            return ;
        }

        Credentials credentials = new Credentials(username, password);

        app.getUserRepository().login(credentials).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                app.getPreferences().setAuthToken(r.getData().getToken());
                sharedPreferences.edit().putBoolean("logged",true).apply();

                getCurrentUser();

            } else {
                defaultResourceHandler(r);
                if (r.getStatus() == Status.ERROR) {
                    StyleableToast.makeText(getApplicationContext(), getText(R.string.invalid_login).toString(), Toast.LENGTH_LONG, R.style.errorToast).show();
                }
            }
        });

    }

    private void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                Log.d("LOGIN", "CARGANDO LOGIN");
                break;
            case ERROR:
                Error error = resource.getError();
                assert error != null;
                Log.d("LOGIN", error.getDescription() + error.getCode() + "");
                break;
        }
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }

    public void getCurrentUser() {
        app.getUserRepository().getCurrentUser().observe(this, res -> {
            if (res.getStatus() == Status.SUCCESS) {
                FullUser user;
                user = res.getData();
                app.getUserRepository().setUser(user);
                goToMainActivity();
                finish();
            } else {
                Resource.defaultResourceHandler(res);
            }
        });
    }
}