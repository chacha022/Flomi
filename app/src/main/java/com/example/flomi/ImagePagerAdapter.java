package com.example.flomi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ViewHolder> {

    private Context context;
    private List<String> imageFileNames;

    public ImagePagerAdapter(Context context, List<String> imageFileNames) {
        this.context = context;
        this.imageFileNames = imageFileNames;
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
        String filename = imageFileNames.get(position);
        try {
            InputStream is = context.getAssets().open("picture/" + filename);
            Drawable drawable = Drawable.createFromStream(is, null);
            holder.imageView.setImageDrawable(drawable);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            holder.imageView.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return imageFileNames.size();
    }
}
