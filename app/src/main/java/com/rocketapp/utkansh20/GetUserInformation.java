package com.rocketapp.utkansh20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetUserInformation extends AppCompatActivity {

    GoogleSignInAccount acct;
    private static String email="";
    private static String phoneNumber="";
    private LinearLayout otp_verification = null;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 234;
    private static final int RC_HINT = 432;
    private FirebaseAuth mAuth;


    private void getUserForCourses(){
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

        branch.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                branch.setInputType(InputType.TYPE_NULL); // disable soft input
                branch.onTouchEvent(event); // call native handler
                //branch.setInputType(); // restore input type
                return true; // consume touch even
            }
        });
    }

    private void getUserForYear(){
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

        year.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                year.setInputType(InputType.TYPE_NULL); // disable soft input
                year.onTouchEvent(event); // call native handler
                //branch.setInputType(); // restore input type
                return true; // consume touch even
            }
        });
    }

    private void getUserForCollege(){
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

        college.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                college.setInputType(InputType.TYPE_NULL); // disable soft input
                college.onTouchEvent(event); // call native handler
                //branch.setInputType(); // restore input type
                return true; // consume touch even
            }
        });
    }

    private void getUserProfilePicture(final String url) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = (InputStream) new URL(url).getContent();
                    Bitmap image = BitmapFactory.decodeStream(is);
                    is.close();
                    CircleImageView circleImageView = findViewById(R.id.profile_image);
                    circleImageView.setImageBitmap(image);
                } catch(IOException e) {
                    System.out.println(e);
                }
            }
        });
    }

    private void setDataFromPhoneNumber(){
        EditText phone = findViewById(R.id.phoneNumber);
        phone.setText(phoneNumber);
        phone.setEnabled(false);
        Button button_for_phone = findViewById(R.id.button_for_phone);
        button_for_phone.setText("Verified");
        button_for_phone.setEnabled(false);
    }

    private void setDataFromGoogleAccout(){
        acct = GoogleSignIn.getLastSignedInAccount(this);
        EditText name = findViewById(R.id.name);
        name.setText(acct.getDisplayName());
        getUserProfilePicture(acct.getPhotoUrl().toString());
        EditText emailTextField = findViewById(R.id.email);
        emailTextField.setText(acct.getEmail());
        emailTextField.setEnabled(false);
        Button button_for_google = findViewById(R.id.button_for_email);
        button_for_google.setText("Verified");
        button_for_google.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_information);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getUserForCourses();
        getUserForYear();
        getUserForCollege();
        /*email=getIntent().getStringExtra("Email");
        phoneNumber=getIntent().getStringExtra("Phone Number");
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        if(!email.equals("")){
            setDataFromGoogleAccout();
        }
        else{
            setDataFromPhoneNumber();
        }*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    public void informationReceive(View view) {
        EditText name = findViewById(R.id.name);
        EditText college_name=findViewById(R.id.college_name);
        AutoCompleteTextView course = findViewById(R.id.courses);
        AutoCompleteTextView year = findViewById(R.id.year);
        if(name.getText().toString().equals("")){
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
        }
        else if(college_name.getText().toString().equals("")){
            Toast.makeText(this, "Enter your college name", Toast.LENGTH_SHORT).show();
        }
        else if(course.getText().toString().equals("")){
            Toast.makeText(this, "Select a course", Toast.LENGTH_SHORT).show();
        }
        else if(year.getText().toString().equals("")){
            Toast.makeText(this, "Select year", Toast.LENGTH_SHORT).show();
        }
        else if(phoneNumber.equals("")){
            Toast.makeText(this, "Verify your phone number", Toast.LENGTH_SHORT).show();
        }
        else if(email.equals("")){
            Toast.makeText(this, "Verify your email address", Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            // Create a new user with a first and last name
            Map<String, Object> user = new HashMap<>();
            user.put("name", name.getText().toString());
            user.put("college_name", college_name.getText().toString());
            user.put("course", course.getText().toString());
            user.put("year", year.getText().toString());
            user.put("profile_photo_url", acct.getPhotoUrl().toString());
            database.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(GetUserInformation.this, "Welcome", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(GetUserInformation.this, EventCard.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Toast.makeText(GetUserInformation.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void verify_email(View view) {
        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //storing saved gmail confirmation
                final ProgressDialog progress = new ProgressDialog(this);
                progress.setTitle("Signing you in");
                progress.setMessage("Utkansh team is waiting to receive you");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                setDataFromGoogleAccout();
                progress.cancel();

            } catch (ApiException e) {
                Toast.makeText(GetUserInformation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void verify_phone(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        View otp_view = inflater.inflate(R.layout.otp_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the
        // dialog layout
        builder.setTitle("OTP Verification");
        builder.setCancelable(false);
        builder.setView(otp_view)
                // Add action buttons
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(GetUserInformation.this, "Working on OTP", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(GetUserInformation.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();

        final EditText number = findViewById(R.id.phoneNumber);
        EditText one = otp_view.findViewById(R.id.onePass);
        EditText two = otp_view.findViewById(R.id.twoPass);
        EditText three = otp_view.findViewById(R.id.threePass);
        EditText four = otp_view.findViewById(R.id.fourPass);
        EditText five = otp_view.findViewById(R.id.fivePass);
        EditText six = otp_view.findViewById(R.id.sixPass);
        EditText arr[] = {one, two, three, four, five, six};
        final OTPVerification otpVerification = new OTPVerification("+91"+number.getText().toString().trim(), this, null, arr, null);
        otpVerification.sendVerificationCode();
    }
}
