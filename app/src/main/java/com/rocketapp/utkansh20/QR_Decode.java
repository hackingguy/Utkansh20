package com.rocketapp.utkansh20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QR_Decode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__decode);

        new IntentIntegrator(this).initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result =   IntentIntegrator.parseActivityResult(requestCode, resultCode, data);            if (result != null) {
        if (result.getContents() == null) {
            Toast.makeText(this,    "Cancelled",Toast.LENGTH_LONG).show();
        } else {
            updateText(result.getContents());
        }
    } else {
        super.onActivityResult(requestCode, resultCode, data);
    }
    }

    private void updateText(String scanCode) {
        FirebaseFirestore.getInstance().collection("users").document(scanCode).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    Intent intent = new Intent(QR_Decode.this, QRScan_Result.class);
                    intent.putExtra("name",snapshot.getString("name"));
                    intent.putExtra("number",snapshot.getString("phoneNumber"));
                    System.out.println(snapshot.getString("name")+"----------------here----------------"+snapshot.getString("phoneNumber"));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}


