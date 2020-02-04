package com.rocketapp.utkansh20;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GetUserInformation extends AppCompatActivity {

    private static String phoneNumber;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;


    private void getUserForCourses() {
        final AutoCompleteTextView branch = findViewById(R.id.courses);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getResources()
                .getStringArray(R.array.Courses));
        String selection;
        branch.setAdapter(arrayAdapter);
        branch.setCursorVisible(false);
        branch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                branch.showDropDown();
                String selection = (String) parent.getItemAtPosition(position);
            }
        });

        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                branch.showDropDown();
            }
        });

        branch.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                branch.setInputType(InputType.TYPE_NULL); // disable soft input
                branch.onTouchEvent(event); // call native handler
                //branch.setInputType(); // restore input type
                return true; // consume touch even
            }
        });
    }

    private void getUserForYear() {
        final AutoCompleteTextView year = findViewById(R.id.year);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getResources()
                .getStringArray(R.array.Year));
        String selection;
        year.setAdapter(arrayAdapter);
        year.setCursorVisible(false);
        year.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                year.showDropDown();
                String selection = (String) parent.getItemAtPosition(position);
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                year.showDropDown();
            }
        });

        year.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                year.setInputType(InputType.TYPE_NULL); // disable soft input
                year.onTouchEvent(event); // call native handler
                //branch.setInputType(); // restore input type
                return true; // consume touch even
            }
        });
    }

    private void getUserForCollege() {
        final AutoCompleteTextView college = findViewById(R.id.college_name);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getResources()
                .getStringArray(R.array.College));
        String selection;
        college.setAdapter(arrayAdapter);
        college.setCursorVisible(false);
        college.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                college.showDropDown();
                String selection = (String) parent.getItemAtPosition(position);
            }
        });

        college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                college.showDropDown();
            }
        });

        college.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                college.setInputType(InputType.TYPE_NULL); // disable soft input
                college.onTouchEvent(event); // call native handler
                //branch.setInputType(); // restore input type
                return true; // consume touch even
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_information);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        System.out.println("------------------------------------------->>>>>>>>>" + phoneNumber);
        StrictMode.setThreadPolicy(policy);
        getUserForCourses();
        getUserForYear();
        getUserForCollege();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void informationReceive(View view) {
        String uid;
        EditText name = findViewById(R.id.name);
        EditText college_name = findViewById(R.id.college_name);
        AutoCompleteTextView course = findViewById(R.id.courses);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser User = mAuth.getCurrentUser();
        if (User != null) {
            uid = User.getUid();
        } else {
            uid = null;
            Toast.makeText(getApplicationContext(), "Fucked Up", Toast.LENGTH_SHORT).show();
        }
        AutoCompleteTextView year = findViewById(R.id.year);
        if (name.getText().toString().equals("")) {
            name.setError("Enter Name");
            name.requestFocus();
        } else if (college_name.getText().toString().equals("")) {
            college_name.setError("Enter College Name");
            college_name.requestFocus();
        } else if (course.getText().toString().equals("")) {
            course.setError("Choose a Course");
            course.requestFocus();
        } else if (year.getText().toString().equals("")) {
            year.setError("Choose Year");
            year.requestFocus();
        } else {
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            // Create a new user with a first and last name
            Map<String, Object> user = new HashMap<>();
            user.put("name", name.getText().toString());
            user.put("college_name", college_name.getText().toString());
            user.put("course", course.getText().toString());
            user.put("year", year.getText().toString());
            user.put("events", "{}");
            user.put("phoneNumber", phoneNumber);
            database.collection("users").document(uid)
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(GetUserInformation.this, HomeActivity.class));
                            Toast.makeText(GetUserInformation.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GetUserInformation.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}