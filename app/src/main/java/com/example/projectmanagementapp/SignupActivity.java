package com.example.projectmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.EditText;


public class SignupActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup_page_acitivity);

        loginButton = findViewById(R.id.login_button);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SignupActivity.this,MainActivity.class);
                startActivity(loginIntent);
            }
        });
        setupPasswordVisibilityToggle(passwordEditText);
        setupPasswordVisibilityToggle(confirmPasswordEditText);
        

    }

    public void setupPasswordVisibilityToggle(final EditText passwordEditText) {
        // Initial state of password visibility
        final boolean[] isPasswordVisible = {false};

        // Set a touch listener on the drawableEnd (eye icon)
        passwordEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2; // Index for right drawable

            // Check if touch event is on the drawable
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {

                    // Toggle password visibility
                    if (isPasswordVisible[0]) {
                        // Hide password
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_24, 0); // Use closed-eye icon
                    } else {
                        // Show password
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0); // Use open-eye icon
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
