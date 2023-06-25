package com.example.offers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.offers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class   SignUpActivity extends AppCompatActivity {

    EditText inputUsername,inputEmail,inputPassword,inputRepassword;
    Button signupbtn;
    String emailPattern = "[a-zA-Z0-9._-]+[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputUsername=findViewById(R.id.inputUsername);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputRepassword=findViewById(R.id.inputRepassword);
        signupbtn=findViewById(R.id.signupbtn);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });
    }

    private void PerforAuth() {
        String name= inputUsername.getText().toString();
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String repassword=inputRepassword.getText().toString();

        if(!email.matches(emailPattern)){
            inputEmail.setError("Enter Correct Email");
        }else if(password.isEmpty() || password.length()<8)
        {
            inputPassword.setError("Enter Correct Password Format");
        }else if(!password.equals(repassword))
        {
            inputRepassword.setError("Password Does Not Match");
        }else if (name.isEmpty())
        {
            inputUsername.setError("Enter Username");
        }else
        {
            progressDialog.setMessage("Please Wait While Signing Up ....");
            progressDialog.setTitle("SigningUp");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(SignUpActivity.this,"SignUp Successful",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }

    private void sendUserToNextActivity() {
        Intent intent= new Intent(SignUpActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}