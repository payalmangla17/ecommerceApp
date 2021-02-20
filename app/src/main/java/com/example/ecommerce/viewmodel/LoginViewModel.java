package com.example.ecommerce.viewmodel;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecommerce.model.LoginModel;
import com.example.ecommerce.model.LoginResponse;
import com.example.ecommerce.repository.LoginRepository;

public class LoginViewModel extends ViewModel {


    public MutableLiveData<String> errorPassword = new MutableLiveData<>();
    public MutableLiveData<String> errorEmail = new MutableLiveData<>();
    public MutableLiveData<Integer> ActivityCode= new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Integer> busy;
    private LoginRepository repo = LoginRepository.getInstance();
    public MutableLiveData<Integer> getBusy() {

        if (busy == null) {
            busy = new MutableLiveData<>();
            busy.setValue(8);
        }

        return busy;
    }
    public LoginViewModel(){
        ActivityCode.setValue(1000);
    }
    //private LiveData<LoginResponse> loginResponseMutableLiveData;

    public LiveData<LoginResponse> getUser() {
//        if (loginResponseMutableLiveData==null)
//            loginResponseMutableLiveData=new MutableLiveData<>();
//        return loginResponseMutableLiveData;
        return LoginRepository.getInstance().getLoginResponse();
    }


    public void onLoginClicked() {
        //ActivityCode.setValue(1000);
        getBusy().setValue(0); //View.VISIBLE

                LoginModel user=new LoginModel();
                user.setEmail(email.getValue());
                user.setPassword(password.getValue());
                Log.d("asd",user.getEmail()+user.getPassword());
                repo.getUser(user);
                busy.setValue(8);
    }
    public MutableLiveData<Integer> getActivityCode(){

        return ActivityCode;
    }
    public void onSignupClicked() {
        ActivityCode.setValue(1001);
    }
}

