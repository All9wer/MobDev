package ru.mirea.chirkovia.firebaseauth;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.chirkovia.firebaseauth.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.emailCreateAccountButton.setOnClickListener(v ->
                createAccount(
                        binding.fieldEmail.getText().toString(),
                        binding.fieldPassword.getText().toString()
                ));

        binding.emailSignInButton.setOnClickListener(v ->
                signIn(
                        binding.fieldEmail.getText().toString(),
                        binding.fieldPassword.getText().toString()
                ));

        binding.signOutButton.setOnClickListener(v -> signOut());

        binding.verifyEmailButton.setOnClickListener(v -> sendEmailVerification());
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password) {
        if (!validateForm(email, password)) return;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        updateUI(mAuth.getCurrentUser());
                    } else {
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void signIn(String email, String password) {
        if (!validateForm(email, password)) return;

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        updateUI(mAuth.getCurrentUser());
                    } else {
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        binding.verifyEmailButton.setEnabled(false);

        user.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    binding.verifyEmailButton.setEnabled(true);
                    if (task.isSuccessful()) {
                        Toast.makeText(this,
                                "Verification email sent to " + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateForm(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            binding.fieldEmail.setError("Required.");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            binding.fieldPassword.setError("Required.");
            return false;
        }
        return true;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            binding.status.setText("Signed In");
            binding.detail.setText("UID: " + user.getUid());

            binding.emailCreateAccountButton.setVisibility(View.GONE);
            binding.emailSignInButton.setVisibility(View.GONE);
            binding.signOutButton.setVisibility(View.VISIBLE);
            binding.verifyEmailButton.setVisibility(
                    user.isEmailVerified() ? View.GONE : View.VISIBLE
            );
        } else {
            binding.status.setText("Signed Out");
            binding.detail.setText("Firebase User");

            binding.emailCreateAccountButton.setVisibility(View.VISIBLE);
            binding.emailSignInButton.setVisibility(View.VISIBLE);
            binding.signOutButton.setVisibility(View.GONE);
            binding.verifyEmailButton.setVisibility(View.GONE);
        }
    }
}
