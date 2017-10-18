package com.dheeraj.pille.minimalist;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TaskViewHolder> {

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView taskText;
        // TODO: add checked

        TaskViewHolder(View v) {
            super(v);
            cardView = (CardView)v.findViewById(R.id.cardView);
            taskText = (TextView)v.findViewById(R.id.taskText);
        }
    }
    ArrayList<Task> tasks;

    RecyclerAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.)
    }


}
