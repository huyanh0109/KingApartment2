package com.example.datn.adapter;

import android.annotation.SuppressLint;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn.R;
import com.example.datn.api.APIClient;
import com.example.datn.api.APIservice;
import com.example.datn.model.Message;
import com.example.datn.model.ResultApartment;
import com.example.datn.viewmodel.RemoveWishListViewModel;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class ApartmentItemWishListAdapter extends RecyclerView.Adapter<ApartmentItemWishListAdapter.ViewHolder> {
    private Context context;
    private List<ResultApartment> list;
    String email;
    OnClickItemApartment onClickItemApartment;
    public ApartmentItemWishListAdapter(Context context, List<ResultApartment> list,String email,OnClickItemApartment onClickItemApartment) {
        this.context = context;
        this.list = list;
        this.email = email;
        this.onClickItemApartment = onClickItemApartment;
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
    public interface OnClickItemApartment{
        void onClickDetailItemApartment(Bundle bundle);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
                onClickItemApartment.onClickDetailItemApartment(bundle);
//                Navigation.findNavController(v).navigate(R.id.action_fragmentDaddy_to_fragmentIndex,bundle);
            }
        });
        Glide.with(context).load(this.list.get(position).getPhotos().get(0)).centerCrop().placeholder(
                R.drawable.animation_loading).error(R.drawable.ic_error_img).into(holder.img_rcv_itemhome);
        holder.ic_wishlist_detail_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemWishlist(resultApartment.getId());
                list.remove(position);
                notifyItemRemoved(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeItemWishlist(resultApartment.getId());
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,list.size());
                return true;
            }
        });
    }
        private void removeItemWishlist(String idApartment){
        APIservice apIservice = APIClient.getClient().create(APIservice.class);
        retrofit2.Call<String> call  = apIservice.postToRemoveWishListData(email,idApartment);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, Response<String> response) {
            }
            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {

            }
        });
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
        ImageView img_rcv_itemhome,ic_wishlist_detail_remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_rcv_itemhome_desciption = (TextView) itemView.findViewById(R.id.tv_rcv_description_wishlist);
            tv_rcv_itemhome_addess = itemView.findViewById(R.id.tv_rcv_address_wishlist);
            tv_rcv_itemhome_price = itemView.findViewById(R.id.tv_rcv_price_wishlist);
            tv_rcv_itemhome_name = itemView.findViewById(R.id.tv_rcv_name_wishlist);
            img_rcv_itemhome = itemView.findViewById(R.id.img_rcv_item_wishlist);
            ic_wishlist_detail_remove = itemView.findViewById(R.id.ic_wishlist_detail_remove);
        }
    }
}
