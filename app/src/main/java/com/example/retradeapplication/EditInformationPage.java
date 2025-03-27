package com.example.retradeapplication;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class EditInformationPage extends AppCompatActivity {

    private EditText nameInput, emailInput, contactInput;
    private Spinner genderSpinner;
    Button updateButton;
    FirebaseAuth auth;
    private FirebaseUser user;
    FirebaseFirestore db;
    private DocumentReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_information_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_information_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditInformationPage.this, ProfilePage.class);
            startActivity(intent);
        });

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        if (user != null) {
            userRef = db.collection("Users").document(user.getUid());
        }

        // Initialize UI Elements
        nameInput = findViewById(R.id.edit_name);
        emailInput = findViewById(R.id.edit_email);
        contactInput = findViewById(R.id.edit_contact);
        genderSpinner = findViewById(R.id.gender_spinner);
        updateButton = findViewById(R.id.update_details_button);

        // Make Email Field Non-Editable
        emailInput.setEnabled(false);

        // Populate Gender Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Load user data (name and email from Firebase Auth, gender & contact from Firestore)
        loadUserData();

        // Update button action
        updateButton.setOnClickListener(v -> updateUserInfo());

        // Bottom Navigation
        findViewById(R.id.home_button).setOnClickListener(v -> navigateTo(HomePageActivity.class));
        findViewById(R.id.sell_button).setOnClickListener(v -> navigateTo(SellingPage.class));
        findViewById(R.id.profile_button).setOnClickListener(v -> navigateTo(ProfilePage.class));
    }

    private void loadUserData() {
        // Load Name & Email from Firebase Auth
        if (user != null) {
            nameInput.setText(user.getDisplayName() != null ? user.getDisplayName() : "");
            emailInput.setText(user.getEmail() != null ? user.getEmail() : "");
        }

        // Load Gender & Contact from Firestore
        if (userRef != null) {
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    // Get stored gender and contact, if available
                    String savedGender = documentSnapshot.contains("gender") ? documentSnapshot.getString("gender") : null;
                    String savedContact = documentSnapshot.contains("contact") ? documentSnapshot.getString("contact") : null;

                    // Set Gender (Only if it's stored)
                    if (savedGender != null) {
                        int spinnerPosition = ((ArrayAdapter<String>) genderSpinner.getAdapter()).getPosition(savedGender);
                        genderSpinner.setSelection(spinnerPosition);
                    }

                    // Set Contact (Only if it's stored)
                    contactInput.setText(savedContact != null ? savedContact : "");

                } else {
                    // If no document exists, do nothing (gender & contact remain blank)
                    Toast.makeText(EditInformationPage.this, "No additional user data found.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e ->
                    Toast.makeText(EditInformationPage.this, "Some fields are empty", Toast.LENGTH_SHORT).show());
        }
    }

    private void updateUserInfo() {
        String newName = nameInput.getText().toString().trim();
        String newGender = genderSpinner.getSelectedItem().toString();
        String newContact = contactInput.getText().toString().trim();

        if (TextUtils.isEmpty(newName) || TextUtils.isEmpty(newContact)) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update user profile name in Firebase Authentication
        user.updateProfile(new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditInformationPage.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditInformationPage.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });

        // Save gender and contact number to Firestore
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("name", newName);
        userUpdates.put("email", user.getEmail());
        userUpdates.put("gender", newGender);
        userUpdates.put("contact", newContact);

        userRef.set(userUpdates, com.google.firebase.firestore.SetOptions.merge())
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(EditInformationPage.this, "Details updated successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(EditInformationPage.this, "Failed to update details!", Toast.LENGTH_SHORT).show());

        Intent intent = new Intent(EditInformationPage.this, ProfilePage.class);
        startActivity(intent);
    }

    private void navigateTo(Class<?> destination) {
        startActivity(new Intent(EditInformationPage.this, destination));
    }
}
