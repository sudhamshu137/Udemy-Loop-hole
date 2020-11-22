package com.example.udemyat40;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class request extends AppCompatActivity {

    EditText linkEt,nameEt,emailEt,phoneEt;
    String email,link,name,phone;
    TextView txt;

    TextView tv1, tv2, tv3, tv4;

    private FirebaseUser user;
    private DatabaseReference reference;
    private  String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        emailEt = findViewById(R.id.email);
        nameEt = findViewById(R.id.name);
        linkEt = findViewById(R.id.link);
        phoneEt = findViewById(R.id.phone);
        txt = findViewById(R.id.txt);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv1.setText("");
        tv2.setText("");
        tv3.setText("");
        tv4.setText("");

        String sss = "Please make sure that course name and course link that you have given directs to the same course\n" +
                "⚫ You will be notified about the progress within next 48 hours";
        txt.setText(sss);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile!=null){
                    String courseone = userProfile.courseonename;
                    String coursetwo = userProfile.coursetwoname;
                    String c1done = userProfile.coneseen;
                    String c2done = userProfile.ctwoseen;

                    tv1.setText(courseone);
                    tv2.setText(coursetwo);
                    tv3.setText(c1done);
                    tv4.setText(c2done);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(request.this,"error!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void Request(View view){

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users").child(userID);

        email = emailEt.getText().toString().trim();
        link = linkEt.getText().toString().trim();
        name = nameEt.getText().toString().trim();
        phone = phoneEt.getText().toString().trim();

        if(link.isEmpty()){
            linkEt.setError("This field cannot be empty");
            linkEt.requestFocus();
        }

        else if(name.isEmpty()){
            nameEt.setError("This field cannot be empty");
            nameEt.requestFocus();
        }

        else if(phone.isEmpty()){
            phoneEt.setError("This field cannot be empty");
            phoneEt.requestFocus();
        }

        else if(phoneEt.length() != 10){
            phoneEt.setError("enter a valid phone number");
            phoneEt.requestFocus();
        }

        else if(email.isEmpty()){
            emailEt.setError("This field cannot be empty");
            emailEt.requestFocus();
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEt.setError("provide a valid email");
            emailEt.requestFocus();
        }

        else if(!Patterns.WEB_URL.matcher(link).matches()){
            linkEt.setError("this URL is not valid");
            linkEt.requestFocus();
        }

        else{
            mRef.child("phone").setValue(phone);
            if(tv1.getText().toString().trim().equals("NA") && tv2.getText().toString().trim().equals("NA") &&
                tv3.getText().toString().trim().equals("NA") && tv4.getText().toString().trim().equals("NA")) {
                mRef.child("courseonename").setValue(name);
                mRef.child("courseonelink").setValue(link);
                mRef.child("conedone").setValue("pending");
                mRef.child("conepaid").setValue("pending");
                mRef.child("coneseen").setValue("pending");
                Intent i = new Intent(request.this, billingActivity.class);
                startActivity(i);
            }
            if(tv2.getText().toString().trim().equals("NA") && !(tv1.getText().toString().trim().equals("NA")) &&
                tv4.getText().toString().trim().equals("NA") && tv3.getText().toString().trim().equals("pending")){
                mRef.child("coursetwoname").setValue(name);
                mRef.child("coursetwolink").setValue(link);
                mRef.child("ctwodone").setValue("pending");
                mRef.child("ctwopaid").setValue("pending");
                mRef.child("ctwoseen").setValue("pending");
                Intent i = new Intent(request.this, billingActivity.class);
                startActivity(i);
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(request.this, courseActivity.class);
        startActivity(ii);
        finish();
    }
}