package com.example.ecommerce.viewmodel;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecommerce.model.LoginModel;
import com.example.ecommerce.model.RegisterModel;
import com.example.ecommerce.model.RegisterResponse;
import com.example.ecommerce.repository.LoginRepository;
import com.example.ecommerce.repository.RegisterRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterViewModel extends ViewModel {

    public MutableLiveData<String> username= new MutableLiveData<>();
    public MutableLiveData<String> email= new MutableLiveData<>();
    public MutableLiveData<String> password= new MutableLiveData<>();
    public MutableLiveData<String> errorPassword = new MutableLiveData<>();
    public MutableLiveData<String> errorEmail = new MutableLiveData<>();
    //private MutableLiveData<RegisterResponse> registerResponseMutableLiveData;
    public MutableLiveData<String> errorUsername = new MutableLiveData<>();
    public MutableLiveData<Integer> busy;
    public MutableLiveData<Integer> ActivityCode=new MutableLiveData<>();
    private RegisterModel registerModel;
    public RegisterViewModel(){
        ActivityCode.setValue(1000);
    }
    public MutableLiveData<Integer> getBusy() {

        if (busy == null) {
            busy = new MutableLiveData<>();
            busy.setValue(8);
        }

        return busy;
    }
    public MutableLiveData<RegisterResponse> newUser(){
//        if(registerResponseMutableLiveData==null){
//            registerResponseMutableLiveData=new MutableLiveData<>();
//        }
     //   onApiResponse();
        return RegisterRepository.getInstance().registerResponseMutableLiveData;
    }
    public void onRegisterClicked() {
        //ActivityCode.setValue(1000);
        getBusy().setValue(0); //View.VISIBLE
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                RegisterRepository repo =  RegisterRepository.getInstance();
                registerModel.setEmail(String.valueOf(email));
                registerModel.setUsername(String.valueOf(username));
                registerModel.setPassword(String.valueOf(password));
                //registerResponseMutableLiveData =
                repo.getNewUser(registerModel);
                //Log.d("abc", String.valueOf(registerResponseMutableLiveData));
                busy.setValue(8);

            }
        }, 3000);
    }

    public MutableLiveData<Integer> getActivityCode(){

        return ActivityCode;
    }
    public void onLoginClicked(){
        ActivityCode.setValue(1001);
    }


}
