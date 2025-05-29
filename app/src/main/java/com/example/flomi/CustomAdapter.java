package com.example.flomi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flomi.data.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

        // assets/picture/에서 이미지 로드
        try {
            String imageFileName = product.getImage();  // 예: "sample1.png"
            InputStream is = holder.itemView.getContext().getAssets().open("picture/" + imageFileName);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            holder.ivProfile.setImageBitmap(bitmap);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            holder.ivProfile.setImageResource(R.drawable.light); // 기본 이미지
        }

        // 텍스트 설정
        holder.tvCompany.setText(product.getCompany());
        holder.tvName.setText(product.getName());
        holder.tvEfficacy1.setText(product.getEfficacy());

        // 아이템 클릭 시 Detail 화면으로 이동
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(product);  // 클릭된 제품 객체 전달
            }

            // 기존 Detail 화면 이동 로직 유지
            Intent intent = new Intent(v.getContext(), Detail.class);
            intent.putExtra("company", product.getCompany());
            intent.putExtra("name", product.getName());
            intent.putExtra("efficacy", product.getEfficacy());
            intent.putExtra("image", product.getImage());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
