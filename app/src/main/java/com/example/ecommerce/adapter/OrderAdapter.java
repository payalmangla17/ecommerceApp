package com.example.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Order;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> myOrders;
    public OrderAdapter(List<Order> mOrders){
        this.myOrders=mOrders;
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View orderView=inflater.inflate(R.layout.myorders,parent,false);
        OrderViewHolder viewHolder = new OrderViewHolder(orderView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order=myOrders.get(position);
            TextView tv1=holder.totaltv;
            TextView tv2=holder.addresstv;
            TextView tv3=holder.datetv;
            if(getItemCount()>0) {
                tv1.setText(order.getOrderTotal());
                tv2.setText(order.getShippingAddress());
                tv3.setText(order.getOrderDate());
            }
            else {
                tv1.setText("YOU HAVE NOT ORDERED ANYTHING YET!");
            }

    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }
    public void setMyOrders(ArrayList<Order> orders){
        this.myOrders=orders;
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView totaltv, addresstv, datetv;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            totaltv=itemView.findViewById(R.id.total_amt);
            addresstv=itemView.findViewById(R.id.shippingadd);
            datetv=itemView.findViewById(R.id.date);
        }
    }
}
