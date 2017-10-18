package com.dheeraj.pille.minimalist;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText taskEditText;
    private String taskString;

    private Animation zoom_in, zoom_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zoom_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);

        taskEditText = (EditText)findViewById(R.id.taskEditText);

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

                    // Adds elevation to create shadow on EditText
                    taskEditText.setElevation(50);
                } else {
                    // Hint that appears when EditText is not in focus
                    taskEditText.setHint("Add task");

                    taskEditText.startAnimation(zoom_out);

                    // Removes shadow from EditText
                    taskEditText.setElevation(0);
                }
            }
        });

        taskEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    taskString = taskEditText.getText().toString();

                    // Clears EditText field and focus
                    taskEditText.getText().clear();
                    taskEditText.clearFocus();

                    if (!taskString.isEmpty()) {
                        Toast.makeText(getBaseContext(), taskString, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }

                Toast.makeText(getBaseContext(), "Empty", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
