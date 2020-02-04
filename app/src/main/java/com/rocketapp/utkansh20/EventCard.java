package com.rocketapp.utkansh20;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class EventCard extends AppCompatActivity {

    private ListView listView;
    private Context context;
    private String events;
    private eventAdapter event_Adapter;
    private ArrayList<String> event_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_card);
        listView = findViewById(R.id.events_list);
        context = EventCard.this;
        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot result = task.getResult();
                    events = result.getString("events");
                    event_Adapter = new eventAdapter();
                    event_list = new ArrayList<>(Arrays.asList(events.split(",")));
                    listView.setAdapter(event_Adapter);
                } else {
                    Toast.makeText(context, "Error Fetching Cards", Toast.LENGTH_SHORT).show();
                    events = "-1";
                }
            }
        });
    }

    class eventAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return event_list.size();
        }

        @Override
        public Object getItem(int position) {
            return event_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.event_cards, null);
            TextView event_name = view.findViewById(R.id.event_text);
            if (position == 0) {
                event_list.get(position).replace("{", "");
            }
            if (position == event_list.size() - 1) {
                event_list.get(position).replace("}", "");
            }
            event_name.setText(event_list.get(position));
            return view;
        }
    }
}