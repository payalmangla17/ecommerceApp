package com.example.ecommerce.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository();
    }
    public LiveData<List<Product>> getAllProduct() {
        return productRepository.getMutableLiveData();
    }
    public  LiveData<Product> getSingleProduct(int id){
        return productRepository.getSingleProduct(id);
    }

}
