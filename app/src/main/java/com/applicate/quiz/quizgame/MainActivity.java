package com.applicate.quiz.quizgame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btLogin;
    private EditText etMail;
    private EditText etPassword;
    private TextView tvRegister;

    private ProgressDialog pDLogin;

    private FirebaseAuth firebaseLogin;
    private FirebaseAuth.AuthStateListener loginListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLogin = (Button) findViewById(R.id.btLogin);
        etMail = (EditText) findViewById(R.id.etMail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegister = (TextView) findViewById(R.id.tvRegister);

        pDLogin = new ProgressDialog(this);

        firebaseLogin = FirebaseAuth.getInstance();

        loginListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //user is signed in - el usuario esta logeado
                    Log.d("Logeo", "Usuario registrado" + user.getUid());
                }else{
                    //User is signed out - el usuario no esta logeado
                    Log.d("Logeo", "Usuario no registrado");
                }
            }
        };

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(view);
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseLogin.addAuthStateListener(loginListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(loginListener != null){
            firebaseLogin.removeAuthStateListener(loginListener);
        }
    }

    //method to login the user - método para logear al usuario
    private void loginUser(View view){
        String mail = etMail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(mail)){
            //the mail is empty
            //Toast.makeText(this, "Please, insert a valid mail", Toast.LENGTH_LONG).show();
            Snackbar.make(view,"Please, insert a valid mail",Snackbar.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            //the password is empty
            //Toast.makeText(this,"Please, insert a password", Toast.LENGTH_LONG).show();
            Snackbar.make(view,"Please, insert a password",Snackbar.LENGTH_LONG).show();
            return;
        }

        pDLogin.setMessage("Log in ...");
        pDLogin.show();

        firebaseLogin.signInWithEmailAndPassword(mail,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Log In", "signInWithEmail:onComplete" + task.isSuccessful());
                //If sign in fails, display a message to the user.
                //If sign in succeeds the auth state listener will be notified and
                //logic to handle the signed in user can be handled in the listener

                if (!task.isSuccessful()){
                    pDLogin.cancel();
                    Log.w("Log In", "signInWithEmail:failed", task.getException());
                    Toast.makeText(MainActivity.this, "Usuario o Password in correcto", Toast.LENGTH_LONG).show();
                    //TODO: Intentar hacerlo con SnackBar
                }
            }
        });

    }
}


