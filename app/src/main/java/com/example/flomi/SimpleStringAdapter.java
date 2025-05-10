package com.example.flomi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class SimpleStringAdapter extends RecyclerView.Adapter<SimpleStringAdapter.ViewHolder>{

    private ArrayList<String> mData;

    public SimpleStringAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
        }

        public void bind(String text) {
            textView.setText(text);
        }
    }

    @NonNull
    @Override
    public SimpleStringAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_string, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleStringAdapter.ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.bind(text);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

