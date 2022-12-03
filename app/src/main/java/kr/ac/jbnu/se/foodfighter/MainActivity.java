package kr.ac.jbnu.se.foodfighter;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicLong;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN_ACTIVITY";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Btn_Event=================================================================================

        ImageButton QR_bt = (ImageButton) findViewById(R.id.QR_imgbt);
        QR_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewPageActivity.class);
                startActivity(intent);
            }
        });

        ImageButton review_manage_bt = (ImageButton) findViewById(R.id.review_manage_imgbt);
        review_manage_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewListActivity.class);
                startActivity(intent);
            }
        });



        ImageButton search_bt = (ImageButton) findViewById(R.id.search_imgbt);
        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GoogleMapActivity.class);
                startActivity(intent);
            }
        });


        TextView point_text = (TextView) findViewById(R.id.point_text);
        myRef.child("point").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int userpoint = snapshot.getValue(int.class);
                point_text.setText(userpoint + " P");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

}

