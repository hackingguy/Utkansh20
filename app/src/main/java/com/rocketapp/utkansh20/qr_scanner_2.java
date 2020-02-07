package com.rocketapp.utkansh20;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;

public class qr_scanner_2 extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private LinearLayout qr_information;
    private FrameLayout frameLayout;
    private String event;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner_2);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 302);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        qr_information = findViewById(R.id.qr_information);
        frameLayout = findViewById(R.id.qr_scaaner);
        qr_information.setTranslationY(1200f);
        mCodeScanner = new CodeScanner(this, scannerView);
        ImageView logo = findViewById(R.id.logo_qr);
        logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in_out));
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                try {
                    FirebaseFirestore.getInstance().collection("users").document(result.getText()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();
                                String events = snapshot.getString("events");
                                try {
                                    event = "Game Number 1";
                                    events.contains(event);
                                    Toast.makeText(qr_scanner_2.this, "Registered in "+event, Toast.LENGTH_SHORT).show();
                                    //Logic Of Moving The Dialog Up And Down



                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(qr_scanner_2.this, "User Not Found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}