package com.example.projectmanagementapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.core.content.res.ResourcesCompat;

import com.example.projectmanagementapp.data.remote.model.AuthResponse;
import com.example.projectmanagementapp.data.remote.repository.AuthRepository;
import com.example.projectmanagementapp.state.UserState;
import com.example.projectmanagementapp.ui.NavigationActivity;
import com.example.projectmanagementapp.ui.auth.SignUpActivity;
import com.example.projectmanagementapp.utils.TokenManager;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // Declare variables for binding and navigation controller


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this line is added to force light mode on the app Not to use dark mode to make simpler to develop just for light mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        // Retrieving the email, password, loginButton and signUpButton from the UI
        final EditText EmailEditText =  findViewById(R.id.email_edit_text);
        final EditText passwordEditText = findViewById(R.id.password_edit_text);
        final Button loginButton = findViewById(R.id.login_button);
        final Button signUpButton = findViewById(R.id.sign_up_button);

        final TokenManager tokenManager = new TokenManager(this);
        final AuthRepository authRepository = new AuthRepository();





        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(final View v) {
                final String email = EmailEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();

                // plz just comment this code don't remove it
                // final Intent homeIntent = new Intent(MainActivity.this, NavigationActivity.class);
                // startActivity(homeIntent);


                authRepository.login(email, password).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            String token = response.body().getToken();
                            if (token != null && !token.isEmpty()) {
                                tokenManager.saveToken(token,response.body());
                                Log.d(token, "Login Success: " + token);
                                Log.d("LoginActivity", "Login Success: " + response.body());
                                UserState.getInstance().setUser(response.body().getUser());
                                final Intent homeIntent = new Intent(MainActivity.this, NavigationActivity.class);
                                startActivity(homeIntent);
                                Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("LoginActivity", "Token missing in successful response");
                                Toast.makeText(MainActivity.this, "Unexpected server response", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            try {
                                assert response.errorBody() != null;
                                String errorBody = response.errorBody().string();
                                Log.e("LoginActivity", "Login Failed - Error Body: " + errorBody);
                            } catch (Exception e) {
                                Log.e("LoginActivity", "Login Failed - Error Reading Error Body", e);
                            }
                            Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                        Log.e("LoginActivity", "Network error: " + t.getMessage(), t);
                        Toast.makeText(MainActivity.this, "Network error occurred. Please check your connection.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        // Routing to sign Up page on click
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signUpIntent);

            }
        });

        // For showing an hiding the password in the UI
        setupPasswordVisibilityToggle(passwordEditText);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setupPasswordVisibilityToggle(final EditText passwordEditText) {
        final boolean[] isPasswordVisible = { false };

        // Set a touch listener on the drawableEnd (eye icon)
        passwordEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2; // Index for right drawable

            // Check if touch event is on the drawable
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordEditText.getRight()
                        - passwordEditText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {

                    // Toggle password visibility
                    if (isPasswordVisible[0]) {
                        // Hide password
                        passwordEditText
                                .setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                R.drawable.baseline_visibility_24, 0); // Use closed-eye icon
                    } else {
                        // Show password
                        passwordEditText.setInputType(
                                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                R.drawable.baseline_visibility_off_24, 0); // Use open-eye icon
                    }
                    passwordEditText.setTypeface(ResourcesCompat.getFont(this, R.font.work_sans_regular));

                    // Reset cursor to end of text
                    passwordEditText.setSelection(passwordEditText.getText().length());

                    // Toggle visibility flag
                    isPasswordVisible[0] = !isPasswordVisible[0];
                    return true;
                }
            }
            return false;
        });
    }
}