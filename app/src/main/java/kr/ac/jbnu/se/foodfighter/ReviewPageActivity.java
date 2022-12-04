package kr.ac.jbnu.se.foodfighter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicLong;

public class ReviewPageActivity extends AppCompatActivity {


    private EditText reviewEdit;
    private Button okButton;
    private Button cancelButton;
    private Button scan_bt;
    private TextView titleView;

    private IntentIntegrator qrScan;
    String location;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);



        okButton = (Button) findViewById(R.id.okButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        scan_bt = (Button) findViewById(R.id.scan_bt);
        reviewEdit = (EditText) findViewById(R.id.reviewEdit);
        titleView = (TextView) findViewById(R.id.titleView);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comparetitle(titleView.getText().toString());
                addReviewTable(titleView.getText().toString(), reviewEdit.getText().toString());
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        qrScan = new IntentIntegrator(this);

        //button onClick
        scan_bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //scan option
                qrScan.setPrompt("Scanning...");
                //qrScan.setOrientationLocked(false);
                qrScan.initiateScan();


            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(ReviewPageActivity.this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(ReviewPageActivity.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                try {
                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());
                    titleView.setText(obj.getString("restaurantName"));
                    location = obj.getString("Location");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //데이터 비교 후 포인트 추가============
    public void comparetitle(String title) {
        myRef.child("point").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int value = (int)snapshot.getValue(Integer.class);
                value += 500;
                myRef.child("point").setValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.child("reviews").child(location).child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    String reviewtitle = postSnapshot.getValue(String.class);
                    if (reviewtitle == title) {
                        myRef.child("point").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int value = (int)snapshot.getValue(Integer.class);
                                value -= 500;
                                myRef.child("point").setValue(value);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // TODO: not implemented
            }
        });
    }
    //==================================
    public void addReviewTable(String title, String review) {
        ReviewTable ReviewTable = new ReviewTable(title, review);

        myRef.child("reviews").child(location).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                AtomicLong newId = new AtomicLong();
                newId.set(task.getResult().getChildrenCount());
                myRef.child("reviews").child(location).child(newId.toString()).setValue(new Review(title, review));
            }
        });

    }
}
