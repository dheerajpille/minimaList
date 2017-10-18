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
            taskText = (TextView)v.findViewById(R.id.taskText);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Task task = taskArrayList.get(position);
        holder.taskText.setText(task.getText());
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }
}