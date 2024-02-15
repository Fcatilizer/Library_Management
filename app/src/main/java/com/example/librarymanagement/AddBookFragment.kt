package com.example.librarymanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.librarymanagement.Book.Book
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddBookFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookName = view.findViewById<EditText>(R.id.addName)
        val bookAuthor = view.findViewById<EditText>(R.id.addAuthor)
        val bookYear = view.findViewById<EditText>(R.id.addYear)
        val bookIsbn = view.findViewById<EditText>(R.id.addIsbn)
        val addBook = view.findViewById<Button>(R.id.Register)



        addBook.setOnClickListener {

            try {

                val book = Book(
                    isbn = bookIsbn.text.toString().trim(),
                    year = bookYear.text.toString().trim().toInt(),
                    author = bookAuthor.text.toString().trim(),
                    bookTitle = bookName.text.toString().trim()
                )

                Firebase.firestore.collection("books").document().set(
                    book
                ).addOnSuccessListener {
                    Toast.makeText(context, "Book Added Successfully", Toast.LENGTH_SHORT).show()
                    bookAuthor.setText("")
                    bookIsbn.setText("")
                    bookName.setText("")
                    bookYear.setText("")
                }.addOnFailureListener {
                    Toast.makeText(context, "Unable to Add book", Toast.LENGTH_SHORT).show()
                }

            } catch (ex: NumberFormatException) {
                Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show()
            }



        }
    }
}