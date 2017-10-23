package com.dheeraj.pille.minimalist;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    // SAVE_KEY used to store and retrieve task data from SharedPreferences
    public static final String SAVE_KEY = "save_key";

    private Context context;
    private ArrayList<Task> taskArrayList;

    public RecyclerAdapter(Context c, ArrayList<Task> tal) {
        this.context = c;
        this.taskArrayList = tal;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView taskText;

        public ViewHolder(View v) {
            super(v);
            taskText = v.findViewById(R.id.taskText);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // Gets initial position of tasks
        final Task task = taskArrayList.get(position);

        holder.taskText.setText(task.getText());

        if (task.getChecked()) {

            // Icon made by Google from www.flaticon.com
            holder.taskText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_box_black_24dp, 0, 0, 0);

        } else {

            // Icon made by Google from www.flaticon.com
            holder.taskText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_box_outline_blank_black_24dp, 0, 0, 0);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // SharedPreferences to store and retrieve data
                SharedPreferences pref = context.getSharedPreferences(SAVE_KEY, context.MODE_PRIVATE);

                // SharedPreferences editor to store data
                SharedPreferences.Editor editor =  pref.edit();

                // Gson to store and retrieve data for SharedPreferences
                Gson gson = new Gson();

                task.toggleChecked();

                // Checks if task has toggled to true
                if (task.getChecked()) {

                    taskArrayList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());

                    taskArrayList.add(task);
                    notifyItemInserted(getItemCount() - 1);


                } else {

                    taskArrayList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());

                    taskArrayList.add(0, task);
                    notifyItemInserted(0);

                }

                // Converts tasks as JSON
                String json = gson.toJson(taskArrayList);

                // Removes old JSON
                editor.remove(SAVE_KEY).commit();

                // Inserts new JSON
                editor.putString(SAVE_KEY, json);
                editor.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }
}