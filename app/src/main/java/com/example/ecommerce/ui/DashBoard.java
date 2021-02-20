package com.example.ecommerce.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.ProductDataAdapter;
import com.example.ecommerce.databinding.ActivityDashboardBinding;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashBoard extends menuActivity implements ProductDataAdapter.onProductListener {
    private ProductViewModel productViewModel;
    private ProductDataAdapter productDataAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Product> items=new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDashboardBinding activityDashboardBinding= DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        RecyclerView recyclerView=activityDashboardBinding.viewProducts;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        productViewModel= new ViewModelProvider(this).get(ProductViewModel.class);
        productDataAdapter=new ProductDataAdapter(items,this);
        recyclerView.setAdapter(productDataAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getAllProducts();
            }
        });
     getAllProducts();
    }

    private void getAllProducts() {
        productViewModel.getAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productDataAdapter.setProductList((ArrayList<Product>) products);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//       // return super.onCreateOptionsMenu(menu);
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.menu_main,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_search: //TODO
//            case R.id.cart:// TODO
//
//        }
//        return true;
//    }


    @Override
    public void onProductClick(Product mItem) {
        Intent intent = new Intent(this, ProductDetail.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b=new Bundle();
        b.putSerializable("product",mItem);
        intent.putExtras(b);
        startActivity(intent);
    }
}
