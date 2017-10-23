package com.dheeraj.pille.minimalist;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // SAVE_KEY used to store and retrieve task data from SharedPreferences
    public static final String SAVE_KEY = "save_key";

    // Application background
    private NestedScrollView background;

    // EditText-related fields
    private EditText taskEditText;
    private String taskString;

    // RecyclerView and Adapter for Tasks
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    // ArrayList of Tasks displayed in RecyclerView
    private ArrayList<Task> tasks = new ArrayList<Task>();

    // Custom animation variables
    private Animation zoom_in, zoom_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Creates application view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SharedPreferences to store and retrieve data
        final SharedPreferences pref = getSharedPreferences(SAVE_KEY, MODE_PRIVATE);

        // SharedPreferences editor to store data
        final SharedPreferences.Editor editor =  pref.edit();

        // Gson to store and retrieve data for SharedPreferences
        final Gson gson = new Gson();

        // Places saved Task data to ArrayList from SharedPreferences
        String response = pref.getString(SAVE_KEY, "");
        tasks = gson.fromJson(response, new TypeToken<ArrayList<Task>>(){}.getType());

        // Defines various views present in application
        background = (NestedScrollView)findViewById(R.id.background);
        taskEditText = (EditText)findViewById(R.id.taskEditText);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), tasks);

        // Defines animations from res/anim/ resources
        zoom_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);

        // Sets LayoutManager and Adapter for RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recyclerAdapter);

        // Removes focus from EditText when clicking on background
        // TODO: remove focus from EditText when clicking on recyclerView
        background.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.requestFocusFromTouch();
                return false;
            }
        });

        // Sets a focus listener on EditText for changes
        taskEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            // Overrides standard FocusChange function
            @Override
            public void onFocusChange(View view, boolean b) {

                // Checks if EditText is in focus
                if (b) {

                    // Removes plus icon on drawableLeft
                    taskEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_add_black_24dp, 0);

                    // Starts zoom_in animation
                    taskEditText.startAnimation(zoom_in);

                    // Adds elevation to create shadow effect for EditText
                    taskEditText.setElevation(25);

                } else {

                    // Starts zoom_out animation
                    taskEditText.startAnimation(zoom_out);

                    // Removes shadow from EditText
                    taskEditText.setElevation(0);

                    // Adds plus icon on drawableLeft
                    taskEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_black_24dp, 0, 0 ,0);

                    // Hides soft keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(taskEditText.getWindowToken(), 0);
                }
            }
        });

        // Sets an editor action listener for EditText done input
        taskEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            // Overrides standard onEditorAction function
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                // Checks if the action is DONE
                // NOTE: replaces return/enter key with done key on soft keyboard
                if (i == EditorInfo.IME_ACTION_DONE) {

                    // Gets string value from EditText
                    // TODO: check for whitespace being entered
                    taskString = taskEditText.getText().toString();

                    // Clears EditText field and focus
                    taskEditText.getText().clear();
                    taskEditText.clearFocus();

                    // Makes sure string is not empty
                    if (!taskString.isEmpty()) {

                        // Adds inputted task to beginning of taskArrayList
                        tasks.add(0, new Task(taskString, false));
                        recyclerAdapter.notifyItemInserted(0);

                        // Refreshes RecyclerView to use standard insert animation
                        recyclerView.setAdapter(recyclerAdapter);

                        // Converts tasks as JSON
                        String json = gson.toJson(tasks);

                        // Removes old JSON
                        editor.remove(SAVE_KEY).commit();

                        // Inserts new JSON
                        editor.putString(SAVE_KEY, json);
                        editor.commit();

                        return true;
                    }
                }

                return false;
            }
        });


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {

                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();

                // Removes task from view with swipe offscreen animation
                tasks.remove(position);
                recyclerAdapter.notifyItemRemoved(position);

                String json = gson.toJson(tasks);

                editor.remove(SAVE_KEY).commit();
                editor.putString(SAVE_KEY, json);
                editor.commit();

            }
        };

        // Adds ItemTouchHelper defined above for swiping task items
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}