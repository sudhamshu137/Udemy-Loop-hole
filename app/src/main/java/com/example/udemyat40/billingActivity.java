package com.example.udemyat40;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class billingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
    }

    public void backtohome(View view){
        Intent i = new Intent(billingActivity.this, courseActivity.class);
        startActivity(i);
        finish();
    }

    public void payintent(View view){
        Intent i = new Intent(billingActivity.this, paymentActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(billingActivity.this,courseActivity.class);
        startActivity(i);
        finish();
    }

}