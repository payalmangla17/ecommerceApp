package com.example.ecommerce.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.CartAdapter;
import com.example.ecommerce.databinding.ActivityCartItemsBinding;
import com.example.ecommerce.databinding.CartRecyclerviewBinding;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartAdapterData;
import com.example.ecommerce.model.CartViewResponse;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.viewmodel.CartViewModel;
import com.example.ecommerce.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends menuActivity implements CartAdapter.onCartItemListener {
    private ArrayList<CartAdapterData> items=new ArrayList<>();
    private ArrayList<Cart> cart_items=new ArrayList<>();
    private CartViewModel cartViewModel;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CartRecyclerviewBinding cartRecyclerviewBinding = DataBindingUtil.setContentView(this, R.layout.cart_recyclerview);
        if(Float.parseFloat((String) cartRecyclerviewBinding.amounttv.getText())==0.00){
            cartRecyclerviewBinding.btn.setEnabled(false);
        }
        else cartRecyclerviewBinding.btn.setEnabled(true);
        cartRecyclerviewBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CartActivity.this, OrderActivity.class);
                Float f=Float.parseFloat((String) cartRecyclerviewBinding.amounttv.getText());
                intent.putExtra("total",f);
                Log.d("a","changed");
                startActivity(intent);

            }
        });
        RecyclerView recyclerView=cartRecyclerviewBinding.cartProducts;
        cartAdapter=new CartAdapter(items,this);
        recyclerView.setAdapter(cartAdapter);
        cartViewModel=new ViewModelProvider(this).get(CartViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
//        TODO insert id for fetching cart results
        getCart(1);
    }

    private void getCart(int id) {
        cartViewModel.getCartProducts(id).observe(this, new Observer<CartViewResponse>() {
            @Override
            public void onChanged(CartViewResponse cartViewResponse) {
//                cartAdapter.setMcart((ArrayList<Cart>)cartViewResponse.getResults());
                cart_items= (ArrayList<Cart>) cartViewResponse.getResults();
                convertToProduct();
                cartAdapter.setMcart(items);
            }
        });
    }

    private void convertToProduct(){
        ProductRepository productRepository=new ProductRepository();
        for (Cart i:cart_items){
            MutableLiveData<Product> product= productRepository.getSingleProduct(i.getItem());
            Product p=product.getValue();
            CartAdapterData c=new CartAdapterData(p,i.getQuantity());
            items.add(c);
        }
    }
    @Override
    public void onItemListener(CartAdapterData mProduct, int id) {
        if(id==R.id.cartlayout) {
            Intent intent = new Intent(this, ProductDetail.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle b = new Bundle();
            b.putSerializable("product", mProduct.getResults());
            intent.putExtras(b);
            startActivity(intent);
        }
        else if (id==R.id.btndel){
            int pos=items.indexOf(mProduct);
            Cart citem= cart_items.get(pos);
            if (citem.getQuantity()>1)
                cartViewModel.updateCart(citem.getId(),citem.getQuantity()-1).observe(this, new Observer<Cart>() {
                    @Override
                    public void onChanged(Cart cartResponse) {
                        mProduct.setCnt(cartResponse.getQuantity());


                    }
                });
            else if(citem.getQuantity()==1){
                cartViewModel.deleteFromCart(citem.getId()).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if (s.equals("Deleted")){
                            Toast.makeText(CartActivity.this, "Item removed from Cart!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }
        else if (id==R.id.btnadd){
            int pos=items.indexOf(mProduct);
            Cart citem= cart_items.get(pos);
            if (citem.getQuantity()<9)
                cartViewModel.updateCart(citem.getId(),citem.getQuantity()+1).observe(this, new Observer<Cart>() {
                    @Override
                    public void onChanged(Cart cartResponse) {
                        if(cartResponse!= null){
                            mProduct.setCnt(cartResponse.getQuantity());
                        }

                    }
                });
            else if(citem.getQuantity()==9){
                Toast.makeText(this,"Can get at max 10 Products.",Toast.LENGTH_LONG).show();

            }
        }
        else if (id==R.id.delete){
            int pos=items.indexOf(mProduct);
            Cart citem= cart_items.get(pos);
            cartViewModel.deleteFromCart(citem.getId()).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (s.equals("Deleted")){
                        Toast.makeText(CartActivity.this, "Item removed from Cart!",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // return super.onCreateOptionsMenu(menu);
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.menu_main,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_search: // TODO
//            case R.id.cart://TODO
//
//        }
//        return true;
//    }
}
