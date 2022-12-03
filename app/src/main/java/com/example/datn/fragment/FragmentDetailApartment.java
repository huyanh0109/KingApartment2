package com.example.datn.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn.R;
import com.example.datn.adapter.ListImageDetailAdapter;
import com.example.datn.api.APIClient;
import com.example.datn.api.APIservice;
import com.example.datn.model.AccountUser;
import com.example.datn.model.Message;
import com.example.datn.model.ResultApartment;
import com.example.datn.viewmodel.AddWishListViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDetailApartment extends Fragment implements OnMapReadyCallback {
    Bundle bundle = new Bundle();
    private GoogleMap mMap;
    MapView mapView;
    ResultApartment resultApartment;
    ArrayList<Message> messageArrayList =  new ArrayList<>();
    TextView tv_detail_apartment_createBy, tv_detail_apartment_name, tv_detail_apartment_address,
            tv_detail_apartment_desciption, tv_detail_apartment_price, tv_detail_apartment_bed,
            tv_detail_apartment_shower, tv_detail_apartment_square, tv_detail_square_result, tv_detail_year_result;
    ImageView image_detail_apartment,ic_wishlist_home;
    RecyclerView rcv_listimage_detail;
    Button btn_detail_contact;
    ArrayList<String> listImage;
    ListImageDetailAdapter detailAdapter;
    AddWishListViewModel addWishListViewModel ;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_apartment, container, false);
        bundle.putString("Callback", "Home");
        resultApartment = (ResultApartment) getArguments().getSerializable("dataApartment");
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.map_apartment);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(FragmentDetailApartment.this).navigate(R.id.action_fragmentIndex_to_fragmentDaddy, bundle);
//                getParentFragmentManager().popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        ;
    }

    public void initView(View view) {
        tv_detail_apartment_address = view.findViewById(R.id.tv_detail_apartment_address);
        tv_detail_apartment_createBy = view.findViewById(R.id.tv_detail_apartment_createby);
        tv_detail_apartment_desciption = view.findViewById(R.id.tv_detail_apartment_desciption);
        tv_detail_apartment_name = view.findViewById(R.id.tv_detail_apartment_name);
        tv_detail_apartment_price = view.findViewById(R.id.tv_detail_apartment_price);
        image_detail_apartment = view.findViewById(R.id.image_detail_apartment);
        tv_detail_apartment_bed = view.findViewById(R.id.tv_detail_apartment_bed);
        tv_detail_apartment_shower = view.findViewById(R.id.tv_detail_apartment_shower);
        tv_detail_apartment_square = view.findViewById(R.id.tv_detail_apartment_square);
        btn_detail_contact = view.findViewById(R.id.btn_detail_contact);
        rcv_listimage_detail = view.findViewById(R.id.rcv_detail_listimage);
        tv_detail_square_result = view.findViewById(R.id.tv_detail_square);
        tv_detail_year_result = view.findViewById(R.id.tv_detail_year);
        ic_wishlist_home = view.findViewById(R.id.ic_wishlist_detail);
    }

    public void initData() {
        if (resultApartment.getCreateBy().equals("admin")) {
            tv_detail_apartment_createBy.setText("King Mall");
        } else {
            tv_detail_apartment_createBy.setText("Shoper");
        }
        tv_detail_apartment_address.setText(resultApartment.getAddress());
        tv_detail_apartment_desciption.setText(resultApartment.getDescription());
        tv_detail_apartment_name.setText(resultApartment.getName());
        DecimalFormat formatter = new DecimalFormat("#,##");
        double formatPrice = Double.parseDouble(resultApartment.getPrice()) / 10000;
        tv_detail_apartment_price.setText(formatter.format(formatPrice) + "tr");
        Glide.with(getActivity()).load(resultApartment.getPhotos().get(0)).centerCrop().placeholder(
                R.drawable.animation_loading).error(R.drawable.ic_error_img).into(image_detail_apartment);
        tv_detail_apartment_bed.setText(resultApartment.getSumBedroom() + " Giường");
        tv_detail_apartment_shower.setText(resultApartment.getSumToilet() + " Nhà tắm");
        tv_detail_apartment_square.setText(resultApartment.getSqrt() + "m2");
        tv_detail_square_result.setText(resultApartment.getSqrt() + "m2");
        ArrayList<Integer> listYear = new ArrayList<>();
        for (int i = 2012; i<2022;i++){
            listYear.add(i);
        }
        Random random = new Random();
        int randomYear = random.nextInt(listYear.size());
        tv_detail_year_result.setText(listYear.get(randomYear)+"");
        btn_detail_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ resultApartment.getContactPhoneNumber()));
                startActivity(intent);
            }
        });
        listImage = new ArrayList<String>();
        for (int i = 0; i < resultApartment.getPhotos().size(); i++) {
            listImage.add(resultApartment.getPhotos().get(i));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.scrollToPosition(0);
        rcv_listimage_detail.setLayoutManager(layoutManager);
        detailAdapter = new ListImageDetailAdapter(getActivity(), resultApartment.getPhotos());
        rcv_listimage_detail.setAdapter(detailAdapter);
        ic_wishlist_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postAccountUserData();
                addWishlist();
            }
        });
    }
    public void addWishlist(){
        addWishListViewModel = new ViewModelProvider(getActivity(),getDefaultViewModelProviderFactory()).get(AddWishListViewModel.class);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(FragmentSignin.KEY_ACCOUNTUSER, "");
        AccountUser user = gson.fromJson(json, AccountUser.class);
        Log.i("TAG", "messageRecicver: " + user.getEmail()+ "/"+ resultApartment.getId());
        addWishListViewModel.postAddWishListLiveData(user.getEmail(),resultApartment.getId()).observe(getActivity(), message -> {
                List<Message> messageRecicver = message;
                messageArrayList.addAll(messageRecicver);
                Log.i("TAG", "messageRecicver: " + messageArrayList.size());
                Message message1 = messageArrayList.get(0);
                Toast.makeText(getContext(), "messageRecicver: " + messageArrayList.size() + message1.getStatus(), Toast.LENGTH_SHORT).show();
        });
    }
//    private void postAccountUserData(){
//        APIservice apIservice = APIClient.getClient().create(APIservice.class);
//        Call<List<Message>> call  = apIservice.postToAddWishListData("huyanh0109@gmail.com",resultApartment.getId());
//        call.enqueue(new Callback<List<Message>>() {
//            @Override
//            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
//                Log.i("TAG", "onResponse: +ss");
//                List<Message> account = response.body();
//                Log.i("TAG","onResponse"+account.size());
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Message>> call, Throwable t) {
//
//            }
//        });
//    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        resultApartment = (ResultApartment) getArguments().getSerializable("dataApartment");
        LatLng locationApartment = new LatLng(Double.parseDouble(resultApartment.getLatitude()), Double.parseDouble(resultApartment.getLongitude()));
        MarkerOptions marker = new MarkerOptions().title(resultApartment.getName());
        Bitmap bitmapIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_location_maker);
        marker.icon(BitmapDescriptorFactory.fromBitmap(bitmapIcon));
        googleMap.addMarker(marker.position(locationApartment));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(locationApartment));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}
