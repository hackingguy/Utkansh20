package com.rocketapp.utkansh20;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class getClubEventsData {

    private FirebaseUser user;
    private Context context;
    private String temp_data;

    getClubEventsData(Context context) {
        this.context = context;
    }

    public String get_data(final String club, final String data) {
        FirebaseFirestore.getInstance().collection("club_events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println(document.getId() + " => " + document.getString("name"));
                        Map<String, Object> map = document.getData();
                        System.out.println(map);
                    }
                }
            }
        });

        return temp_data;
    }
}