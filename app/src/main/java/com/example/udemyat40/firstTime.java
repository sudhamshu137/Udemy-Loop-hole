package com.example.udemyat40;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class firstTime extends AppCompatActivity {
    TextView tv;
    CheckBox checkBox;
    EditText emailEt;
    String emailcame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        tv = findViewById(R.id.tv1);
        checkBox = findViewById(R.id.checkBox);
        emailEt = findViewById(R.id.email);

        String s = "1. This app allows you to get the Udemy course you require with a great concession," +
                "supplied to you by sharing the mega cloud storage link containing the course's videos and documents.\n" +
                "2. The maximum time you can wait will be 48 hours (depending on the length and price of the course).\n" +
                "3. Pricing will be messaged to you directly through mail or WhatsApp number that you enter.\n" +
                "4. Pricing will be a minimum of 40₹ and a maximum of 100₹ (depending on the length and price of the course).\n" +
                "5. If the price gets more than 40rs then the price will be mailed to your email address and messaged through WhatsApp and SMS will be sent\n" +
                "6. Courses which are requested and are processing will exist in the course waiting list\n" +
                "7. The maximum number of courses that can exist in waiting list will be 2.\n" +
                "8. The link to the course will be delivered to you only after the full payment and its verification.\n" +
                "9. Note that there will be no certificate provided once you finish the course.\n" +
                "10. Course will be valid for lifetime once you buy it from this app!\n" +
                "11. If you have already paid and did'nt get the course link after 24 hours of payment then you can request for refund and it will be fully refunded";

        tv.setText(s);


    }

    public void registerfunc(View view){

        emailcame = emailEt.getText().toString().trim();

        if(emailcame.isEmpty()){
            emailEt.setError("This field cannot be empty");
            emailEt.requestFocus();
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(emailcame).matches()){
            emailEt.setError("provide a valid email");
            emailEt.requestFocus();
        }
        else if(!(checkBox.isChecked())){
            Toast.makeText(firstTime.this,"You cannot move forward without agreeing to the rules",Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = new Intent(firstTime.this, MainActivity.class);
            i.putExtra("extramessage", "no");
            i.putExtra("emailcame",emailcame);
            startActivity(i);
            finish();
        }

    }

}