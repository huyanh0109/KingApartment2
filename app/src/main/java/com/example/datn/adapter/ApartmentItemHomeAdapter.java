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
import com.example.datn.fragment.FragmentHome;
import com.example.datn.model.ResultApartment;

import java.text.DecimalFormat;
import java.util.List;

public class ApartmentItemHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == LAYOUT_ONE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_apartment, parent, false);
            viewHolder =  new ViewHolder(view);
        }else {
            View viewPlus = LayoutInflater.from(context).inflate(R.layout.item_recycler_fill, parent, false);
            viewHolder =  new ViewHolderPlus(viewPlus);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ResultApartment resultApartment = list.get(position);
        if (holder.getItemViewType() == LAYOUT_ONE) {
            ViewHolder viewHolder =(ViewHolder) holder;
            viewHolder.tv_rcv_itemhome_name.setText(resultApartment.getName());
            switch (viewType) {
                case 1:
                    viewHolder.tv_rcv_itemhome_addess.setText(resultApartment.getAddress());
                    break;
                case 2:
                    viewHolder.tv_rcv_itemhome_addess.setText(resultApartment.getDistance() + "Km");
                    break;
                case 3:
                    viewHolder.tv_rcv_itemhome_addess.setText(resultApartment.getAddress());
                    viewHolder.ic_wishlist_home.setBackgroundResource(R.drawable.ic_wishlist);
                    break;
            }
            DecimalFormat formatterPrice = new DecimalFormat("#,##");
            double formatPrice = Double.parseDouble(this.list.get(position).getPrice()) / 10000;
            viewHolder.tv_rcv_itemhome_price.setText(formatterPrice.format(formatPrice) + "tr");
            if (this.list.get(position).getCreateBy().equals("admin")) {
                viewHolder.tv_rcv_itemhome_desciption.setText("King Mall");
            } else {
                viewHolder.tv_rcv_itemhome_desciption.setText("Shoper");
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
                    R.drawable.animation_loading).error(R.drawable.ic_error_img).into(viewHolder.img_rcv_itemhome);
//        Drawable drawable = holder.img_rcv_itemhome.getDrawable();
//        if (drawable instanceof AnimatedVectorDrawableCompat) {
//            AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) drawable;
//            avd.start();
//        }else if (drawable instanceof  AnimatedVectorDrawable){
//            AnimatedVectorDrawable adv = (AnimatedVectorDrawable) drawable;
//            adv.start();
//        }
        }else {
            ViewHolderPlus holderPlus = (ViewHolderPlus) holder;
            holderPlus.tv_rcv_name_wishlist.setText(resultApartment.getName());
            holderPlus.tv_rcv_address_wishlist.setText(resultApartment.getAddress());
            DecimalFormat formatterPrice = new DecimalFormat("#,##");
            double formatPrice = Double.parseDouble(this.list.get(position).getPrice()) / 10000;
            holderPlus.tv_rcv_price_wishlist.setText(formatterPrice.format(formatPrice) + "tr");
            if (this.list.get(position).getCreateBy().equals("admin")) {
                holderPlus.tv_rcv_description_wishlist.setText("King Mall");
            } else {
                holderPlus.tv_rcv_description_wishlist.setText("Shoper");
            }
            holderPlus.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dataApartment", resultApartment);
                    Navigation.findNavController(v).navigate(R.id.action_fragmentDaddy_to_fragmentIndex, bundle);
                }
            });
            Glide.with(context).load(this.list.get(position).getPhotos().get(0)).centerCrop().placeholder(
                    R.drawable.animation_loading).error(R.drawable.ic_error_img).into(holderPlus.img_rcv_item_wishlist);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if(viewType == FragmentHome.VIEWTYPE_FULL)
            return LAYOUT_TWO;
        else
            return LAYOUT_ONE;
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
        ImageView img_rcv_itemhome,ic_wishlist_home;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_rcv_itemhome_desciption = itemView.findViewById(R.id.tv_rcv_description);
            tv_rcv_itemhome_addess = itemView.findViewById(R.id.tv_rcv_address);
            tv_rcv_itemhome_price = itemView.findViewById(R.id.tv_rcv_price);
            tv_rcv_itemhome_name = itemView.findViewById(R.id.tv_rcv_name);
            img_rcv_itemhome = itemView.findViewById(R.id.img_rcv_item);
            ic_wishlist_home = itemView.findViewById(R.id.ic_wishlist_home);
        }
    }
    public class ViewHolderPlus extends RecyclerView.ViewHolder {
        TextView tv_rcv_description_wishlist, tv_rcv_price_wishlist, tv_rcv_name_wishlist,
                tv_rcv_address_wishlist;
        ImageView img_rcv_item_wishlist;
        public ViewHolderPlus(@NonNull View itemView) {
            super(itemView);
            tv_rcv_description_wishlist = itemView.findViewById(R.id.tv_rcv_description_wishlist);
            tv_rcv_price_wishlist = itemView.findViewById(R.id.tv_rcv_price_wishlist);
            tv_rcv_name_wishlist = itemView.findViewById(R.id.tv_rcv_name_wishlist);
            tv_rcv_address_wishlist = itemView.findViewById(R.id.tv_rcv_address_wishlist);
            img_rcv_item_wishlist = itemView.findViewById(R.id.img_rcv_item_wishlist);
        }
    }
}
