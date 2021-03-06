package com.refCustomerFamily.activities_fragments.activity_home.fragments.order_fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.refCustomerFamily.R;
import com.refCustomerFamily.adapters.MarketAdapter;
import com.refCustomerFamily.databinding.FragmentStoresBinding;
import com.refCustomerFamily.models.OrderModel;
import com.refCustomerFamily.models.UserModel;
import com.refCustomerFamily.preferences.Preferences;
import com.refCustomerFamily.remote.Api;
import com.refCustomerFamily.tags.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleOrderFragment extends Fragment {


    private FragmentStoresBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;
    private MarketAdapter orderAdapter;
    private List<OrderModel.Data> orderList;

    public static GoogleOrderFragment newInstance() {

        return new GoogleOrderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stores, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(getActivity());
        orderList = new ArrayList<>();
        orderAdapter = new MarketAdapter(orderList, getActivity());
        Paper.init(getActivity());
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.recViewOrders.setAdapter(orderAdapter);
        binding.recViewOrders.setLayoutManager(new LinearLayoutManager(this.getContext()));


     //   getOrders();
    }
    public void getOrders() {
        orderList.clear();
        orderAdapter.notifyDataSetChanged();
        binding.progBarOrders.setVisibility(View.VISIBLE);
        Api.getService(Tags.base_url).getOrderByStatus("Bearer " + userModel.getData().getToken(),
                userModel.getData().getId(), "google", "client", "current").enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                binding.progBarOrders.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    orderList.addAll(response.body().getData());
                    orderAdapter.notifyDataSetChanged();

                    if (orderList.size() == 0){
                        binding.linearNoData.setVisibility(View.VISIBLE);
                    }else {
                        binding.linearNoData.setVisibility(View.GONE);
                    }
                }else {
                    Log.e("Fragment_Orders: ",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                binding.progBarOrders.setVisibility(View.GONE);
                Log.e("Fragment_Orders: ",t.getMessage());
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getOrders();
    }
}