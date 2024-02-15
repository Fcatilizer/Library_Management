package com.example.librarymanagement.Book

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.R
import com.example.librarymanagement.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserBooksRecyclerAdapter(
    private val context: Context,
    private val list: List<User>,
    var isAdmin: Boolean = false,
    var bookIsbn: String? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UsersViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val usersViewHolder: UsersViewHolder = holder as UsersViewHolder
        val user = list[position]


        usersViewHolder.bind(user)
        if (isAdmin) {
            usersViewHolder.itemView.findViewById<CardView>(R.id.userCard).setOnClickListener {
                Firebase.firestore.collection("books").whereEqualTo("isbn", bookIsbn)
                    .get().addOnSuccessListener { it ->
                        val bookID = it.documents.get(0).id
                        Firebase.firestore.collection("books").document(bookID).update(
                            "bookIssued", true
                        ).addOnSuccessListener {
                            Firebase.firestore.collection("users")
                                .whereEqualTo("rollNo", user.rollNo).get().addOnSuccessListener {
                                if (it != null) {
                                    val bookList = user.bookHistory.toMutableList()
                                    bookList.add(bookID)
                                    Firebase.firestore.collection("users")
                                        .document(it.documents.get(0).id).update(
                                            "bookHistory", bookList
                                        )
                                        .addOnSuccessListener {
                                            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                }
                            }
                        }
                    }
            }
        }


    }


    override fun getItemCount(): Int = list.size
}