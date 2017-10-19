package com.dheeraj.pille.minimalist;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

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
        final Task task = taskArrayList.get(position);
        holder.taskText.setText(task.getText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView taskText = view.findViewById(R.id.taskText);
                // Toggles task to be opposite of current checked status
                task.toggleChecked();

                // Checks if task has toggled to true
                if (task.getChecked()) {

                    taskArrayList.remove(position);

                    taskArrayList.add(task);

                    taskText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_box_black_24dp, 0, 0, 0);

                    notifyDataSetChanged();

                } else {

                    taskArrayList.remove(position);

                    taskArrayList.add(0, task);

                    taskText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_box_outline_blank_black_24dp, 0, 0, 0);

                    notifyDataSetChanged();


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }
}