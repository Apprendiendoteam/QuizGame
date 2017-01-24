package com.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sunsun on 24/1/17.
 */

public class FBConnect {

    public FBConnect(){
    }


    //Método para añadir un nuevo Usuario Json en la BBDD
    public void addNewUser(Usuario usuario){
        FirebaseDatabase FBDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = FBDatabase.getReference(QGReference.USER_REFERENCE);
        userRef.push().setValue(usuario);
    }

}
