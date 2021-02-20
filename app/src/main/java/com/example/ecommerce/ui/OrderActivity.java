package com.example.ecommerce.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Order1;
import com.example.ecommerce.network.apiClient;
import com.example.ecommerce.network.apiInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends menuActivity {
    Button adddress, order;
    EditText mAddress;
    RadioGroup radioGroup;
    RadioButton rbtn1,rbtn2;
    String status="placed";
    TextView total_price;

    int res;
    String stats;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        adddress=findViewById(R.id.btnaddress);
        order=findViewById(R.id.placeorder);
        mAddress=findViewById(R.id.address);
        rbtn1=findViewById(R.id.rbtn1);
        rbtn2=findViewById(R.id.rbtn2);
        total_price=findViewById(R.id.totalPrice);
        String price= getIntent().getStringExtra("total");
        SharedPreferences sharedPreferences =getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        int user=sharedPreferences.getInt("id",0);
        Log.d("user",String.valueOf(user));
        String shiipingAddress;
        Order1 mOrder=new Order1();
        Log.d("a","OrderActivity");

        res=0;
        if (Float.parseFloat(price) >= 500) {
            mOrder.setShipping(0.0);
        } else mOrder.setShipping(50.00);
        mOrder.setOrderTotal(mOrder.getShipping() + Float.parseFloat(price));
        mOrder.setUser(user);
        total_price.setText("Total: " + String.valueOf(mOrder.getOrderTotal()));

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String billingAddress=mAddress.getText().toString().trim();

                if(billingAddress.length()>0) {
//                    shiipingAddress = billingAddress;

                    mOrder.setBillingAddress(billingAddress);
                    mOrder.setShippingAddress(billingAddress);


                } else{
                    mOrder.setShippingAddress("a");
                    mOrder.setBillingAddress("a");
                    Toast.makeText(OrderActivity.this, "Please provide shipping address",Toast.LENGTH_LONG).show();
                }
                mOrder.setStatus(status);
                Intent intent = new Intent(OrderActivity.this, PaymentActivity.class);
                    final apiInterface apiInterface = apiClient.getClient().create(apiInterface.class);
                    Call<Order> call=apiInterface.placeOrder(user,mOrder);
                    call.enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            Log.d("order",mOrder.getBillingAddress()+"    "+mOrder.getShippingAddress()+"  "+mOrder.getShipping()+"  "+mOrder.getOrderTotal()+ "  "+
                                    mOrder.getStatus()+"      "+mOrder.getUser());
                            Log.d("code",String.valueOf(response.code()));
                            if(response.code()==201) {
                                    res=1;
                                    stats=response.body().getStatus();
                                intent.putExtra("status", stats);
                                startActivity(intent);

                            }
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {
                            Toast.makeText(OrderActivity.this, "Not able to place Order.\n Please try again!",Toast.LENGTH_LONG).show();
                            return;

                        }
                    });
                if(res==1){
                    intent.putExtra("status", stats);
                    startActivity(intent);
                }

                }


        });
        adddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddress.setVisibility(View.VISIBLE);
                adddress.setEnabled(false);


            }
        });

    }

    public String onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.rbtn1:
                if (checked)
                   status= "placed";
                    break;
            case R.id.rbtn2:
                if (checked)
                    status= "paid";
                    break;

        }
        return status;
    }

}
