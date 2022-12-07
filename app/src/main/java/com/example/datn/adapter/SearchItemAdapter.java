package com.example.datn.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn.R;
import com.example.datn.model.ResultApartment;

import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> {
    private final static String KEY_DATASEARCH = "dataApartment";
    Context context;
    List<ResultApartment> listSearch ;
    public SearchItemAdapter(Context context, List<ResultApartment> listSearch){
        this.listSearch = listSearch;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultApartment resultApartment = listSearch.get(position);
        holder.tv_detail_search_name.setText(resultApartment.getName());
        holder.tv_detail_search_addess.setText(resultApartment.getAddress());
        Glide.with(context).load(resultApartment.getPhotos().get(0)).centerCrop().placeholder(
                R.drawable.animation_loading).error(R.drawable.ic_error_img).into(holder.image_detail_search);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_DATASEARCH, resultApartment);
                Navigation.findNavController(v).navigate(R.id.action_fragmentSearch_to_fragmentIndex,bundle);
            }
        });
    }
    public interface OnClickSearch{
        void onClickDetailSearch();
    }
    @Override
    public int getItemCount() {
        if (listSearch != null){
            return listSearch.size();
        }
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_detail_search;
        TextView tv_detail_search_name,tv_detail_search_addess;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_detail_search = itemView.findViewById(R.id.image_detail_search);
            tv_detail_search_addess = itemView.findViewById(R.id.tv_detail_search_address);
            tv_detail_search_name = itemView.findViewById(R.id.tv_detail_search_name);
        }
    }
}
