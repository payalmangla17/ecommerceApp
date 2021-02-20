package com.example.ecommerce.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ActivityMainBinding;
import com.example.ecommerce.databinding.ActivityRegisterBinding;
import com.example.ecommerce.model.LoginResponse;
import com.example.ecommerce.model.RegisterResponse;
import com.example.ecommerce.viewmodel.LoginViewModel;
import com.example.ecommerce.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerViewModel= new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setRegisterViewModel(registerViewModel);
        binding.setLifecycleOwner(this);

        registerViewModel.newUser().observe(this, new Observer<RegisterResponse>() {
            @Override
            public void onChanged(RegisterResponse response) {
                if(response!=null && response.getEmail().equals("Email has been already in use!")){
                    Toast.makeText(RegisterActivity.this,response.getEmail() +" or "+ response.getUsername(),Toast.LENGTH_LONG).show();
                    return;
                }
                if(response!=null ){
                    Toast.makeText(RegisterActivity.this,"An email has been set to your email id for verification.",Toast.LENGTH_LONG).show();
                }
            }
        });
        registerViewModel.getActivityCode().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1001){
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        });
    }
}
