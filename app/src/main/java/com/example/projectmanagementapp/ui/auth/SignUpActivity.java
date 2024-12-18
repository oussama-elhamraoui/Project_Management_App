package com.example.projectmanagementapp.ui.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.data.remote.model.AuthResponse;
import com.example.projectmanagementapp.data.remote.repository.AuthRepository;
import com.example.projectmanagementapp.models.UserTheme;
import com.example.projectmanagementapp.state.UserState;
import com.example.projectmanagementapp.ui.NavigationActivity;
import com.example.projectmanagementapp.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);


        AuthRepository authRepository = new AuthRepository();
        final TokenManager tokenManager = new TokenManager(this);


        //we are just collecting the first last and email and password for the UI and when the button is clicked we send the post request to the backend
        Button signUpButton = findViewById(R.id.sign_up_button);
        firstNameEditText = findViewById(R.id.first_name_edit_text);
        lastNameEditText = findViewById(R.id.last_name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);

        //just for the password visibility
        setupPasswordVisibilityToggle(passwordEditText);
        setupPasswordVisibilityToggle(confirmPasswordEditText);

        signUpButton.setOnClickListener(v -> {
            final String firstName = firstNameEditText.getText().toString().trim();
            final String lastName = lastNameEditText.getText().toString().trim();
            final String email = emailEditText.getText().toString().trim();
            final String password = passwordEditText.getText().toString();
            final String confirmPassword = confirmPasswordEditText.getText().toString();
            final int color = UserTheme.randomColor();

            if (password.equals(confirmPassword)) {
                authRepository.signUp(firstName, lastName, email, password, color).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                        if (response.body() != null) {
                            String token = response.body().getToken();

                            tokenManager.saveToken(token,response.body());
                            Log.d(token, "Login Success: " + token);
                            Log.d("LoginActivity", "Login Success: " + response.body());
                            UserState.getInstance().setUser(response.body().getUser());
                            final Intent homeIntent = new Intent(SignUpActivity.this, NavigationActivity.class);
                            startActivity(homeIntent);
                            Toast.makeText(SignUpActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                            //! why not just throw him in the home screen

                        } else {
                            // Failure: Display an error message based on the response
                            String errorMessage = "Failed to register. Error: " + response.message();
                            Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                        String failureMessage = "Registration failed: " + t.getMessage();
                        Log.e("LoginActivity", "Login Failed - Error Body: " + t.getMessage());
                        Toast.makeText(SignUpActivity.this, failureMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setupPasswordVisibilityToggle(final EditText passwordEditText) {
        final boolean[] isPasswordVisible = {false};
        passwordEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                    if (isPasswordVisible[0]) {
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_24, 0);
                    } else {
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0);
                    }

                    passwordEditText.setTypeface(ResourcesCompat.getFont(this, R.font.work_sans_regular));
                    passwordEditText.setSelection(passwordEditText.getText().length());

                    isPasswordVisible[0] = !isPasswordVisible[0];
                    return true;
                }
            }
            return false;
        });
    }
}
