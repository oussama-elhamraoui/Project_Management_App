package com.example.projectmanagementapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends AppCompatActivity {
    private Button signUpButton;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signUpButton = findViewById(R.id.sign_up_button);
        passwordEditText = findViewById(R.id.password_edit_text);
        final Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(final View v) {
                final Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(signupIntent);

            }
        });
        setupPasswordVisibilityToggle(passwordEditText);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setupPasswordVisibilityToggle(final EditText passwordEditText) {
        boolean[] isPasswordVisible = { false };

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