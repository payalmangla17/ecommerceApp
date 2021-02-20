package com.example.ecommerce.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;

public class DetailViewModel extends AndroidViewModel {
    //public MutableLiveData<Product> item = new MutableLiveData<>();
    private ProductRepository productRepository;
    private MutableLiveData<Integer> activityCode=new MutableLiveData<>();
    public MutableLiveData<Cart> cartMutableLiveData=new MutableLiveData<>();
    public DetailViewModel(Application application){
        super(application);
        this.productRepository=new ProductRepository();
        this.activityCode.setValue(10);
    }
    SharedPreferences sharedpreferences =getApplication().getSharedPreferences("UserSettings", Context.MODE_PRIVATE);

    public LiveData<Cart> cartProduct(String user, int quantity, int item){
        return productRepository.setProductToCart(user,quantity,item);
    }
    public void addToCart(int item_name){
        String user=sharedpreferences.getString("user","");
//        int qty=sharedPreferences.getInt("quantity","")
//        String item_name=item.getName();
        cartProduct(user,1,item_name);

    }

    public LiveData<Integer> changeActivity(){

        return activityCode;
    }
    public void placeOrder(){
        activityCode.setValue(11);
    }
}
