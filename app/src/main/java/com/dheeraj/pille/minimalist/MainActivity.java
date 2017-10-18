package com.dheeraj.pille.minimalist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText taskEditText;
    private String taskString;

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    ArrayList<Task> tasks = new ArrayList<Task>();

    private Animation zoom_in, zoom_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Creates application view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks.add(new Task("cool", false));

        tasks.add(new Task("cool", false));

        tasks.add(new Task("cool", false));

        tasks.add(new Task("cool", false));

        // Defines animations from res/anim/ resources
        zoom_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);

        taskEditText = (EditText)findViewById(R.id.taskEditText);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), tasks);

        recyclerView.setAdapter(recyclerAdapter);

        // Initial hint state for EditText
        taskEditText.setHint("Add task");

        // Sets a focus listener for changes
        taskEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            // Overrides standard FocusChange function
            @Override
            public void onFocusChange(View view, boolean b) {

                // Checks if EditText is in focus
                if (b) {

                    // Starts zoom_in animation
                    taskEditText.startAnimation(zoom_in);

                    // Adds elevation to create shadow on EditText
                    taskEditText.setElevation(50);
                } else {

                    // Hint that appears when EditText is not in focus
                    taskEditText.setHint("Add task");

                    // Starts zoom_out animation
                    taskEditText.startAnimation(zoom_out);

                    // Removes shadow from EditText
                    taskEditText.setElevation(0);

                    // Hides soft keyboard from view
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(taskEditText.getWindowToken(), 0);
                }
            }
        });

        // Sets an editor action listener for done input
        taskEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            // Overrides standard onEditorAction function
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                // Checks if the action is DONE
                // NOTE: replaces return/enter key with done key on soft keyboard
                if (i == EditorInfo.IME_ACTION_DONE) {

                    // Gets string value from EditText
                    taskString = taskEditText.getText().toString();

                    // Clears EditText field and focus
                    taskEditText.getText().clear();
                    taskEditText.clearFocus();

                    // Returns appropriate Toast response
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
