package com.example.datn.adapter;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
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

public class ApartmentItemWishListAdapter extends RecyclerView.Adapter<ApartmentItemWishListAdapter.ViewHolder> {
    private Context context;
    private List<ResultApartment> list;
    public ApartmentItemWishListAdapter(Context context, List<ResultApartment> list) {
        this.context = context;
        this.list = list;
    }
    public void setApartment(List<ResultApartment> resultPopulate) {
        this.list = resultPopulate;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_wishlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultApartment resultApartment = list.get(position);
        holder.tv_rcv_itemhome_name.setText(resultApartment.getName());
        holder.tv_rcv_itemhome_addess.setText(resultApartment.getAddress());
        DecimalFormat formatterPrice = new DecimalFormat("#,##");
        double formatPrice  =Double.parseDouble(this.list.get(position).getPrice())/10000;
        holder.tv_rcv_itemhome_price.setText(formatterPrice.format(formatPrice)+"tr");
        if (this.list.get(position).getCreateBy().equals("admin")) {
            holder.tv_rcv_itemhome_desciption.setText("King Mall");
        }else {
            holder.tv_rcv_itemhome_desciption.setText("Shoper");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataApartment", resultApartment);
                Navigation.findNavController(v).navigate(R.id.action_fragmentDaddy_to_fragmentIndex,bundle);
            }
        });
        Glide.with(context).load(this.list.get(position).getPhotos().get(0)).centerCrop().placeholder(
                R.drawable.animation_loading).error(R.drawable.ic_error_img).into(holder.img_rcv_itemhome);
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
            tv_rcv_itemhome_desciption = (TextView) itemView.findViewById(R.id.tv_rcv_description_wishlist);
            tv_rcv_itemhome_addess = itemView.findViewById(R.id.tv_rcv_address_wishlist);
            tv_rcv_itemhome_price = itemView.findViewById(R.id.tv_rcv_price_wishlist);
            tv_rcv_itemhome_name = itemView.findViewById(R.id.tv_rcv_name_wishlist);
            img_rcv_itemhome = itemView.findViewById(R.id.img_rcv_item_wishlist);
        }
    }
}
