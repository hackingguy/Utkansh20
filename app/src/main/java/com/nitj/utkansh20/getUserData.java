package com.nitj.utkansh20;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class getUserData {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String uid;
    private Context context;
    private String temp_data;

    public ArrayList<String[]> data;

    getUserData(Context context) {
        this.context = context;
    }

    public String get_data(final String data) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        assert user != null;
        uid = user.getUid();
        FirebaseFirestore.getInstance().collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot result = task.getResult();
                    temp_data = result.getString(data);
                } else {
                    Toast.makeText(context, "Error Fetching Cards", Toast.LENGTH_SHORT).show();
                    temp_data = "-1";
                }
            }
        });
        return temp_data;
    }
}