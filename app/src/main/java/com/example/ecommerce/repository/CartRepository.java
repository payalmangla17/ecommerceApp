package com.example.ecommerce.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartViewResponse;
import com.example.ecommerce.model.ProductList;
import com.example.ecommerce.network.apiClient;
import com.example.ecommerce.network.apiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {
    public MutableLiveData<CartViewResponse> cartResponse=new MutableLiveData<>();
    public MutableLiveData<Cart> updateCart=new MutableLiveData<>();
    public MutableLiveData<String> deleteResponse=new MutableLiveData<>();
    public LiveData<CartViewResponse> getCartResponse(int id){
        final apiInterface apiInterface = apiClient.getClient().create(apiInterface.class);
        Call<CartViewResponse> call =apiInterface.getCartView( id);
        call.enqueue(new Callback<CartViewResponse>() {
            @Override
            public void onResponse(Call<CartViewResponse> call, Response<CartViewResponse> response) {
                cartResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartViewResponse> call, Throwable t) {

            }
        });
        return cartResponse;
    }

    public LiveData<Cart> updateCartView(int id, int qty){
        final apiInterface apiInterface=apiClient.getClient().create(apiInterface.class);
        Call<Cart> call=apiInterface.updateCartItem(id,qty);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.code()==200){
                    updateCart.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });
        return updateCart;
    }

    public LiveData<String> deleteItemfromCart(int id){
        final apiInterface apiInterface=apiClient.getClient().create(apiInterface.class);
        Call<Response> call=apiInterface.deleteItem(id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {
                if(response.code()==204){
                    deleteResponse.setValue("Deleted");

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                deleteResponse.setValue("failure");

            }
        });
        return deleteResponse;

    }


}
