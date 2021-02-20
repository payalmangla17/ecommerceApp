package com.example.ecommerce.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ProductDetailBinding;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.viewmodel.DetailViewModel;

public class ProductDetail extends menuActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductDetailBinding productDetailBinding= DataBindingUtil.setContentView(this, R.layout.product_detail);
        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        Product item= (Product) bundle.getSerializable("product");
        DetailViewModel viewModel= new ViewModelProvider(this).get(DetailViewModel.class);
        Log.d("PRODUCT",item.getSlug());
        productDetailBinding.setLifecycleOwner(this);
        productDetailBinding.setDetail(item);
        productDetailBinding.setBtnClick(viewModel);
        SharedPreferences sharedPreferences=getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        String user=sharedPreferences.getString("user","");
//        int qty=sharedPreferences.getInt("quantity","")
        int item_name=item.getId();
        viewModel.cartProduct(user,1,item_name).observe(this, new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {
                if (cart!=null && cart.getId()>0){
                    Toast.makeText(ProductDetail.this,"Item added to Cart Successfully!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ProductDetail.this,"Item already present in cart!", Toast.LENGTH_LONG).show();

                }
                Intent intent=new Intent(ProductDetail.this, CartActivity.class);
                startActivity(intent);
            }
        });

        viewModel.changeActivity().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==11){
                    Intent intent=new Intent(ProductDetail.this,OrderActivity.class);
                    intent.putExtra("total",item.getPrice());
                    startActivity(intent);
                }
            }
        });
    }
}
