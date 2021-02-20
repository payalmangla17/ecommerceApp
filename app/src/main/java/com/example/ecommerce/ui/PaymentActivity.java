package com.example.ecommerce.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;

public class PaymentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        TextView tv=findViewById(R.id.tv);
        String s=getIntent().getExtras().getString("status");
        if(s.equals("placed")){
            tv.setText("Bhaiya knjoosi na krein! :)");
        }
        else tv.setText("aap jaise customer ho toh kya baat h! :)");

    }
}
