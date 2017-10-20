package com.dheeraj.pille.minimalist;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        // Gets initial position of tasks
        final Task task = taskArrayList.get(position);

        holder.taskText.setText(task.getText());

        if (task.getChecked()) {

            // Icon made by Google from www.flaticon.com
            holder.taskText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick_inside_circle, 0, 0, 0);
        } else {

            // Icon made by Google from www.flaticon.com
            holder.taskText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_circle_outline, 0, 0, 0);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

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
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }
}