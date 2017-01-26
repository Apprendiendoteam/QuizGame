package com.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sunsun on 24/1/17.
 */

public class FBConnect {


    FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    DatabaseReference userRef = fbdb.getReference(QGReference.USER_REFERENCE);

    public FBConnect(){
    }

    public void editUser(String userID, String newUserName){

    userRef.child(userID).setValue(newUserName);

}

    public void getUser(){

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.d("PROBANDO con child", dataSnapshot.getKey());
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userMail = user.getEmail();
                if (usuario.getUserMail().equals(userMail)){
                    Log.d("PROBANDO con child", dataSnapshot.getKey());
                    Log.d("USUARIO", usuario.getUserMail());

                    //TODO: Poner lo que se quiera q haga la función
                    //hasta ahora solo se comprueba que se leen los usuarios
                }else{
                    Log.d("Usuario", "no entro");
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
        };
        userRef.addChildEventListener(childEventListener);
    }
    public void updatePoints(final int points){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (usuario.getUserMail().equals(user.getEmail())){
                    Log.d("FBConnect", "User equals in updatePoints");
                    String key = userRef.child(QGReference.USER_REFERENCE).push().getKey();
                    Log.d("FBConnet",key);
                    /*
                    HashMap<String, Object> updateValue = usuario.toMap();
                    HashMap<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("")
                    */
                    //userRef.child(dataSnapshot.getKey()).updateChildren(points);
                }else{
                    Log.d("FBConnet","No entro en updatePoints");
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
        };
        userRef.addChildEventListener(childEventListener);

    }


    //Método para añadir un nuevo Usuario Json en la BBDD
    public void addNewUser(Usuario usuario){
        FirebaseDatabase FBDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = FBDatabase.getReference(QGReference.USER_REFERENCE);
        userRef.push().setValue(usuario);
    }



}
