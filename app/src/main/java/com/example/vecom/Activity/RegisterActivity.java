package com.example.vecom.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vecom.Model.Product;
import com.example.vecom.Model.User;
import com.example.vecom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.graphics.Color;


public class RegisterActivity extends AppCompatActivity {
    private TextView birthdayTextView;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        // Add this in your MainActivity or Application class
        FirebaseApp.initializeApp(this);


        ImageView backArrow = findViewById(R.id.back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        birthdayTextView = findViewById(R.id.birthdayTextView);
        birthdayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Initialize Spinner
        Spinner genderSpinner = findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Set up a listener for the Spinner to handle the selected gender
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedGender = parentView.getItemAtPosition(position).toString();
                // You can use the selectedGender as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        RelativeLayout signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserToDatabase();
            }
        });
    }

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog and set the current date as the default
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the TextView
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        birthdayTextView.setText(selectedDate);
                        birthdayTextView.setTextColor(Color.parseColor("#000000"));

                    }
                },
                year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }

    private void saveUserToDatabase() {
        EditText phoneEditText = findViewById(R.id.phoneEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText fullnameEditText = findViewById(R.id.fullnameEditText);
        TextView dobEditText = findViewById(R.id.birthdayTextView);
        Spinner genderSpinner = findViewById(R.id.genderSpinner);
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        String phoneNumber = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String fullname = fullnameEditText.getText().toString();
        String dob = dobEditText.getText().toString();
        String selectedGender = genderSpinner.getSelectedItem().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Create a User object
        User newUser = new User(
                username,
                password,
                email,
                fullname,
                dob,
                selectedGender,
                phoneNumber
        );

        // Get a reference to the Firebase database
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.push().setValue(newUser, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registration successful
                                    Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish(); // Close the current activity
                                } else {
                                    // Registration failed
                                    Exception e = task.getException();
                                    Toast.makeText(RegisterActivity.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                // Registration failed
                Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                Log.e("FirebaseError", "Error writing to Firebase", databaseError.toException());
            }
        });

    }
}