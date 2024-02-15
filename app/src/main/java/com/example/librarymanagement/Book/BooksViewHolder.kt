package com.example.librarymanagement.Book

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.R


class BooksViewHolder( inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.book_card, parent, false
        )
    ) {

    private val isbnTextView = itemView.findViewById<TextView>(R.id.isbn)
    private val bookTitleTextView = itemView.findViewById<TextView>(R.id.bookTitle)
    private val bookAuthorTextView = itemView.findViewById<TextView>(R.id.bookAuthor)
    private val YearTextView = itemView.findViewById<TextView>(R.id.year)
    private val bookImageView = itemView.findViewById<ImageView>(R.id.bookImage)

    fun bind(book: Book) {
        isbnTextView.text = book.isbn
        bookTitleTextView.text = book.bookTitle
        bookAuthorTextView.text = book.author
        YearTextView.text = book.year.toString()

    }

}