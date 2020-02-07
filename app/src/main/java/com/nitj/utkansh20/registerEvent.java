package com.nitj.utkansh20;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class registerEvent {
    private Context context;
    String all_events;
    Map<String, Object> events = new HashMap<>();

    registerEvent(final Context context, final String string, final String uid) {
        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    all_events = documentSnapshot.getString("events");
                    if (all_events.length() == 2) {
                        all_events = all_events.replace("}", string + "}");
                        events.put("events", all_events);
                    } else {
                        all_events = all_events.replace("}", "," + string + "}");
                        events.put("events", all_events);
                    }
                    Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    database.collection("users").document(uid).set(events, SetOptions.merge());
                }
            }
        });
    }
}