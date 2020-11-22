package com.example.udemyat40;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class paymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView tv = findViewById(R.id.clickhere);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

    public void copyText(View view){

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", "+919148706915");
        clipboard.setPrimaryClip(clip);
        Toast.makeText(paymentActivity.this, "phone number copied to clipboard", Toast.LENGTH_LONG).show();

    }

    public void clickhere(View view){
        String phoneNumber = "+917406401273";
        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + "I am not sure about the payment!");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(sendIntent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(paymentActivity.this, courseActivity.class);
        startActivity(ii);
        finish();
    }
}