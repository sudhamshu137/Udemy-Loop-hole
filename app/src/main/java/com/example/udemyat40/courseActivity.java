package com.example.udemyat40;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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

public class courseActivity extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4, tv5, tv6;

    Button requestbtn;

    private FirebaseUser user;
    private DatabaseReference reference;
    private  String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        TextView tv = findViewById(R.id.clickhere);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        requestbtn = findViewById(R.id.requestbtn);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        // get the courseone and coursetwo variables from firebase
        tv1.setText("");
        tv2.setText("");
        tv3.setText("");
        tv4.setText("");
        tv5.setText("");
        tv6.setText("");

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile!=null){
                    String courseone = userProfile.courseonename;
                    String coursetwo = userProfile.coursetwoname;
                    String c1done = userProfile.conedone;
                    String c2done = userProfile.ctwodone;
                    String c1paid = userProfile.conepaid;
                    String c2paid = userProfile.ctwopaid;

                    tv1.setText(courseone);
                    tv2.setText(coursetwo);
                    tv3.setText(c1done);
                    tv4.setText(c2done);
                    tv5.setText(c1paid);
                    tv6.setText(c2paid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(courseActivity.this,"error!",Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void RequestPage(View view){


        if(!(tv1.getText().toString().trim().equals("NA")) && !(tv2.getText().toString().trim().equals("NA"))
                && tv3.getText().toString().trim().equals("NA") && tv4.getText().toString().trim().equals("NA"))  {

            Toast.makeText(courseActivity.this,"You have already reached the maximum number of waiting courses! " +
                    "Please try again after any one course get approved",Toast.LENGTH_LONG).show();

        }
        else if(!(tv1.getText().toString().trim().equals("NA")) && !(tv2.getText().toString().trim().equals("NA"))
                && tv3.getText().toString().trim().equals("pending") && tv4.getText().toString().trim().equals("pending"))  {

            Toast.makeText(courseActivity.this,"You have already reached the maximum number of waiting courses! " +
                    "Please try again after any one course get approved",Toast.LENGTH_LONG).show();

        }
        else if(tv1.getText().toString().trim().equals("") && tv2.getText().toString().trim().equals("") &&
                tv3.getText().toString().trim().equals("") && tv4.getText().toString().trim().equals("")){
            Toast.makeText(courseActivity.this, "please wait",Toast.LENGTH_LONG).show();
        }
        else{
            Intent i = new Intent(courseActivity.this, request.class);
            startActivity(i);
        }
    }


    public void firstcourse(View view){

        if(tv3.getText().toString().trim().equals("pending")){
            Intent i = new Intent(courseActivity.this,billingActivity.class);
            startActivity(i);
        }
        if(tv3.getText().toString().trim().equals("done") && tv5.getText().toString().trim().equals("pending")){
            Intent i = new Intent(courseActivity.this,paymentActivity.class);
            startActivity(i);
        }
        if(tv3.getText().toString().trim().equals("done") && tv5.getText().toString().trim().equals("paid")){
            Intent i = new Intent(courseActivity.this,finalActivity.class);
            i.putExtra("number","one");
            startActivity(i);
            finish();
        }
    }

    public void secondcourse(View view){
        if(tv4.getText().toString().trim().equals("pending")){
            Intent i = new Intent(courseActivity.this,billingActivity.class);
            startActivity(i);
        }
        if(tv4.getText().toString().trim().equals("done") && tv6.getText().toString().trim().equals("pending")){
            Intent i = new Intent(courseActivity.this,paymentActivity.class);
            startActivity(i);
        }
        if(tv4.getText().toString().trim().equals("done") && tv6.getText().toString().trim().equals("paid")){
            Intent i = new Intent(courseActivity.this,finalActivity.class);
            i.putExtra("number","two");
            startActivity(i);
            finish();
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile!=null){
                    String courseone = userProfile.courseonename;
                    String coursetwo = userProfile.coursetwoname;
                    String c1done = userProfile.conedone;
                    String c2done = userProfile.ctwodone;
                    String c1paid = userProfile.conepaid;
                    String c2paid = userProfile.ctwopaid;
                    String c1seen = userProfile.coneseen;
                    String c2seen = userProfile.ctwoseen;
                    String c2link = userProfile.coursetwolink;

                    if(c1done.equals("done") && c1paid.equals("paid") && (c1seen.equals("seen")||c1seen.equals("NA")) && c2seen.equals("NA")){
                        reference.child(userID).child("courseonename").setValue("NA");
                        reference.child(userID).child("conedone").setValue("NA");
                        reference.child(userID).child("conepaid").setValue("NA");
                        reference.child(userID).child("coneseen").setValue("NA");
                        reference.child(userID).child("conelink").setValue("NA");
                    }
                    if(c1seen.equals("NA") && c2seen.equals("pending")){
                        reference.child(userID).child("courseonename").setValue(coursetwo);
                        reference.child(userID).child("conedone").setValue(c2done);
                        reference.child(userID).child("conepaid").setValue(c2paid);
                        reference.child(userID).child("coneseen").setValue(c2seen);
                        reference.child(userID).child("courseonelink").setValue(c2link);

                        reference.child(userID).child("coursetwoname").setValue("NA");
                        reference.child(userID).child("ctwodone").setValue("NA");
                        reference.child(userID).child("ctwopaid").setValue("NA");
                        reference.child(userID).child("ctwoseen").setValue("NA");
                        reference.child(userID).child("coursetwolink").setValue("NA");
                    }

                    tv1.setText(courseone);
                    tv2.setText(coursetwo);
                    tv3.setText(c1done);
                    tv4.setText(c2done);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(courseActivity.this,"error!",Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void clickhere(View view){
        String phoneNumber = "+917406401273";
        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + "help");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(sendIntent);
    }

    public void onBackPressed() {
        finish();
    }

}