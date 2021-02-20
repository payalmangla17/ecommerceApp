package com.example.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartViewResponse;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartRepository;

import java.util.List;

public class CartViewModel extends ViewModel {
    private CartRepository cartRepository;
    public CartViewModel(){
        this.cartRepository=new CartRepository();
    }
    public LiveData<CartViewResponse> getCartProducts(int id){
        return cartRepository.getCartResponse(id);
    }
    public LiveData<Cart> updateCart(int cid,int qty){
        return cartRepository.updateCartView(cid,qty);
    }
    public LiveData<String> deleteFromCart(int id){
        return cartRepository.deleteItemfromCart(id);
    }

}
