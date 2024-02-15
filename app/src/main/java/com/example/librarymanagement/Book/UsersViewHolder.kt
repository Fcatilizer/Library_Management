package com.example.librarymanagement.Book

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.R
import com.example.librarymanagement.User


class UsersViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.user_card, parent, false
        )
    ) {

    private val rollNumber = itemView.findViewById<TextView>(R.id.RollNo)
    private val username = itemView.findViewById<TextView>(R.id.Username)

    fun bind(user: User) {
        rollNumber.text = user.rollNo.toString()
        username.text = user.username.toString()

    }

}