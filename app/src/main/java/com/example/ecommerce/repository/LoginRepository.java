package com.example.ecommerce.repository;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.ecommerce.model.LoginModel;
import com.example.ecommerce.model.LoginResponse;
import com.example.ecommerce.network.apiClient;
import com.example.ecommerce.network.apiInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRepository {
    private static LoginRepository repository;
    private Retrofit retrofitInstance= apiClient.getClient();
    private apiInterface apiInterface;


    public static LoginRepository getInstance(){
        if (repository == null){
            repository = new LoginRepository();
        }
        return repository;
    }
    public  LoginRepository(){

        apiInterface = apiClient.getClient().create(apiInterface.class);
    }
    public MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<LoginResponse> getLoginResponse() {
        return loginResponseMutableLiveData;
    }
    public void getUser(LoginModel user){


        apiInterface.getLoginUser(user).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
              //  Log.d("abcw", "onResponse response:: " + response.body());
                Log.d("abc", String.valueOf(response.code()));
                if(response.code()==401){
                    LoginResponse res=new LoginResponse();
                    res.setMessage("error");
                    loginResponseMutableLiveData.setValue(res);

                }
                if (response.body()!=null && response.code()==200){
                    loginResponseMutableLiveData.setValue(response.body());
                }

                //Log.d("reskk", String.valueOf(response.body().getMessage()));
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("abcyy", "onResponse response:: " );
                LoginResponse res=new LoginResponse();
                res.setMessage("error");
                loginResponseMutableLiveData.postValue(res);
            }
        });
      //  Log.d("res", String.valueOf(loginResponseMutableLiveData));
        //return loginResponseMutableLiveData;
    }
}
