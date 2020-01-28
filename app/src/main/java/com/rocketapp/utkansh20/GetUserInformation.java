package com.rocketapp.utkansh20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class GetUserInformation extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_information);
        getUserForCourses();
        getUserForYear();
    }
}
