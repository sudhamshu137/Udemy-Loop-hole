package com.example.udemyat40;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    String firsttime;
    String extramsg;
    String emailLogin;

    LazyLoader lloader;

    int x = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences prf = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
        firsttime = prf.getString("FirstTimeInstall","yes");
        emailLogin = prf.getString("emailLogin","nnn");
        final SharedPreferences.Editor editor = prf.edit();

        extramsg = "yes";

        Intent intent = getIntent();

        if (intent.hasExtra("extramessage")) {
            extramsg = getIntent().getStringExtra("extramessage");
            emailLogin = getIntent().getStringExtra("emailcame");
            editor.putString("FirstTimeInstall",extramsg);
            editor.putString("emailLogin",emailLogin);
            editor.apply();
            x = 7000;
            final User user = new User("NA", "NA", "NA", "NA",
                                emailLogin, "NA", "NA", "NA","NA","NA","NA","NA");

            mAuth.createUserWithEmailAndPassword(emailLogin,"password123").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"logging in",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Unsuccessful, uninstall the app and try again",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Unsuccessful",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else if(!(emailLogin.equals("nnn"))){
            x = 4000;
            mAuth.signInWithEmailAndPassword(emailLogin,"password123").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"logging in",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this,"login Unsuccessful, close the app and try again",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


        lloader = findViewById(R.id.loader);
        LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());
        lloader.addView(loader);



        extramsg = prf.getString("FirstTimeInstall","yes");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(extramsg.equals("yes")){

                    Intent i = new Intent(MainActivity.this,firstTime.class);
                    startActivity(i);

                }
                else{

                    Intent i = new Intent(MainActivity.this,courseActivity.class);
                    startActivity(i);

                }
                finish();

            }
        },5000);

    }

}