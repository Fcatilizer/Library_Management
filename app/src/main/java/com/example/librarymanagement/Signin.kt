package com.example.librarymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Signin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        auth = Firebase.auth

        val signInBtn = findViewById<Button>(R.id.signIn)
        val signUpBtn = findViewById<Button>(R.id.back)


        signInBtn.setOnClickListener {

            val email = findViewById<EditText>(R.id.Email).text.toString()
            val passw = findViewById<EditText>(R.id.password).text.toString()

            auth.signInWithEmailAndPassword(email, passw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success")

                        val currentUser = auth.currentUser
                        if(currentUser != null){

                            Firebase.firestore.collection("users").document(currentUser.uid).get().addOnSuccessListener {
                                if (it.get("teacher") == true) {
                                    startActivity(Intent(this, AdminActivity::class.java))
                                    finish()
                                } else {
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }
                            }

                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }

        signUpBtn.setOnClickListener {
            val i = Intent(this, Signup::class.java)
            startActivity(i)
            finish()
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){

            Firebase.firestore.collection("users").document(currentUser.uid).get().addOnSuccessListener {
                if (it.get("teacher") == true) {
                    startActivity(Intent(this, AdminActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }

        }
    }
}