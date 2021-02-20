package com.example.ecommerce.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ecommerce.model.LoginResponse;
import com.example.ecommerce.R;
import com.example.ecommerce.viewmodel.LoginViewModel;
import com.example.ecommerce.databinding.ActivityMainBinding;


public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding=DataBindingUtil.setContentView(this, R.layout.activity_main);
        loginViewModel= new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLoginViewModel(loginViewModel);
        binding.setLifecycleOwner(this);
        SharedPreferences sharedPreferences=getSharedPreferences("UserSettings", Context.MODE_PRIVATE);

//        loginViewModel.getUser().observe(this, (LoginResponse response) -> {
//            if(response !=null && response.getMessage().equals("successful")){
//                Log.d("abcd",response.getMessage());
//                Toast.makeText(LoginActivity.this,"success",Toast.LENGTH_LONG).show();
//            }
//            if(response==null)
//                Toast.makeText(LoginActivity.this,"unsuccess",Toast.LENGTH_LONG).show();
//
//        });

        loginViewModel.getUser().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse response) {
               Log.d("abcd", String.valueOf(response));
                if(response !=null && response.getMessage().equals("success")){

                Toast.makeText(LoginActivity.this,"success",Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email",response.getEmail());
                editor.putString("username",response.getUsername());
                editor.putString("access_tokens",response.getTokens().getAccess());
                editor.putString("refresh_token",response.getTokens().getRefresh());
                editor.putInt("id",response.getId());
                editor.apply();

                    startActivity(new Intent(LoginActivity.this, DashBoard.class));
                return;
            }
            if(response!=null && response.getMessage().equals("error")){
                Toast.makeText(LoginActivity.this,"Please provide valid Credentials!",Toast.LENGTH_LONG).show();
                return;
            }

                //Toast.makeText(LoginActivity.this,"unsuccess",Toast.LENGTH_LONG).show();
                //Log.d("cgange","pjhjh");
            }
        });

        loginViewModel.getActivityCode().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1001){
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            }
        });

    }
}