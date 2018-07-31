package com.appsinventiv.corperstripadmin.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appsinventiv.corperstripadmin.GroupModel;
import com.appsinventiv.corperstripadmin.GroupsAdapter;
import com.appsinventiv.corperstripadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    DatabaseReference mDatabase;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<GroupModel> groupModelArrayList = new ArrayList<>();
    ArrayList<String> userGroupsArrayList = new ArrayList<>();
    GroupsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddGroup.class);
                startActivity(i);

            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GroupsAdapter(this, groupModelArrayList);
        recyclerView.setAdapter(adapter);



    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllGroupsFromServer();
    }

    private void getAllGroupsFromServer() {
        mDatabase.child("Groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    groupModelArrayList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        GroupModel model = snapshot.getValue(GroupModel.class);
                        if (model != null) {
                            groupModelArrayList.add(model);
                            Collections.sort(groupModelArrayList, new Comparator<GroupModel>() {
                                @Override
                                public int compare(GroupModel listData, GroupModel t1) {
                                    String ob1 = listData.getName();
                                    String ob2 = t1.getName();

                                    return ob1.compareTo(ob2);

                                }
                            });
                            adapter.notifyDataSetChanged();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
