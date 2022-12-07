package com.example.datn.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn.R;
import com.example.datn.model.ResultApartment;

import java.text.DecimalFormat;
import java.util.List;

public class ApartmentItemHomeAdapter extends RecyclerView.Adapter<ApartmentItemHomeAdapter.ViewHolder> {
    private final Context context;
    private List<ResultApartment> list;
    private final Double userLongitude;
    private final Double userLatitude;
    private final Integer viewType;

    public ApartmentItemHomeAdapter(Context context, List<ResultApartment> list, Double userLongitude,
                                    Double userLatitude, Integer viewType) {
        this.context = context;
        this.list = list;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.viewType = viewType;
    }

    public void setApartment(List<ResultApartment> resultPopulate) {
        this.list = resultPopulate;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_apartment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultApartment resultApartment = list.get(position);
        holder.tv_rcv_itemhome_name.setText(resultApartment.getName());
        switch (viewType) {
            case 1:
                holder.tv_rcv_itemhome_addess.setText(resultApartment.getAddress());
                break;
            case 2:
                holder.tv_rcv_itemhome_addess.setText(resultApartment.getDistance() + "Km");
                break;
        }
        DecimalFormat formatterPrice = new DecimalFormat("#,##");
        double formatPrice = Double.parseDouble(this.list.get(position).getPrice()) / 10000;
        holder.tv_rcv_itemhome_price.setText(formatterPrice.format(formatPrice) + "tr");
        if (this.list.get(position).getCreateBy().equals("admin")) {
            holder.tv_rcv_itemhome_desciption.setText("King Mall");
        } else {
            holder.tv_rcv_itemhome_desciption.setText("Shoper");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataApartment", resultApartment);
                Navigation.findNavController(v).navigate(R.id.action_fragmentDaddy_to_fragmentIndex, bundle);
            }
        });
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
            tv_rcv_itemhome_desciption = itemView.findViewById(R.id.tv_rcv_description);
            tv_rcv_itemhome_addess = itemView.findViewById(R.id.tv_rcv_address);
            tv_rcv_itemhome_price = itemView.findViewById(R.id.tv_rcv_price);
            tv_rcv_itemhome_name = itemView.findViewById(R.id.tv_rcv_name);
            img_rcv_itemhome = itemView.findViewById(R.id.img_rcv_item);
        }
    }
}
