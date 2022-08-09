package com.example.mcc_deliveryapp.User;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mcc_deliveryapp.MainActivity2;
import com.example.mcc_deliveryapp.R;
import com.example.mcc_deliveryapp.Rider.editprofile_fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class user_profile_fragment extends Fragment {
    private TextView userName, userPhone;
    private String phone, imgName;
    private View view;

    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference, imageReference;

    private ImageView profile_user;

    private ImageButton btn_editProfile;
    private Button btnRider_Logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_profile_fragment, container, false);
        profile_user = view.findViewById(R.id.profile_user);
        userName = view.findViewById(R.id.user_name);
        userPhone =  view.findViewById(R.id.user_number);
        btn_editProfile = view.findViewById(R.id.btnUser_EditProfile);
        btnRider_Logout = view.findViewById(R.id.btnRider_Logout);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        Intent intent = getActivity().getIntent();
        phone = intent.getStringExtra("phonenum");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    if (ds.child("userPhone").getValue().equals(phone)){
                        userName.setText(ds.child("userFullname").getValue(String.class));
                        userPhone.setText(ds.child("userPhone").getValue(String.class));

                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }



        });

        profile_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        //retrieved courier's profile picture from firebase storage
        imageReference = FirebaseStorage.getInstance().getReference().child("user/"+phone+"/profile_image.jpg");
        try{
            final File file= File.createTempFile("profile_image", "jpg");
            imageReference.getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                            ((ImageView)view.findViewById(R.id.profile_user)).setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }catch (IOException e){
            e.printStackTrace();
        }
        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), user_editprofile_fragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                intent.putExtra("userPhone", phone);
                intent.putExtra("userFullname", userName.getText().toString());
                view.getContext().startActivity(intent);
            }
        });

        btnRider_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity2.class);
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }
    //upload picture for user profile
    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            profile_user.setImageURI(imageUri);
            imgName="profile_image.jpg";
            uploadPicture();
        }
    }
    //upload method
    private void uploadPicture() {
        final ProgressDialog pd= new ProgressDialog(view.getContext());
        pd.setTitle("Uploading Image");
        pd.show();

        StorageReference riversRef=storageReference.child("user/"+phone+"/"+imgName);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int) progressPercent +"%");
                    }
                });


    }
}