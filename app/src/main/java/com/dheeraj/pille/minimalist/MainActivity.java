package com.dheeraj.pille.minimalist;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText taskEditText;
    private Button taskAddButton;
    private String taskString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskEditText = (EditText)findViewById(R.id.taskEditText);
        taskAddButton = (Button)findViewById(R.id.taskAddButton);

        taskEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Toast.makeText(getBaseContext(), "Focused", Toast.LENGTH_SHORT).show();
                    taskAddButton.setVisibility(View.VISIBLE);
                    taskAddButton.setEnabled(true);
                } else {
                    Toast.makeText(getBaseContext(), "Not focused", Toast.LENGTH_SHORT).show();
                    taskAddButton.setVisibility(View.GONE);
                    taskAddButton.setEnabled(false);
                }
            }
        });

        // Sets a button listener for clicks
        taskAddButton.setOnClickListener(new View.OnClickListener() {

            // Processes when button is clicked
            public void onClick(View v) {

                // Get string value from EditText
                taskString = taskEditText.getText().toString();

                // Clears EditText field and focus
                taskEditText.getText().clear();
                taskEditText.clearFocus();

                if (taskString.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), taskString, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
