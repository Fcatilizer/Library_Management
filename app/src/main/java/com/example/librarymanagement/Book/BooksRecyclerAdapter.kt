package com.example.librarymanagement.Book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.R

class BooksRecyclerAdapter(
    private val list: List<Book>,
    var isAdmin:Boolean = false
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BooksViewHolder( inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val booksRecyclerAdapter: BooksViewHolder = holder as BooksViewHolder
        val book = list[position]


        booksRecyclerAdapter.bind(book)
        if (isAdmin){
            booksRecyclerAdapter.itemView.findViewById<CardView>(R.id.cardBOOK).setOnClickListener {
                val bundle = bundleOf("bookIsbn" to book.isbn)
                Navigation.findNavController(it).navigate(
                    R.id.action_addAssignFragment_to_userFragment,
                    bundle
                )
            }
        }


    }


    override fun getItemCount(): Int = list.size
}