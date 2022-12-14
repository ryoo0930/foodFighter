package kr.ac.jbnu.se.foodfighter;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

    public String userId;
    public String userFamilyName;
    public String userGivenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId = getIntent().getStringExtra("userId");
        userFamilyName = getIntent().getStringExtra("userFamilyName");
        userGivenName = getIntent().getStringExtra("userGivenName");



        TextView adress_text = (TextView) findViewById(R.id.adress_text);
        adress_text.setText("환영합니다! "+userFamilyName+userGivenName+"님!");

        //Btn_Event=================================================================================

        ImageButton QR_bt = (ImageButton) findViewById(R.id.QR_imgbt);
        QR_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewPageActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);

            }
        });

        ImageButton review_manage_bt = (ImageButton) findViewById(R.id.review_manage_imgbt);
        review_manage_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewListActivity.class);
                intent.putExtra("userId", userId);
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
                point_text.setText(userpoint + "P");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageButton point_bt = (ImageButton) findViewById(R.id.POINT_imgbt);
        point_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PointActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        //IMAGE MAPPPPPPPPP===========
        //덕진동
        ImageView deokjin_30per = (ImageView) findViewById(R.id.deokjin_30per);
        ImageView deokjin_70per = (ImageView) findViewById(R.id.deokjin_70per);
        ImageView deokjin_100per = (ImageView) findViewById(R.id.deokjin_100per);
        myRef.child("reviews").child("덕진동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    deokjin_30per.setVisibility(View.VISIBLE);
                    deokjin_70per.setVisibility(View.INVISIBLE);
                    deokjin_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    deokjin_30per.setVisibility(View.INVISIBLE);
                    deokjin_70per.setVisibility(View.VISIBLE);
                    deokjin_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    deokjin_30per.setVisibility(View.INVISIBLE);
                    deokjin_70per.setVisibility(View.INVISIBLE);
                    deokjin_100per.setVisibility(View.VISIBLE);
                }
                else {
                    deokjin_30per.setVisibility(View.INVISIBLE);
                    deokjin_70per.setVisibility(View.INVISIBLE);
                    deokjin_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //조촌동
        ImageView jochon_30per = (ImageView) findViewById(R.id.jochon_30per);
        ImageView jochon_70per = (ImageView) findViewById(R.id.jochon_70per);
        ImageView jochon_100per = (ImageView) findViewById(R.id.jochon_100per);
        myRef.child("reviews").child("조촌동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    jochon_30per.setVisibility(View.VISIBLE);
                    jochon_70per.setVisibility(View.INVISIBLE);
                    jochon_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    jochon_30per.setVisibility(View.INVISIBLE);
                    jochon_70per.setVisibility(View.VISIBLE);
                    jochon_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    jochon_30per.setVisibility(View.INVISIBLE);
                    jochon_70per.setVisibility(View.INVISIBLE);
                    jochon_100per.setVisibility(View.VISIBLE);
                }
                else {
                    jochon_30per.setVisibility(View.INVISIBLE);
                    jochon_70per.setVisibility(View.INVISIBLE);
                    jochon_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //여의동
        ImageView yeoui_30per = (ImageView) findViewById(R.id.yeoui_30per);
        ImageView yeoui_70per = (ImageView) findViewById(R.id.yeoui_70per);
        ImageView yeoui_100per = (ImageView) findViewById(R.id.yeoui_100per);
        myRef.child("reviews").child("여의동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    yeoui_30per.setVisibility(View.VISIBLE);
                    yeoui_70per.setVisibility(View.INVISIBLE);
                    yeoui_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    yeoui_30per.setVisibility(View.INVISIBLE);
                    yeoui_70per.setVisibility(View.VISIBLE);
                    yeoui_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    yeoui_30per.setVisibility(View.INVISIBLE);
                    yeoui_70per.setVisibility(View.INVISIBLE);
                    yeoui_100per.setVisibility(View.VISIBLE);
                }
                else {
                    yeoui_30per.setVisibility(View.INVISIBLE);
                    yeoui_70per.setVisibility(View.INVISIBLE);
                    yeoui_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //혁신동
        ImageView hyeoksin_30per = (ImageView) findViewById(R.id.hyeoksin_30per);
        ImageView hyeoksin_70per = (ImageView) findViewById(R.id.hyeoksin_70per);
        ImageView hyeoksin_100per = (ImageView) findViewById(R.id.hyeoksin_100per);
        myRef.child("reviews").child("혁신동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    hyeoksin_30per.setVisibility(View.VISIBLE);
                    hyeoksin_70per.setVisibility(View.INVISIBLE);
                    hyeoksin_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    hyeoksin_30per.setVisibility(View.INVISIBLE);
                    hyeoksin_70per.setVisibility(View.VISIBLE);
                    hyeoksin_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    hyeoksin_30per.setVisibility(View.INVISIBLE);
                    hyeoksin_70per.setVisibility(View.INVISIBLE);
                    hyeoksin_100per.setVisibility(View.VISIBLE);
                }
                else {
                    hyeoksin_30per.setVisibility(View.INVISIBLE);
                    hyeoksin_70per.setVisibility(View.INVISIBLE);
                    hyeoksin_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //중앙동
        ImageView jungang_30per = (ImageView) findViewById(R.id.jungang_30per);
        ImageView jungang_70per = (ImageView) findViewById(R.id.jungang_70per);
        ImageView jungang_100per = (ImageView) findViewById(R.id.jungang_100per);
        myRef.child("reviews").child("중앙동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    jungang_30per.setVisibility(View.VISIBLE);
                    jungang_70per.setVisibility(View.INVISIBLE);
                    jungang_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    jungang_30per.setVisibility(View.INVISIBLE);
                    jungang_70per.setVisibility(View.VISIBLE);
                    jungang_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    jungang_30per.setVisibility(View.INVISIBLE);
                    jungang_70per.setVisibility(View.INVISIBLE);
                    jungang_100per.setVisibility(View.VISIBLE);
                }
                else {
                    jungang_30per.setVisibility(View.INVISIBLE);
                    jungang_70per.setVisibility(View.INVISIBLE);
                    jungang_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //풍남동
        ImageView pungnam_30per = (ImageView) findViewById(R.id.pungnam_30per);
        ImageView pungnam_70per = (ImageView) findViewById(R.id.pungnam_70per);
        ImageView pungnam_100per = (ImageView) findViewById(R.id.pungnam_100per);
        myRef.child("reviews").child("풍남동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    pungnam_30per.setVisibility(View.VISIBLE);
                    pungnam_70per.setVisibility(View.INVISIBLE);
                    pungnam_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    pungnam_30per.setVisibility(View.INVISIBLE);
                    pungnam_70per.setVisibility(View.VISIBLE);
                    pungnam_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    pungnam_30per.setVisibility(View.INVISIBLE);
                    pungnam_70per.setVisibility(View.INVISIBLE);
                    pungnam_100per.setVisibility(View.VISIBLE);
                }
                else {
                    pungnam_30per.setVisibility(View.INVISIBLE);
                    pungnam_70per.setVisibility(View.INVISIBLE);
                    pungnam_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //노송동
        ImageView nosong_30per = (ImageView) findViewById(R.id.nosong_30per);
        ImageView nosong_70per = (ImageView) findViewById(R.id.nosong_70per);
        ImageView nosong_100per = (ImageView) findViewById(R.id.nosong_100per);
        myRef.child("reviews").child("노송동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    nosong_30per.setVisibility(View.VISIBLE);
                    nosong_70per.setVisibility(View.INVISIBLE);
                    nosong_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    nosong_30per.setVisibility(View.INVISIBLE);
                    nosong_70per.setVisibility(View.VISIBLE);
                    nosong_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    nosong_30per.setVisibility(View.INVISIBLE);
                    nosong_70per.setVisibility(View.INVISIBLE);
                    nosong_100per.setVisibility(View.VISIBLE);
                }
                else {
                    nosong_30per.setVisibility(View.INVISIBLE);
                    nosong_70per.setVisibility(View.INVISIBLE);
                    nosong_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //완산동
        ImageView wansan_30per = (ImageView) findViewById(R.id.wansan_30per);
        ImageView wansan_70per = (ImageView) findViewById(R.id.wansan_70per);
        ImageView wansan_100per = (ImageView) findViewById(R.id.wansan_100per);
        myRef.child("reviews").child("완산동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    wansan_30per.setVisibility(View.VISIBLE);
                    wansan_70per.setVisibility(View.INVISIBLE);
                    wansan_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    wansan_30per.setVisibility(View.INVISIBLE);
                    wansan_70per.setVisibility(View.VISIBLE);
                    wansan_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    wansan_30per.setVisibility(View.INVISIBLE);
                    wansan_70per.setVisibility(View.INVISIBLE);
                    wansan_100per.setVisibility(View.VISIBLE);
                }
                else {
                    wansan_30per.setVisibility(View.INVISIBLE);
                    wansan_70per.setVisibility(View.INVISIBLE);
                    wansan_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //서학동
        ImageView seohak_30per = (ImageView) findViewById(R.id.seohak_30per);
        ImageView seohak_70per = (ImageView) findViewById(R.id.seohak_70per);
        ImageView seohak_100per = (ImageView) findViewById(R.id.seohak_100per);
        myRef.child("reviews").child("서학동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    seohak_30per.setVisibility(View.VISIBLE);
                    seohak_70per.setVisibility(View.INVISIBLE);
                    seohak_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    seohak_30per.setVisibility(View.INVISIBLE);
                    seohak_70per.setVisibility(View.VISIBLE);
                    seohak_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    seohak_30per.setVisibility(View.INVISIBLE);
                    seohak_70per.setVisibility(View.INVISIBLE);
                    seohak_100per.setVisibility(View.VISIBLE);
                }
                else {
                    seohak_30per.setVisibility(View.INVISIBLE);
                    seohak_70per.setVisibility(View.INVISIBLE);
                    seohak_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //중화산동
        ImageView junghwasan_30per = (ImageView) findViewById(R.id.junghwasan_30per);
        ImageView junghwasan_70per = (ImageView) findViewById(R.id.junghwasan_70per);
        ImageView junghwasan_100per = (ImageView) findViewById(R.id.junghwasan_100per);
        myRef.child("reviews").child("중화산동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    junghwasan_30per.setVisibility(View.VISIBLE);
                    junghwasan_70per.setVisibility(View.INVISIBLE);
                    junghwasan_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    junghwasan_30per.setVisibility(View.INVISIBLE);
                    junghwasan_70per.setVisibility(View.VISIBLE);
                    junghwasan_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    junghwasan_30per.setVisibility(View.INVISIBLE);
                    junghwasan_70per.setVisibility(View.INVISIBLE);
                    junghwasan_100per.setVisibility(View.VISIBLE);
                }
                else {
                    junghwasan_30per.setVisibility(View.INVISIBLE);
                    junghwasan_70per.setVisibility(View.INVISIBLE);
                    junghwasan_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //평화동
        ImageView pyeonghwa_30per = (ImageView) findViewById(R.id.pyeonghwa_30per);
        ImageView pyeonghwa_70per = (ImageView) findViewById(R.id.pyeonghwa_70per);
        ImageView pyeonghwa_100per = (ImageView) findViewById(R.id.pyeonghwa_100per);
        myRef.child("reviews").child("평화동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    pyeonghwa_30per.setVisibility(View.VISIBLE);
                    pyeonghwa_70per.setVisibility(View.INVISIBLE);
                    pyeonghwa_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    pyeonghwa_30per.setVisibility(View.INVISIBLE);
                    pyeonghwa_70per.setVisibility(View.VISIBLE);
                    pyeonghwa_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    pyeonghwa_30per.setVisibility(View.INVISIBLE);
                    pyeonghwa_70per.setVisibility(View.INVISIBLE);
                    pyeonghwa_100per.setVisibility(View.VISIBLE);
                }
                else {
                    pyeonghwa_30per.setVisibility(View.INVISIBLE);
                    pyeonghwa_70per.setVisibility(View.INVISIBLE);
                    pyeonghwa_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //서신동
        ImageView seosin_30per = (ImageView) findViewById(R.id.seosin_30per);
        ImageView seosin_70per = (ImageView) findViewById(R.id.seosin_70per);
        ImageView seosin_100per = (ImageView) findViewById(R.id.seosin_100per);
        myRef.child("reviews").child("서신동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    seosin_30per.setVisibility(View.VISIBLE);
                    seosin_70per.setVisibility(View.INVISIBLE);
                    seosin_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    seosin_30per.setVisibility(View.INVISIBLE);
                    seosin_70per.setVisibility(View.VISIBLE);
                    seosin_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    seosin_30per.setVisibility(View.INVISIBLE);
                    seosin_70per.setVisibility(View.INVISIBLE);
                    seosin_100per.setVisibility(View.VISIBLE);
                }
                else {
                    seosin_30per.setVisibility(View.INVISIBLE);
                    seosin_70per.setVisibility(View.INVISIBLE);
                    seosin_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //삼천동
        ImageView samcheon_30per = (ImageView) findViewById(R.id.samcheon_30per);
        ImageView samcheon_70per = (ImageView) findViewById(R.id.samcheon_70per);
        ImageView samcheon_100per = (ImageView) findViewById(R.id.samcheon_100per);
        myRef.child("reviews").child("삼천동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    samcheon_30per.setVisibility(View.VISIBLE);
                    samcheon_70per.setVisibility(View.INVISIBLE);
                    samcheon_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    samcheon_30per.setVisibility(View.INVISIBLE);
                    samcheon_70per.setVisibility(View.VISIBLE);
                    samcheon_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    samcheon_30per.setVisibility(View.INVISIBLE);
                    samcheon_70per.setVisibility(View.INVISIBLE);
                    samcheon_100per.setVisibility(View.VISIBLE);
                }
                else {
                    samcheon_30per.setVisibility(View.INVISIBLE);
                    samcheon_70per.setVisibility(View.INVISIBLE);
                    samcheon_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //효자동
        ImageView hyoja_30per = (ImageView) findViewById(R.id.hyoja_30per);
        ImageView hyoja_70per = (ImageView) findViewById(R.id.hyoja_70per);
        ImageView hyoja_100per = (ImageView) findViewById(R.id.hyoja_100per);
        myRef.child("reviews").child("효자동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    hyoja_30per.setVisibility(View.VISIBLE);
                    hyoja_70per.setVisibility(View.INVISIBLE);
                    hyoja_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    hyoja_30per.setVisibility(View.INVISIBLE);
                    hyoja_70per.setVisibility(View.VISIBLE);
                    hyoja_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    hyoja_30per.setVisibility(View.INVISIBLE);
                    hyoja_70per.setVisibility(View.INVISIBLE);
                    hyoja_100per.setVisibility(View.VISIBLE);
                }
                else {
                    hyoja_30per.setVisibility(View.INVISIBLE);
                    hyoja_70per.setVisibility(View.INVISIBLE);
                    hyoja_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //진북동
        ImageView jinbuk_30per = (ImageView) findViewById(R.id.jinbuk_30per);
        ImageView jinbuk_70per = (ImageView) findViewById(R.id.jinbuk_70per);
        ImageView jinbuk_100per = (ImageView) findViewById(R.id.jinbuk_100per);
        myRef.child("reviews").child("진북동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    jinbuk_30per.setVisibility(View.VISIBLE);
                    jinbuk_70per.setVisibility(View.INVISIBLE);
                    jinbuk_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    jinbuk_30per.setVisibility(View.INVISIBLE);
                    jinbuk_70per.setVisibility(View.VISIBLE);
                    jinbuk_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    jinbuk_30per.setVisibility(View.INVISIBLE);
                    jinbuk_70per.setVisibility(View.INVISIBLE);
                    jinbuk_100per.setVisibility(View.VISIBLE);
                }
                else {
                    jinbuk_30per.setVisibility(View.INVISIBLE);
                    jinbuk_70per.setVisibility(View.INVISIBLE);
                    jinbuk_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //인후동
        ImageView inhu_30per = (ImageView) findViewById(R.id.inhu_30per);
        ImageView inhu_70per = (ImageView) findViewById(R.id.inhu_70per);
        ImageView inhu_100per = (ImageView) findViewById(R.id.inhu_100per);
        myRef.child("reviews").child("인후동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    inhu_30per.setVisibility(View.VISIBLE);
                    inhu_70per.setVisibility(View.INVISIBLE);
                    inhu_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    inhu_30per.setVisibility(View.INVISIBLE);
                    inhu_70per.setVisibility(View.VISIBLE);
                    inhu_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    inhu_30per.setVisibility(View.INVISIBLE);
                    inhu_70per.setVisibility(View.INVISIBLE);
                    inhu_100per.setVisibility(View.VISIBLE);
                }
                else {
                    inhu_30per.setVisibility(View.INVISIBLE);
                    inhu_70per.setVisibility(View.INVISIBLE);
                    inhu_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //금암동
        ImageView geumam_30per = (ImageView) findViewById(R.id.geumam_30per);
        ImageView geumam_70per = (ImageView) findViewById(R.id.geumam_70per);
        ImageView geumam_100per = (ImageView) findViewById(R.id.geumam_100per);
        myRef.child("reviews").child("금암동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    geumam_30per.setVisibility(View.VISIBLE);
                    geumam_70per.setVisibility(View.INVISIBLE);
                    geumam_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    geumam_30per.setVisibility(View.INVISIBLE);
                    geumam_70per.setVisibility(View.VISIBLE);
                    geumam_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    geumam_30per.setVisibility(View.INVISIBLE);
                    geumam_70per.setVisibility(View.INVISIBLE);
                    geumam_100per.setVisibility(View.VISIBLE);
                }
                else {
                    geumam_30per.setVisibility(View.INVISIBLE);
                    geumam_70per.setVisibility(View.INVISIBLE);
                    geumam_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //팔복동
        ImageView palbok_30per = (ImageView) findViewById(R.id.palbok_30per);
        ImageView palbok_70per = (ImageView) findViewById(R.id.palbok_70per);
        ImageView palbok_100per = (ImageView) findViewById(R.id.palbok_100per);
        myRef.child("reviews").child("팔복동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    palbok_30per.setVisibility(View.VISIBLE);
                    palbok_70per.setVisibility(View.INVISIBLE);
                    palbok_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    palbok_30per.setVisibility(View.INVISIBLE);
                    palbok_70per.setVisibility(View.VISIBLE);
                    palbok_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    palbok_30per.setVisibility(View.INVISIBLE);
                    palbok_70per.setVisibility(View.INVISIBLE);
                    palbok_100per.setVisibility(View.VISIBLE);
                }
                else {
                    palbok_30per.setVisibility(View.INVISIBLE);
                    palbok_70per.setVisibility(View.INVISIBLE);
                    palbok_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //우아동
        ImageView ua_30per = (ImageView) findViewById(R.id.ua_30per);
        ImageView ua_70per = (ImageView) findViewById(R.id.ua_70per);
        ImageView ua_100per = (ImageView) findViewById(R.id.ua_100per);
        myRef.child("reviews").child("우아동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    ua_30per.setVisibility(View.VISIBLE);
                    ua_70per.setVisibility(View.INVISIBLE);
                    ua_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    ua_30per.setVisibility(View.INVISIBLE);
                    ua_70per.setVisibility(View.VISIBLE);
                    ua_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    ua_30per.setVisibility(View.INVISIBLE);
                    ua_70per.setVisibility(View.INVISIBLE);
                    ua_100per.setVisibility(View.VISIBLE);
                }
                else {
                    ua_30per.setVisibility(View.INVISIBLE);
                    ua_70per.setVisibility(View.INVISIBLE);
                    ua_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //호성동
        ImageView hosung_30per = (ImageView) findViewById(R.id.hosung_30per);
        ImageView hosung_70per = (ImageView) findViewById(R.id.hosung_70per);
        ImageView hosung_100per = (ImageView) findViewById(R.id.hosung_100per);
        myRef.child("reviews").child("호성동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    hosung_30per.setVisibility(View.VISIBLE);
                    hosung_70per.setVisibility(View.INVISIBLE);
                    hosung_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    hosung_30per.setVisibility(View.INVISIBLE);
                    hosung_70per.setVisibility(View.VISIBLE);
                    hosung_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    hosung_30per.setVisibility(View.INVISIBLE);
                    hosung_70per.setVisibility(View.INVISIBLE);
                    hosung_100per.setVisibility(View.VISIBLE);
                }
                else {
                    hosung_30per.setVisibility(View.INVISIBLE);
                    hosung_70per.setVisibility(View.INVISIBLE);
                    hosung_100per.setVisibility(View.INVISIBLE);
                }
            }
        });
        //송천동
        ImageView songcheon_30per = (ImageView) findViewById(R.id.songcheon_30per);
        ImageView songcheon_70per = (ImageView) findViewById(R.id.songcheon_70per);
        ImageView songcheon_100per = (ImageView) findViewById(R.id.songcheon_100per);
        myRef.child("reviews").child("송천동").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                if (newId.intValue() > 0 && newId.intValue() < 20) {
                    songcheon_30per.setVisibility(View.VISIBLE);
                    songcheon_70per.setVisibility(View.INVISIBLE);
                    songcheon_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 20 && newId.intValue() < 50) {
                    songcheon_30per.setVisibility(View.INVISIBLE);
                    songcheon_70per.setVisibility(View.VISIBLE);
                    songcheon_100per.setVisibility(View.INVISIBLE);
                }
                else if (newId.intValue() >= 50) {
                    songcheon_30per.setVisibility(View.INVISIBLE);
                    songcheon_70per.setVisibility(View.INVISIBLE);
                    songcheon_100per.setVisibility(View.VISIBLE);
                }
                else {
                    songcheon_30per.setVisibility(View.INVISIBLE);
                    songcheon_70per.setVisibility(View.INVISIBLE);
                    songcheon_100per.setVisibility(View.INVISIBLE);
                }
            }
        });







        //========================================================
    }

}

