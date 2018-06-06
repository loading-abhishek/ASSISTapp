package com.theworkingbros.ak.assist.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.theworkingbros.ak.assist.R;

public class LoginPage extends AppCompatActivity {
 private Button registerbtn,loginbtn;
 private FirebaseUser mUser;
 private FirebaseAuth mAuth;
 private FirebaseAuth.AuthStateListener mAuthListener;
 private EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        loginbtn=findViewById(R.id.loginbtn);
        registerbtn=findViewById(R.id.registerbtn);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser=firebaseAuth.getCurrentUser();

                if(mUser!=null)
                {
                    Toast.makeText(LoginPage.this,"Signed In",Toast.LENGTH_LONG).show();
                    Intent main=new Intent(LoginPage.this,MainActivity.class);
                    startActivity(main);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginPage.this,"Not Signed In",Toast.LENGTH_LONG).show();
                }
            }
        };
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(email.getText().toString())&&
                        !TextUtils.isEmpty(password.getText().toString()))
                {
                    String emailid= email.getText().toString();
                    String pwd=password.getText().toString();
                    login(emailid,pwd);
                }
                else
                {

                }
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(LoginPage.this, Register.class);
                startActivity(register);

            }
        });


        }

    private void login(String emailid, String pwd) {
        mAuth.signInWithEmailAndPassword(emailid,pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginPage.this,"Signed In",Toast.LENGTH_LONG).show();
                            Intent login=new Intent(LoginPage.this,MainActivity.class);
                            startActivity(login);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginPage.this,"Unsuccessful",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
