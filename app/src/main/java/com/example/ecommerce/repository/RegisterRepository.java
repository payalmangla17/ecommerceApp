package com.example.ecommerce.repository;

import androidx.lifecycle.MutableLiveData;


import com.example.ecommerce.model.RegisterModel;
import com.example.ecommerce.model.RegisterResponse;
import com.example.ecommerce.network.apiClient;
import com.example.ecommerce.network.apiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterRepository {

    private static RegisterRepository repository;
    private Retrofit retrofitInstance= apiClient.getClient();

    private apiInterface apiService;
    public static RegisterRepository getInstance(){
        if (repository == null){
            repository = new RegisterRepository();
        }
        return repository;
    }
    public RegisterRepository(){

        apiService = retrofitInstance.create(apiInterface.class);
    }

    public MutableLiveData<RegisterResponse> registerResponseMutableLiveData=new MutableLiveData<>();

    public MutableLiveData<RegisterResponse>  getNewUser(RegisterModel registerModel){
        Call<RegisterResponse> call=apiService.createUser(registerModel);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.code()==200)
                    registerResponseMutableLiveData.setValue(response.body());
                else {
                    RegisterResponse res=new RegisterResponse();
                    res.setEmail("Email has been already in use!");
                    res.setUsername("Usename has already been in use!");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                registerResponseMutableLiveData.setValue(null);
            }
        });
        return  registerResponseMutableLiveData;
    }

}
