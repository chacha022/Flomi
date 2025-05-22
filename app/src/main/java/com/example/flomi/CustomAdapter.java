package com.example.flomi;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.flomi.data.Product;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Product> localDataSet;

    public CustomAdapter(List<Product> dataSet) {
        this.localDataSet = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView tvCompany, tvName, tvEfficacy1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.iv_profile);
            tvCompany = itemView.findViewById(R.id.tv_enter);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEfficacy1 = itemView.findViewById(R.id.tv_efficacy1);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = localDataSet.get(position);

        // 이미지 셋팅 (drawable id가 문자열로 저장된 경우)
        holder.ivProfile.setImageResource(product.getImageResId());

        // 텍스트 셋팅
        holder.tvCompany.setText(product.getCompany());
        holder.tvName.setText(product.getName());

        // efficacy가 "보습,트러블" 식으로 콤마로 연결된 경우 첫 번째 효능만 표시
        String efficacy = product.getEfficacy();  // 그대로 가져오기
        holder.tvEfficacy1.setText(efficacy);


        // 아이템 클릭 시 상세 화면 이동 (예시)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Detail.class);
            intent.putExtra("company", product.getCompany());
            intent.putExtra("name", product.getName());
            intent.putExtra("efficacy", efficacy);
            intent.putExtra("imageResId", product.getImageResId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

