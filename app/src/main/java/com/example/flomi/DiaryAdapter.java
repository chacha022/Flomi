package com.example.flomi;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {

    private List<DiaryItem> diaryList;

    public DiaryAdapter(List<DiaryItem> diaryList) {
        this.diaryList = diaryList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView numTextView;
        TextView titleTextView;
        TextView contentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numTextView = itemView.findViewById(R.id.num);
            titleTextView = itemView.findViewById(R.id.title);
            contentTextView = itemView.findViewById(R.id.content);
        }
    }

    @NonNull
    @Override
    public DiaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.ViewHolder holder, int position) {
        DiaryItem item = diaryList.get(position);
        holder.numTextView.setText(item.getNumber());
        holder.titleTextView.setText(item.getTitle());
        holder.contentTextView.setText(item.getContent());

        // 클릭 이벤트 추가
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DiaryDetail.class);
            intent.putExtra("number", item.getNumber());
            intent.putExtra("title", item.getTitle());
            intent.putExtra("content", item.getContent());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }
}