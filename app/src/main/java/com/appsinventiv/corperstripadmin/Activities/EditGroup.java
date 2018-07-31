package com.appsinventiv.corperstripadmin.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.appsinventiv.corperstripadmin.GroupModel;
import com.appsinventiv.corperstripadmin.R;
import com.appsinventiv.corperstripadmin.User;
import com.appsinventiv.corperstripadmin.Utils.CommonUtils;
import com.appsinventiv.corperstripadmin.Utils.CompressImage;
import com.appsinventiv.corperstripadmin.Utils.Constants;
import com.appsinventiv.corperstripadmin.Utils.GifSizeFilter;
import com.appsinventiv.corperstripadmin.Utils.NotificationAsync;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditGroup extends AppCompatActivity {
    Button update;
    DatabaseReference mDatabase;
    EditText e_name, e_description;
    String profileUrl;
    List<Uri> mSelected = new ArrayList<>();
    ArrayList<String> imageUrl = new ArrayList<>();
    StorageReference mStorageRef;


    CircleImageView profile_image;
    private static final int REQUEST_CODE_CHOOSE = 23;
    RelativeLayout wholeLayout;
    String key;
    GroupModel model;
    Switch switchs;
    Boolean isActive;
    ArrayList<User> userArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        this.setTitle("Edit Group");
        Intent i = getIntent();
        key = i.getStringExtra("groupId");


        mDatabase = FirebaseDatabase.getInstance().getReference();

        switchs=findViewById(R.id.switchs);
        e_name = findViewById(R.id.name);
        e_description = findViewById(R.id.groupName);
        update = findViewById(R.id.updateGroup);
        wholeLayout = findViewById(R.id.wholeLayout);
        profile_image = findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelected.clear();
                imageUrl.clear();
                initMatisse();
            }
        });

        switchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isActive=b;
            }
        });

        mDatabase.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot!=null){
                    User user=dataSnapshot.getValue(User.class);
                    if(user!=null){
                        userArrayList.add(user);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("Groups").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    model = dataSnapshot.getValue(GroupModel.class);
                    if (model != null) {
                        isActive=model.isActive();
                        if(model.isActive()){
                            switchs.setChecked(true);
                        }else{
                           switchs.setChecked(false);
                        }
                        Glide.with(EditGroup.this).load(model.getPicUrl()).placeholder(R.drawable.ic_group).into(profile_image);
                        e_name.setText(model.getName());
                        e_description.setText(model.getDescription());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (e_name.getText().length() == 0) {
                    e_name.setError("Enter name");
                } else if (e_description.getText().length() == 0) {
                    e_description.setError("Enter description");

                } else {
                    wholeLayout.setVisibility(View.VISIBLE);
                    mDatabase.child("Groups").child(key).setValue(new GroupModel(key,
                            e_name.getText().toString(),
                            e_description.getText().toString()
                            , model.getLastMessage(), model.getType()
                            , model.getPicUrl(),
                            isActive, model.getTime())).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            CommonUtils.showToast("Group Updated");
                            Intent i=new Intent(EditGroup.this,MainActivity.class);
                            startActivity(i);
                            if (imageUrl.isEmpty()) {

                            } else {
                                for (String im : imageUrl) {
                                    putPictures(im);
                                }
                                finish();
                            }
                            for (User u:userArrayList) {
                                NotificationAsync notificationAsync = new NotificationAsync(EditGroup.this);
                                String NotificationTitle = "test";
                                String NotificationMessage = "test";
                                notificationAsync.execute("ali", u.getFcmKey(), NotificationTitle, NotificationMessage, ""+isActive, key, "");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            CommonUtils.showToast("Error\nPlease try again");
                        }
                    });
                }
            }
        });



    }

    private void initMatisse() {
        Matisse.from(EditGroup.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE && data != null) {
            mSelected = Matisse.obtainResult(data);
            Glide.with(EditGroup.this).load(mSelected.get(0)).into(profile_image);
            for (Uri img : mSelected) {
                CompressImage compressImage = new CompressImage(EditGroup.this);
                imageUrl.add(compressImage.compressImage("" + img));
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void putPictures(String path) {
        String imgName = Long.toHexString(Double.doubleToLongBits(Math.random()));

        ;
        Uri file = Uri.fromFile(new File(path));

        mStorageRef = FirebaseStorage.getInstance().getReference();

        StorageReference riversRef = mStorageRef.child("Photos").child(imgName);

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    @SuppressWarnings("VisibleForTests")
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        profileUrl = "" + downloadUrl;
                        mDatabase.child("Groups").child(key).child("picUrl").setValue("" + downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                CommonUtils.showToast("Group Created");
                                finish();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        CommonUtils.showToast(exception.getMessage() + "");

                    }
                });


    }


    private void getPermissions() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,

        };

        if (!hasPermissions(EditGroup.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }


    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                } else {

                }
            }
        }
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
