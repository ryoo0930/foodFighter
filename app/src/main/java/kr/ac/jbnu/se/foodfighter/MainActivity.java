package kr.ac.jbnu.se.foodfighter;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN_ACTIVITY";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};


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



    }

}

