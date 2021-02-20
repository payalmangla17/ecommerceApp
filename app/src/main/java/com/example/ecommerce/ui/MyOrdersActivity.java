package com.example.ecommerce.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.OrderAdapter;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderResponse;
import com.example.ecommerce.network.apiClient;
import com.example.ecommerce.network.apiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersActivity extends menuActivity {
    ArrayList<Order> items;
    OrderAdapter orderAdapter;
    int user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_recyclerview);
        RecyclerView recyclerView=findViewById(R.id.viewOrders);
        SharedPreferences sharedPreferences=getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        user=sharedPreferences.getInt("id",0);
        items=new ArrayList<>();
        getAllOrders();
        orderAdapter=new OrderAdapter(items);
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void getAllOrders() {
        final apiInterface apiInterface= apiClient.getClient().create(apiInterface.class);
        Call<OrderResponse> call=apiInterface.getAllOrders(user);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(response.code()==200){
                    items.addAll(response.body().getResults());
                    orderAdapter.setMyOrders(items);
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });
    }
}
