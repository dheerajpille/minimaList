package com.dheeraj.pille.minimalist;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.MotionEvent;
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

        // TODO: implement saving and getting data

        // Defines animations from res/anim/ resources
        zoom_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);

        taskEditText = (EditText)findViewById(R.id.taskEditText);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);


        // Initial hint state for EditText
        // TODO: remove this when it's intuitive
        taskEditText.setHint("Add task");

        // Sets a focus listener for changes
        taskEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            // Overrides standard FocusChange function
            @Override
            public void onFocusChange(View view, boolean b) {

                // Checks if EditText is in focus
                if (b) {
                    // Starts zoom_in animation\
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

                        // Adds inputted task to beginning of taskArrayList
                        tasks.add(0, new Task(taskString, false));

                        // Refreshes RecyclerView
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), tasks);

                        // This is the thing that actually refreshes RecyclerView
                        recyclerView.setAdapter(recyclerAdapter);

                        Toast.makeText(getBaseContext(), taskString, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }

                Toast.makeText(getBaseContext(), "Empty", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getApplicationContext(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(getApplicationContext(), "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                final int position = viewHolder.getAdapterPosition();

                // TODO: refresh recyclerView so that it shrinks on deletion.
                tasks.remove(position);
                recyclerAdapter.notifyItemRemoved(position);
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}