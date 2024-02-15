package com.example.librarymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signIn = findViewById<Button>(R.id.signin)
        val signUp = findViewById<Button>(R.id.signup)

        signIn.setOnClickListener {
            val i = Intent(this, Signin::class.java)
            startActivity(i)
            finish()
        }

        signUp.setOnClickListener {
            val i = Intent(this, Signup::class.java)
            startActivity(i)
            finish()
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = Firebase.auth.currentUser
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