package com.example.ecommerce.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductList;
import com.example.ecommerce.network.apiClient;
import com.example.ecommerce.network.apiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private static final String TAG = "ProductRepository";
    private ArrayList<Product> products = new ArrayList<>();
    private MutableLiveData<List<Product>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> productMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<Cart> cartMutableLiveData=new MutableLiveData<Cart>();
    public ProductRepository() {
    }
    public MutableLiveData<List<Product>> getMutableLiveData() {
        final apiInterface apiInterface = apiClient.getClient().create(apiInterface.class);
        Call<ProductList> call = apiInterface.getAllProducts();
        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                ProductList productDBResponse = response.body();
                if (productDBResponse != null && productDBResponse.getResults() != null) {
                    products = (ArrayList<Product>) productDBResponse.getResults();
                    mutableLiveData.setValue(products);
                }
            }
            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
            }
        });
        return mutableLiveData;
    }

    public  MutableLiveData<Product> getSingleProduct(int id){
        final  apiInterface apiInterface=apiClient.getClient().create(apiInterface.class);
        Call<Product> call =apiInterface.getProductDetail(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.code() == 200){
                    productMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
        return productMutableLiveData;
    }

    public LiveData<Cart> setProductToCart(String user, int quantity, int item){
        final  apiInterface apiInterface=apiClient.getClient().create(apiInterface.class);
        Call<Cart> call =apiInterface.addProductToCart( user,quantity, item);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.code()==200 && response!=null){
                    cartMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }


        });
        return cartMutableLiveData;
    }


}
