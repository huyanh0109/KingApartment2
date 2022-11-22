package com.example.datn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn.R;
import com.example.datn.model.ResultPopular;

import java.util.List;

public class ApartmentItemHomeAdapter extends RecyclerView.Adapter<ApartmentItemHomeAdapter.ViewHolder> {
    private Context context;
    private List<ResultPopular> list;

    public ApartmentItemHomeAdapter(Context context, List<ResultPopular> list) {
        this.context = context;
        this.list = list;
    }

    public void setApartment(List<ResultPopular> resultPopulate) {
        this.list = resultPopulate;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycle_apartment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_rcv_itemhome_name.setText(this.list.get(position).getName());
        holder.tv_rcv_itemhome_addess.setText(this.list.get(position).getAddress());
        holder.tv_rcv_itemhome_price.setText(this.list.get(position).getPrice());
        holder.tv_rcv_itemhome_desciption.setText(this.list.get(position).getDescription());
        Glide.with(context).load(this.list.get(position).getPhotos().get(0)).centerCrop().placeholder(
                R.drawable.animation_loading).error(R.drawable.ic_error_img).into(holder.img_rcv_itemhome);
//        Drawable drawable = holder.img_rcv_itemhome.getDrawable();
//        if (drawable instanceof AnimatedVectorDrawableCompat) {
//            AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) drawable;
//            avd.start();
//        }else if (drawable instanceof  AnimatedVectorDrawable){
//            AnimatedVectorDrawable adv = (AnimatedVectorDrawable) drawable;
//            adv.start();
//        }
    }


    @Override
    public int getItemCount() {
        if (this.list != null) {
            Log.i("TAG", "list.size: " + list.size());
            return this.list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_rcv_itemhome_desciption, tv_rcv_itemhome_price, tv_rcv_itemhome_name,
                tv_rcv_itemhome_addess;
        ImageView img_rcv_itemhome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_rcv_itemhome_desciption = (TextView) itemView.findViewById(R.id.tv_rcv_description);
            tv_rcv_itemhome_addess = itemView.findViewById(R.id.tv_rcv_address);
            tv_rcv_itemhome_price = itemView.findViewById(R.id.tv_rcv_price);
            tv_rcv_itemhome_name = itemView.findViewById(R.id.tv_rcv_name);
            img_rcv_itemhome = itemView.findViewById(R.id.img_rcv_item);
        }
    }
}
