package com.example.ecommerce.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;

public class menuActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search: //TODO
                break;
            case R.id.cart:
                Intent intent=new Intent(this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.my_orders:
                Intent intent1=new Intent(this, MyOrdersActivity.class);
                startActivity(intent1);
                break;

        }
        return true;
    }
}
