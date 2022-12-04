package kr.ac.jbnu.se.foodfighter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;



public class ReviewDetailActivity extends AppCompatActivity {



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String board_seq = intent.getStringExtra("board_seq");

        TextView title_tv = (TextView) findViewById(R.id.title_tv);
        myRef.child("reviews").child("덕진동").child(board_seq).child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                title_tv.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TextView content_tv = (TextView) findViewById(R.id.content_tv);
        myRef.child("reviews").child("덕진동").child(board_seq).child("content").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                content_tv.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


}
