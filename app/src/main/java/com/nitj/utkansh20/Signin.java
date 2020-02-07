package com.nitj.utkansh20;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shobhitpuri.custombuttons.GoogleSignInButton;


public class Signin extends AppCompatActivity {

    private LinearLayout otp_verification = null;
    GoogleSignInClient mGoogleSignInClient;
    private static boolean suggested = false;
    private static final int RC_SIGN_IN = 234;
    private static final int RC_HINT = 432;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private static String phoneNumber = null;
    EditText one, two, three, four, five, six;
    TextView resendOTP;

    public void verify(View view) {
        final EditText phone_number = findViewById(R.id.phoneNumber);
        resendOTP = findViewById(R.id.resend);
        if (phone_number.getText().toString().length() == 10) {
            LinearLayout number = findViewById(R.id.number);
            number.setVisibility(View.INVISIBLE);
            otp_verification.animate().translationYBy(-1200f).setDuration(500);
            phoneNumber = phone_number.getText().toString();
            EditText arr[] = {one, two, three, four, five, six};
            Intent intent = new Intent(Signin.this, GetUserInformation.class);
            intent.putExtra("Email", "");
            intent.putExtra("Phone Number", phoneNumber);
            final OTPVerification otpVerification = new OTPVerification("+91" + phoneNumber, this, mAuth, arr, intent);
            otpVerification.sendVerificationCode();
            resendOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Signin.this, "OTP Resent!", Toast.LENGTH_SHORT).show();
                    otpVerification.resendVerificationCode("+91" + phoneNumber, otpVerification.resendToken());
                }
            });
        } else if (phone_number.getText().toString().length() == 0) {
            Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
        }
    }

    public void allNormal(View view) {
        final GoogleSignInButton googleSignInButton = findViewById(R.id.googleSignInButton);
        final LinearLayout number = findViewById(R.id.number);
        int visibility = googleSignInButton.getVisibility();
        if (visibility == View.INVISIBLE) {
            ValueAnimator va = ValueAnimator.ofFloat(0f, 1200f);
            int mDuration = 500; //in millis
            va.setDuration(mDuration);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    otp_verification.setTranslationY((float) animation.getAnimatedValue());
                }
            });
            va.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    googleSignInButton.setVisibility(View.VISIBLE);
                    number.setVisibility(View.VISIBLE);
                }
            });
            va.start();
        }
    }

    public void nothing(View view) {
    }

    private void setListenerToAllEditTextOfOTP() {
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        one.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (one.getText().toString().equals("")) {
                    return;
                }
                two.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });

        two.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (two.getText().toString().equals("")) {
                    one.requestFocus();
                    return;
                }
                three.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });

        three.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (three.getText().toString().equals("")) {
                    two.requestFocus();
                    return;
                }
                four.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });

        four.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (four.getText().toString().equals("")) {
                    three.requestFocus();
                    return;
                }
                five.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });

        five.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (five.getText().toString().equals("")) {
                    four.requestFocus();
                    return;
                }
                six.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });


        six.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (six.getText().toString().equals("")) {
                    five.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void signIn() {
        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_HINT) {
            if (resultCode == RESULT_OK) {
                EditText number = findViewById(R.id.phoneNumber);
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                number.setText(credential.getId().substring(3));
                verify(null);
            } else {
                suggested = true;
            }
        }
    }

    private void showNumbers() {
        EditText number = findViewById(R.id.phoneNumber);
        number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (suggested)
                        return;
                    GoogleApiClient googleApiClient = new GoogleApiClient.Builder(Signin.this)
                            .addApi(Auth.CREDENTIALS_API)
                            .build();
                    googleApiClient.connect();
                    HintRequest hintRequest = new HintRequest.Builder()
                            .setPhoneNumberIdentifierSupported(true)
                            .build();
                    PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);
                    try {
                        startIntentSenderForResult(intent.getIntentSender(),
                                RC_HINT, null, 0, 0, 0);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth.getInstance().signOut();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid());
            setContentView(R.layout.activity_signin);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Signin.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 4000);
        } else {

            setContentView(R.layout.activity_signup);
            showNumbers();
            FirebaseApp.initializeApp(this);
            mAuth = FirebaseAuth.getInstance();
            otp_verification = findViewById(R.id.otp_verification);
            otp_verification.setTranslationY(1200f);
            //GoogleSignInButton googleSignInButton = findViewById(R.id.googleSignInButton);
            LinearLayout number = findViewById(R.id.number);
            //googleSignInButton.setTranslationY(1200f);
            number.setTranslationY(1200f);
            //googleSignInButton.setVisibility(View.VISIBLE);
            number.setVisibility(View.VISIBLE);
            //googleSignInButton.animate().translationYBy(-1200f).setDuration(800 + 1500);
            number.animate().translationYBy(-1200f).setDuration(1500 + 1500);
            //Set listener to the OTP fields
            setListenerToAllEditTextOfOTP();
        }

    }
}