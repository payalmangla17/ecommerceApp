package com.example.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ActivityCartItemsBinding;
import com.example.ecommerce.model.CartAdapterData;
import com.example.ecommerce.model.Product;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private ArrayList<CartAdapterData> mcart;
    public interface onCartItemListener{
        void onItemListener(CartAdapterData mProduct, int id);
    }
    private onCartItemListener mListener;
     public CartAdapter(ArrayList<CartAdapterData> mcart,onCartItemListener mListener){
         this.mcart=mcart;
         this.mListener=mListener;
     }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityCartItemsBinding activityCartItemsBinding=
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.activity_cart_items, parent, false);
        return new CartViewHolder(activityCartItemsBinding,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
//        Product product=mcart.get(position);
//        holder.activityCartItemsBinding.setProduct(product);
        CartAdapterData c=mcart.get(position);
        holder.activityCartItemsBinding.setProduct(c.getResults());
        holder.activityCartItemsBinding.setItemcnt(c.getCnt());
    }

    @Override
    public int getItemCount() {
        if (mcart!= null) {
            return mcart.size();
        } else {
            return 0;
        }
    }

    public void setMcart(ArrayList<CartAdapterData> products) {
        this.mcart = products;
        notifyDataSetChanged();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ActivityCartItemsBinding activityCartItemsBinding;
        onCartItemListener cartItemListener;
        public CartViewHolder(@NonNull ActivityCartItemsBinding binding,onCartItemListener listener) {
            super(binding.getRoot());
            this.activityCartItemsBinding=binding;
            this.cartItemListener=listener;
            activityCartItemsBinding.cartlayout.setOnClickListener(this);
            activityCartItemsBinding.btndel.setOnClickListener(this);
            activityCartItemsBinding.btnadd.setOnClickListener(this);
            activityCartItemsBinding.delete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btndel){
                cartItemListener.onItemListener(mcart.get(getAdapterPosition()),R.id.btndel);
            }
            else if (v.getId()== R.id.btnadd){
                cartItemListener.onItemListener(mcart.get(getAdapterPosition()),R.id.btnadd);
            }
            else if (v.getId()==R.id.delete){
                cartItemListener.onItemListener(mcart.get(getAdapterPosition()), R.id.delete);
            }
            else
            cartItemListener.onItemListener(mcart.get(getAdapterPosition()), R.id.cartlayout);
        }
    }
}
