package com.dheeraj.pille.minimalist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText taskEditText;
    private ImageButton taskAddButton;
    private String taskString;

    private Animation zoom_in, zoom_out, slide_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zoom_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        slide_left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);

        taskEditText = (EditText)findViewById(R.id.taskEditText);
        taskAddButton = (ImageButton)findViewById(R.id.taskAddButton);

        // Initial hint state for EditText
        taskEditText.setHint("Add task");

        // Sets a focus listener for changes
        taskEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            // Overrides standard FocusChange function
            @Override
            public void onFocusChange(View view, boolean b) {

                // Checks if EditText is in focus
                // Sets button visibility/enabled status accordingly
                if (b) {
                    taskEditText.startAnimation(zoom_in);
                    taskEditText.setElevation(25);
                    taskAddButton.setVisibility(View.VISIBLE);
                    taskAddButton.setEnabled(true);
                } else {
                    // Hint that appears when EditText is not in focus
                    taskEditText.setHint("Add task");
                    taskEditText.setElevation(0);
                    taskAddButton.setVisibility(View.GONE);
                    taskAddButton.setEnabled(false);
                    taskEditText.startAnimation(zoom_out);
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
