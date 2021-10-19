package com.example.todolistwithloginregister.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistwithloginregister.R;
import com.example.todolistwithloginregister.UpdateTaskAct;
import com.example.todolistwithloginregister.model.Task;
import com.example.todolistwithloginregister.utils.DatabaseHandler;

import java.util.ArrayList;

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.ListViewHolder> {
    Task task;
    ArrayList<Task> listTask;
    DatabaseHandler db;

    public ListTaskAdapter(ArrayList<Task> Task, DatabaseHandler databaseHandler) {
        this.db = databaseHandler;
        this.listTask = Task;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_does, parent,
                false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Task task = listTask.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UpdateTaskAct.class);
                intent.putExtra("id", task.getId());
                intent.putExtra("title", task.getTitledoes());
                intent.putExtra("desc", task.getDescdoes());
                intent.putExtra("date", task.getDatedoes());
                v.getContext().startActivity(intent);
                v.getContext().stopService(intent);
            }
        });

        holder.titledoes.setText(task.getTitledoes());
        holder.descdoes.setText(task.getDescdoes());
        holder.datedoes.setText(task.getDatedoes());
    }

    @Override
    public int getItemCount() {
        return listTask.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        TextView titledoes, descdoes, datedoes;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            titledoes = (TextView) itemView.findViewById(R.id.titledoes);
            descdoes = (TextView) itemView.findViewById(R.id.descdoes);
            datedoes = (TextView) itemView.findViewById(R.id.datedoes);
        }
    }
}