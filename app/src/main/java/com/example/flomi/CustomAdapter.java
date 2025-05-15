package com.example.flomi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Product> localDataSet;

    // ===== 뷰홀더 클래스 =====================================================
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView tvCompany, tvName, tvEfficacy1, tvEfficacy2, tvEfficacy3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.iv_profile);
            tvCompany = itemView.findViewById(R.id.tv_enter);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEfficacy1 = itemView.findViewById(R.id.tv_efficacy1);
            tvEfficacy2 = itemView.findViewById(R.id.tv_efficacy2);
            tvEfficacy3 = itemView.findViewById(R.id.tv_efficacy3);
        }
    }
    // ========================================================================

    // ----- 생성자 --------------------------------------
    public CustomAdapter(ArrayList<Product> dataSet) {
        localDataSet = dataSet;
    }
    // --------------------------------------------------

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);  // XML 파일명
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = localDataSet.get(position);
        holder.ivProfile.setImageResource(product.getImg());
        holder.tvCompany.setText(product.getCompany());
        holder.tvName.setText(product.getName());
        holder.tvEfficacy1.setText(product.getEfficacy1());
        holder.tvEfficacy2.setText(product.getEfficacy2());
        holder.tvEfficacy3.setText(product.getEfficacy3());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

