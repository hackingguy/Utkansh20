package com.rocketapp.utkansh20;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPVerification {
    private Context context;
    private FirebaseAuth auth;
    private String phoneNumber = null;
    private String verificationID;
    private EditText one, two, three, four, five, six;
    private Intent intent;
    private String CODE = "000000";
    private boolean otp_verification_status = false;
    private PhoneAuthProvider.ForceResendingToken resend_token;
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    OTPVerification(String number, Context context, FirebaseAuth auth, EditText arr[], Intent intent) {
        phoneNumber = number;
        this.context = context;
        this.auth = auth;
        one = arr[0];
        two = arr[1];
        three = arr[2];
        four = arr[3];
        five = arr[4];
        six = arr[5];
        this.intent = intent;
    }

    public void sendVerificationCode() {
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                resend_token = forceResendingToken;
                Toast.makeText(context, "OTP sent", Toast.LENGTH_SHORT).show();
                verificationID = s;

            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(context, "Failed To Verify The Code." + e.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("-----------------------------" + e.getMessage());
            }
        };

        System.out.println("---------------------------------------------" + phoneNumber);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                (Activity) context,
                mCallback
        );
        six.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(six.getText().toString().equals("") && five.getText().toString().equals("") && four.getText().toString().equals("") && four.getText().toString().equals("") && three.getText().toString().equals("") && two.getText().toString().equals("") && one.getText().toString().equals(""))) {
                    String code = one.getText().toString() + two.getText().toString() + three.getText().toString() + four.getText().toString() + five.getText().toString() + six.getText().toString();
                    verifyCode(code);
                    System.out.println("---------------------Calling extra verification  ^^^^^^^^^^^^^^^^^^^^^^");
                } else {
                    Toast.makeText(context, "Enter Correct OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void resendVerificationCode(String phoneNumber,
                                       PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                (Activity) context,               // Activity (for callback binding)
                mCallback,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        CODE = code;
        signInWithCredentials(credential);
    }

    public PhoneAuthProvider.ForceResendingToken resendToken() {
        return resend_token;
    }

    private void signInWithCredentials(PhoneAuthCredential credential) {

        if (intent != null) {
            auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        fillTheAutomaticOTP(CODE);
                        intent.putExtra("phoneNumber",phoneNumber);
                        context.startActivity(intent);
                        Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(context, "noop noop", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {

            fillTheAutomaticOTP(CODE);
            Toast.makeText(context, "Verified", Toast.LENGTH_SHORT).show();
            otp_verification_status = true;
        }
    }

    private void fillTheAutomaticOTP(String code) {
        try {
            one.setText("" + code.charAt(0));
            two.setText("" + code.charAt(1));
            three.setText("" + code.charAt(2));
            four.setText("" + code.charAt(3));
            five.setText("" + code.charAt(4));
            six.setText("" + code.charAt(5));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Auto-Detection failed!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public boolean checkVerification() {
        return otp_verification_status;
    }
}