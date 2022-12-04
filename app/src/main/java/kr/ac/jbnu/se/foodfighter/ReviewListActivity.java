package kr.ac.jbnu.se.foodfighter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ReviewListActivity extends AppCompatActivity {

    ListView listView;
    String[] locationItems = {"중앙동", "풍남동", "노송동", "완산동", "서학동", "중화산동", "평화동", "서신동", "삼천동", "효자동", "진북동", "인후동", "덕진동", "금암동", "팔복동", "우아동", "호성동", "송천동", "조촌동", "여의동", "혁신동"};
    String location = "덕진동";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>()
        );

        listView = (ListView) findViewById(R.id.listview);

        ArrayList<String> reviews = new ArrayList<>();

        listView.setAdapter(adapter);

        //spinner=======

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, locationItems
        );

        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(spinneradapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location = locationItems[i];

                adapter.clear();

                myRef.child("reviews").child(location).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            String reviewtitle = postSnapshot.getValue(Review.class).getTitle();
                            adapter.add(reviewtitle);

                        }
                        // list view update

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // TODO: not implemented
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //==================


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ReviewListActivity.this, ReviewDetailActivity.class);
                intent.putExtra("board_seq", Integer.toString(i));
                startActivity(intent);
            }
        });

    }
}
