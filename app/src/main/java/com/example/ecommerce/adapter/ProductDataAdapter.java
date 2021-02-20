package com.example.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ActivityProductListBinding;
import com.example.ecommerce.model.Product;

import java.util.ArrayList;

public class ProductDataAdapter extends RecyclerView.Adapter<ProductDataAdapter.ProductViewHolder>  {
    private ArrayList<Product> products;
    public interface onProductListener{
        void onProductClick(Product mItem);
    }
    private onProductListener mListener;
    public ProductDataAdapter(ArrayList<Product> productList, onProductListener mListener) {
        this.products=productList;
        this.mListener= mListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ActivityProductListBinding productListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.activity_product_list, viewGroup, false);
        return new ProductViewHolder(productListItemBinding,mListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        Product currentProduct = products.get(i);
        productViewHolder.productListItemBinding.setProduct(currentProduct);
//        productViewHolder.productListItemBinding.onClick(f -> {
//            Intent intent = new Intent(context, ProductDetail.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("product", currentProduct);
//            context.startActivity(intent);
//        });
    }
    @Override
    public int getItemCount() {
        if (products != null) {
            return products.size();
        } else {
            return 0;
        }
    }
    public void setProductList(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }



    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ActivityProductListBinding productListItemBinding;
        onProductListener mItemListener;
        public ProductViewHolder(@NonNull ActivityProductListBinding productListItemBinding,onProductListener mListener) {
            super(productListItemBinding.getRoot());
            this.productListItemBinding =productListItemBinding;
            this.mItemListener=mListener;
            productListItemBinding.layout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mItemListener.onProductClick(products.get(getAdapterPosition()));
        }
    }


}

