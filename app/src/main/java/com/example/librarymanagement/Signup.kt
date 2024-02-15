package com.example.librarymanagement

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Signup : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val SignIn = findViewById<Button>(R.id.back)
        mAuth = FirebaseAuth.getInstance()

        SignIn.setOnClickListener {
            val i = Intent(this, Signin::class.java)
            startActivity(i)
            finish()
        }

        val Register = findViewById<Button>(R.id.register)
        val isTeacher = findViewById<CheckBox>(R.id.isTeacher)
        val rollNumber = findViewById<EditText>(R.id.RollNo)


        isTeacher.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                rollNumber.visibility = View.GONE
            } else {
                rollNumber.visibility = View.VISIBLE
            }

        }

        Register.setOnClickListener {

            val email = findViewById<EditText>(R.id.Email)
            val passw = findViewById<EditText>(R.id.password)
            val username = findViewById<EditText>(R.id.Username)

            if (isTeacher.isChecked) {
                createUser(
                    email = email.text.toString(),
                    passw = passw.text.toString(),
                    username = username.text.toString(),
                    isTeacher = isTeacher.isChecked,
                    rollNumber = null
                )
            }


            val isRollNumber = rollNumber.text.toString().trim().matches(Regex("([A-Z])+/[0-9]+/[A-Z]+/[0-9]+/[0-9]+"))

            // Log.d("test", isRollNumber.toString())
            if (isRollNumber) {
                Firebase.firestore.collection("users").whereEqualTo("rollNo", rollNumber.text.toString().trim()).get().addOnSuccessListener {
                    if (it.size() == 0) {
                       createUser(
                           email = email.text.toString(),
                           passw = passw.text.toString(),
                           username = username.text.toString(),
                           isTeacher = isTeacher.isChecked,
                           rollNumber = rollNumber.text.toString().trim()
                       )
                    }
                    else {
                        rollNumber.error = "Roll No already exists"
                    }
                }
            }


        }


    }

    fun createUser(email: String, passw: String, username: String, isTeacher: Boolean, rollNumber: String?) {
        mAuth.createUserWithEmailAndPassword(email, passw)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    Toast.makeText(
                        this, "Authentication complete.",
                        Toast.LENGTH_SHORT
                    ).show()

                    val db = Firebase.firestore
                    val user = FirebaseAuth.getInstance().currentUser


                    if (user != null) {
                        db.collection("users")
                            .document(user.uid)
                            .set(
                                User(
                                    rollNo = rollNumber,
                                    username = username,
                                    isTeacher = isTeacher
                                )
                            )
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Success",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                                startActivity(
                                   if (isTeacher) {
                                       Intent(
                                           this,
                                           AdminActivity::class.java
                                       )
                                   } else {
                                       Intent(
                                           this,
                                           MainActivity::class.java
                                       )
                                   }
                                )
                                finish()
                            }.addOnFailureListener { e ->
                                Toast.makeText(
                                    this,
                                    e.message,
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.getException())
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

}