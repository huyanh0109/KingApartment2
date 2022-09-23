package com.example.datn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.R;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.ViewHolder> {
    Context context;
    public IntroAdapter (Context context){
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  =LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_intro,parent,false);

        return new IntroAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (position){
            case 0:
                holder.tv_title.setText(R.string.title_intro_1);
                holder.tv_content.setText(R.string.content_intro);
                break;
            case 1:
                holder.tv_title.setText("@string/title_intro_1");
                holder.tv_content.setText("@string/content_intro2");
                break;
            case 2:
                holder.tv_title.setText("@string/title_intro_1");
                holder.tv_content.setText("@string/content_intro");
                break;
        }
    }


    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        AppCompatTextView tv_title,tv_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title_intro);
            tv_content = itemView.findViewById(R.id.tv_content_intro);
        }
    }
}
