package com.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by sunsun on 24/1/17.
 */

public class FBConnect {


    FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    DatabaseReference userRef = fbdb.getReference(QGReference.USER_REFERENCE);

    private final static String TAG = "FBCONNECT";

    public FBConnect(){
    }

    public void editUser(String userID, String newUserName){

    userRef.child(userID).setValue(newUserName);
        //TODO: No se si funciona bien
        //TODO: Hay que probarlo
        //TODO: Xq requiere tener la key del usuario

}

    //Método para obtener el usuario que esta jugando en ese momento

    public ArrayList getUser(){

        //He intentado hacer este metodo como public Usuario getUSer(){}
        //para que devolviera un usuario, xo es imposible hacer que el usuario que devuelve
        //no me pida q lo convierta en array
        //o bien si intento crear un usuarioADevolver y luego lo iguale cogiendo
        //los valores del usuario en el if del dataSanpshot no me diga luego
        //que el usuario a devolver es Null
        //así q no se como narices hacer para devolver el usuario q esta registrado en ese momento
        //xq para hacer el método updatePoint o actulizar cualq campo del usuario
        //x lo q entiendo de firebase tienes que pasarle todos los datos para q los sobreescriba
        //y si poniamos el childEventListener en el updatePoints
        //entraba en un bucle sin fin y además no actualizaba datos
        //lo que hacía era incluir cada vez un nuevo usuario dentro de una rama nueva de /users/


        final ArrayList<Usuario> userArray = new ArrayList();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.d(TAG, "HA ENTRADO EN EL ONCHILDADDED");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userMail = user.getEmail();
                if (dataSnapshot.getValue(Usuario.class).getUserMail().equals(userMail)){
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    Log.d("USUARIOOOOOO", usuario.getUserMail());
                    userArray.add(usuario);
                }else{
                    Log.d("PROBANDO DE NUEVO","No ENTRO EN EL If");
                }
                //Esto funciona hasta escribir el usuario, pero luego no hay forma
                //de sacar el usuario del bucle if sin un array

                Log.d(TAG,userArray.toString());
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
        Log.d(TAG, "SIZE:" + userArray.size());
        //En consola devuelve antes esta sentencia que la del log.con el array.toString()
        return userArray;
    }




    public void updatePoints(final int points){

        String key = userRef.child(QGReference.USER_REFERENCE).push().getKey();

        /*
        //He comentado esto para intentar hacerlo sin el childEventListener
        //pero hace falta el usuario
        //x lo q no se como hacerlo :(
        //xq estaba pensando q si tal vez tienes la key del usuario
        //en lugar de acceder la rama padre, se puede ir directamente al usuario
        //y con la rama points actualizarla pero.. sq el updateChild
        //te pide un HashMap

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (usuario != null) {
                    if (usuario.getUserMail().equals(user.getEmail())) {

                        Log.d(TAG, "User equals in updatePoints");

                        String key = userRef.child(QGReference.USER_REFERENCE).push().getKey();

                        Log.d(TAG, "KEY: " + key);

                        usuario.points = 8;

                        HashMap<String, Object> updateValue = usuario.toMap();

                        HashMap<String, Object> childUpdates = new HashMap<>();

                        childUpdates.put("/users/" + key, updateValue);


                        Log.d("A MANDAR A UPDATEAR:", "HashMap: " + childUpdates);

                        userRef.updateChildren(childUpdates);
                        //userRef.child(dataSnapshot.getKey()).updateChildren(points);

                    } else {

                        Log.d(TAG, "Los usuarios no coinciden");

                    }
                } else {
                    Log.d(TAG, "USUARIO NO ENCONTRADO");

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
        */


    }


    //Método para añadir un nuevo Usuario Json en la BBDD
    //Este método se llama en el registro (RegisterView)
    public void addNewUser(Usuario usuario){
        FirebaseDatabase FBDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = FBDatabase.getReference(QGReference.USER_REFERENCE);
        userRef.push().setValue(usuario);
    }



}
