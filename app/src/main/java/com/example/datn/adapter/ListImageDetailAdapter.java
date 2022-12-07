package com.example.datn.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ListImageDetailAdapter extends RecyclerView.Adapter<ListImageDetailAdapter.ViewHolder> {
    List<String> resultPopular;
    Context context;

    public ListImageDetailAdapter(Context context, List<String>  resultPopular){
        this.context = context;
        this.resultPopular = resultPopular;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listimage_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String result = resultPopular.get(position);
            Glide.with(context).load(result).centerCrop().placeholder(
                    R.drawable.animation_loading).error(R.drawable.ic_error_img).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_detail_image);
                ImageView imageDialog = dialog.findViewById(R.id.image_detail_dialog);
                Glide.with(context).load(result).centerCrop().placeholder(
                        R.drawable.animation_loading).error(R.drawable.ic_error_img).into(imageDialog);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultPopular.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_listimage_detail);
        }
    }{

    }
}
