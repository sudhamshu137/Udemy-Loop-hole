package com.example.udemyat40;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class finalActivity extends AppCompatActivity {

    TextView linktv;
    String exmsg;

    private FirebaseUser user;
    private DatabaseReference reference;
    private  String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        TextView tv = findViewById(R.id.clickhere);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        linktv = findViewById(R.id.link);
        linktv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        linktv.setText("");

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users").child(userID);

        // get the extra message from the previous intent and take the link from firebase
        exmsg = getIntent().getStringExtra("number");
        if(exmsg.equals("one")){
            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                    if(userProfile!=null){
                        String courseonelink = userProfile.courseonelink;

                        linktv.setText(courseonelink);
                        mRef.child("coneseen").setValue("NA");
                        mRef.child("courseonelink").setValue("NA");
                        mRef.child("courseonename").setValue("NA");
                        mRef.child("conepaid").setValue("NA");
                        mRef.child("conedone").setValue("NA");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(finalActivity.this,"error!",Toast.LENGTH_SHORT).show();
                }
            });


        }
        if(exmsg.equals("two")){
            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                    if(userProfile!=null){
                        String coursetwolink = userProfile.coursetwolink;

                        linktv.setText(coursetwolink);
                        mRef.child("ctwoseen").setValue("NA");
                        mRef.child("coursetwolink").setValue("NA");
                        mRef.child("coursetwoname").setValue("NA");
                        mRef.child("ctwopaid").setValue("NA");
                        mRef.child("ctwodone").setValue("NA");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(finalActivity.this,"error!",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void copy(View view){

        //copy text from the link

        if(linktv.getText().toString().trim().equals("")){
            Toast.makeText(finalActivity.this,"please wait",Toast.LENGTH_LONG).show();
        }
        else{
            String resultString = linktv.getText().toString().trim();
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", resultString);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(finalActivity.this, "link copied to clipboard", Toast.LENGTH_LONG).show();

        }
    }

    public void clickhere(View view){
        String phoneNumber = "+917406401273";
        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + "help");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(sendIntent);
    }


    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(finalActivity.this, courseActivity.class);
        startActivity(ii);
        finish();
    }

}