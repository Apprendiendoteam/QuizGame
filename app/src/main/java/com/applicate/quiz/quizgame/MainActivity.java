package com.applicate.quiz.quizgame;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.etMail)
    EditText etMail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.btLogin)
    Button btLogin;
    @Bind(R.id.tvRegister)
    TextView tvRegister;

    /*
    private Button btLogin;
    private EditText etMail;
    private EditText etPassword;
    private TextView tvRegister;
    */

    private ProgressDialog pDLogin;

    private FirebaseAuth firebaseLogin;
    private FirebaseAuth.AuthStateListener loginListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Para insertar las vistas con el ButterKnife
        ButterKnife.bind(this);

        pDLogin = new ProgressDialog(this);

        firebaseLogin = FirebaseAuth.getInstance();

        //el listener para saber si el usuario ya esta registrado
        loginListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in - el usuario esta logeado
                    Log.d(TAG, "Usuario registrado " + user.getUid());
                    Log.d(TAG, "Usuario " + user.getEmail());
                    Intent intent = new Intent(MainActivity.this, LateralActivity.class);
                    startActivity(intent);

                } else {
                    //user is signed out - el usuario no esta logeado
                    Log.d(TAG, "Usuario no registrado");
                }
            }
        };

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                //Si queremos que se logee directamente al venir del register
                //startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }

    //method to login the user - método para logear al usuario
    private void loginUser() {

        Log.d(TAG, "Login");

        if (!validate()){
            onLoginFailed();
            return;
        }

        btLogin.setEnabled(false);

        pDLogin.setMessage("Log in ...");
        pDLogin.show();

        final String mail = etMail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        firebaseLogin.signInWithEmailAndPassword(mail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete " + task.isSuccessful());
                //If sign in fails, display a message to the user.
                //If sign in succeeds the auth state listener will be notified and
                //logic to handle the signed in user can be handled in the listener

                if (!task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:failed", task.getException());
                    onLoginFailed();
                    passwordRequest();

                    //TODO: Intentar hacerlo con SnackBar
                }else{
                    onLoginSuccess();
                }
            }
        });

        /*
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoginSuccess();
            }
        },3000);
        */
    }

    //Method to reset the password sending an email
    //Método para resetear el password enviando un correo
    public void passwordRequest(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Do not remember your password, would you like to reset it?")
                .setTitle("Password")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "User press CANCEL button");
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String mail = etMail.getText().toString().trim();
                        firebaseLogin.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getBaseContext(), "Check your mail to reset your password",Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Email sent to " + mail);
                                }else{
                                    Log.d(TAG, "Error sending the email");
                                }
                            }
                        });
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    public boolean validate() {
        boolean valid = true;

        String mail = etMail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(mail) || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            etMail.setError("Enter a valid email address");
            valid = false;
            //the mail is empty
        }else{
            etMail.setError(null);
            pDLogin.dismiss();
        }

        if (TextUtils.isEmpty(password) || password.length() < 4 || password.length() > 10) {
            etPassword.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
            //the password is empty
        }else{
            etPassword.setError(null);
            pDLogin.dismiss();
        }
        return valid;
    }

    public void onLoginSuccess(){
        btLogin.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(MainActivity.this, "Login correct", Toast.LENGTH_SHORT).show();
        pDLogin.dismiss();
    }

    public void onLoginFailed(){
        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
        btLogin.setEnabled(true);
        pDLogin.dismiss();
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
}


