package com.example.openup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class registration extends AppCompatActivity {
    TextView loginbut;
    EditText rg_username,rg_password,rg_email,rg_repassword;
    Button rg_signup;
    CircleImageView rg_profileImg;
    FirebaseAuth auth;
    Uri imageURI;
    String imageuri;
    String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");
        progressDialog.setCancelable(false);

        database=FirebaseDatabase.getInstance("https://openup-645ec-default-rtdb.firebaseio.com/").getReference().getDatabase();
        storage= FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        loginbut=findViewById(R.id.loginbut);
        rg_username=findViewById(R.id.rgusername);
        rg_email=findViewById(R.id.rgemail);
        rg_password=findViewById(R.id.rgpassword);
        rg_repassword=findViewById(R.id.rgrepassword);
        rg_profileImg=findViewById(R.id.profilerg0);
        rg_signup=findViewById(R.id.signupbutton);



        loginbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(registration.this,Login.class);
                startActivity(intent);
                finish();

            }
        });

        rg_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namee =rg_username.getText().toString();
                String emaill=rg_email.getText().toString();
                String password=rg_password.getText().toString();
                String cpassword=rg_repassword.getText().toString();
                String status="Hey, I'am Using This Application";


                if(TextUtils.isEmpty(namee)|| TextUtils.isEmpty(emaill)||TextUtils.isEmpty(password)||TextUtils.isEmpty(cpassword)){
                    progressDialog.dismiss();
                    Toast.makeText(registration.this,"Please Enter Valid Information",Toast.LENGTH_SHORT).show();

                } else if (!emaill.matches(emailPattern)) {
                    progressDialog.dismiss();

                    rg_email.setError("Type A Valid E-mail Here.");

                } else if (password.length()<6) {
                    progressDialog.dismiss();

                    rg_password.setError("Password Must Be 6 Characters or More");
                } else if (!password.equals(cpassword)) {
                    progressDialog.dismiss();

                    rg_password.setError("The Password Doesn't Match");
                }else {
                    auth.createUserWithEmailAndPassword(emaill,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                String id= task.getResult().getUser().getUid();
                                DatabaseReference reference=database.getReference().child("user").child(id);
                                StorageReference storageReference=storage.getReference().child("Upload").child(id);

                                if(imageURI!=null){
                                    storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if(task.isSuccessful()) {
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageuri= uri.toString();
                                                        Users users= new Users(id,namee,emaill,password,cpassword,imageuri,status);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    progressDialog.show();

                                                                    Intent intent=new Intent(registration.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }else {
                                                                    Toast.makeText(registration.this,"Error in creating the user",Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }


                                        }
                                    });
                                }else {
                                    String status="Hey, I'am Using This Application";
                                    imageuri="https://firebasestorage.googleapis.com/v0/b/openup-645ec.appspot.com/o/mmain.jpg?alt=media&token=c9a682fe-901f-45d7-acaf-c674a9b8e089";
                                    Users users= new Users(id,namee,emaill,password, cpassword,imageuri,status);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                progressDialog.show();

                                                Intent intent=new Intent(registration.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }else {
                                                Toast.makeText(registration.this,"Error in creating the user",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }else {
                                Toast.makeText(registration.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
                }


            }
        });



        rg_profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(data!=null){
                imageURI=data.getData() ;
                rg_profileImg.setImageURI(imageURI);



            }
        }

    }
}