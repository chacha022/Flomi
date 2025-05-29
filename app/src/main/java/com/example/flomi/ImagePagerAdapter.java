package com.example.flomi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;
    private AppDatabase db;  // DB 인스턴스 추가

    public ImagePagerAdapter(Context context, List<Product> products, AppDatabase db) {
        this.context = context;
        this.products = products;
        this.db = db;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewPagerItem);
        }
    }

    @NonNull
    @Override
    public ImagePagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_slide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagePagerAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        String filename = product.getImage();

        // assets/picture 폴더에서 이미지 불러오기
        try {
            InputStream is = context.getAssets().open("picture/" + filename);
            Drawable drawable = Drawable.createFromStream(is, null);
            holder.imageView.setImageDrawable(drawable);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            holder.imageView.setImageDrawable(null);
        }

        // 클릭 시 Detail 액티비티로 상품 id 전달 후 이동 및 현재 시각 업데이트
        holder.imageView.setOnClickListener(v -> {
            product.createdAt = new Date();

            new Thread(() -> db.productDao().updateProduct(product)).start();

            Intent intent = new Intent(context, Detail.class);
            intent.putExtra("id", product.getProductId());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }
}
