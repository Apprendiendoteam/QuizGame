package com.applicate.quiz.quizgame;

import android.app.ProgressDialog;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.etMail) EditText etMail;
    @Bind(R.id.etPassword) EditText etPassword;
    @Bind(R.id.etPasscheck) EditText etPassCheck;
    @Bind(R.id.btRegister) Button btRegister;
    @Bind(R.id.btCancel) Button btCancel;

    /*
    private EditText etMail;
    private EditText etPassword;
    private EditText etPassCheck;
    private Button btRegister;
    private Button btCancel;
    */

    //private static final String TAG = "RegisterActivity";
    private static final int REQUEST_SIGNUP = 0;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Para la inyección de las vistas con ButterKnife
        ButterKnife.bind(this);

        /*
        etMail = (EditText) findViewById(R.id.etMail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassCheck = (EditText) findViewById(R.id.etPasscheck);
        btRegister = (Button) findViewById(R.id.btRegister);
        btCancel = (Button) findViewById(R.id.btCancel);
        */

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        //listener del Cancel Button para volver a la vista de Login
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //listener del Register Button para ejecutar la función de registro
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser(){
        String mail = etMail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String passCheck = etPassCheck.getText().toString().trim();

        if(TextUtils.isEmpty(mail)){
            //mail is empty
            Toast.makeText(this, "Please insert a valid mail", Toast.LENGTH_SHORT).show();
            //stop the function execution further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please insert a valid password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passCheck)){
            Toast.makeText(this, "Password should be the same", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user is successfully registered and logged in
                    progressDialog.cancel();
                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.cancel();
                    Toast.makeText(RegisterActivity.this, "Could not register, try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //TODO: Habría q hacer que pasará a la página principal del juego o que
        //TODO: volvierá a la del login para logearse
        //TODO: Se podría poner aquí el campo de la comprobación de la contraseña
        //TODO: sin saber si esa opción la tiene firebase
        //TODO: Se me ocurre que podría hacerse con un if
        //TODO: Si  el texto q hay en el campo 1 de contraseña == al texto del campo 2


    }
}
