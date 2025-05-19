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

        // 클릭 리스너 추가
        holder.itemView.setOnClickListener(view -> {
            // 클릭된 아이템의 데이터 가져오기
            Product clickedProduct = localDataSet.get(position);

            // Intent를 생성하여 데이터 전달
            Intent intent = new Intent(view.getContext(), Detail.class);
            intent.putExtra("company", clickedProduct.getCompany());
            intent.putExtra("name", clickedProduct.getName());
            intent.putExtra("efficacy1", clickedProduct.getEfficacy1());
            intent.putExtra("efficacy2", clickedProduct.getEfficacy2());
            intent.putExtra("efficacy3", clickedProduct.getEfficacy3());

            // 이미지도 전달 (Optional)
            intent.putExtra("image", clickedProduct.getImg());

            // 다른 액티비티로 이동
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

