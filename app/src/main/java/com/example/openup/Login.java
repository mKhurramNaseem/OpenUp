package com.example.openup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    TextView logsignup;
Button button;
EditText email,password;
FirebaseAuth auth;
FirebaseDatabase database;

String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

android.app.ProgressDialog progressDialog;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        database=FirebaseDatabase.getInstance("https://openup-645ec-default-rtdb.firebaseio.com/").getReference().getDatabase();

        auth= FirebaseAuth.getInstance();
        button=findViewById(R.id.logbutton);
        email=findViewById(R.id.editTextlogEmail);
        password=findViewById(R.id.editTextlogPassword);
        logsignup=findViewById(R.id.logsignup);
        logsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,registration.class);
                startActivity(intent);
                finish();
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String Email=email.getText().toString();
                 String pass=password.getText().toString();

                 if((TextUtils.isEmpty(Email))){
                     progressDialog.dismiss();
                     Toast.makeText(Login.this,"Enter The E-mail", Toast.LENGTH_SHORT).show();
                 } else if (TextUtils.isEmpty(pass)) {
                     progressDialog.dismiss();


                     Toast.makeText(Login.this,"Enter The Password", Toast.LENGTH_SHORT).show();

                 } else if (!Email.matches(emailPattern)) {
                     progressDialog.dismiss();

                     email.setError("Give Proper Email Address");
                 } else if (password.length()<6) {
                     progressDialog.dismiss();

                     password.setError("More Then Six Characters");
                     Toast.makeText(Login.this,"Password Needs To Be Longer Then Six Characters", Toast.LENGTH_SHORT).show();

                 }else{
                     auth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {

                             if(task.isSuccessful()){
                                 progressDialog.show();

                                 try{
                                     Intent intent=new Intent(Login.this, MainActivity.class);
                                     startActivity(intent);
                                     finish();
                                 }catch (Exception e){
                                     Toast.makeText(Login.this, e.getMessage(),Toast.LENGTH_SHORT).show();


                                 }
                             }else{
                                 Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
                 }




            }
        });



    }
}