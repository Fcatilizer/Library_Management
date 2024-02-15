package com.example.librarymanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.example.librarymanagement.Book.BooksRecyclerAdapter
import com.example.librarymanagement.Book.Book
import com.example.librarymanagement.databinding.FragmentSecondBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/

        binding.search.doAfterTextChanged {


            Firebase.firestore.collection("books")
                .whereGreaterThanOrEqualTo("bookTitle", binding.search.text.toString()).get()
                .addOnSuccessListener { value ->

                    if (value != null ) {

                        val bookList =  value.toObjects(Book::class.java).filter {
                            it.bookIssued == false
                        }
                        binding.booksRecyclerView.apply {
                            adapter = BooksRecyclerAdapter(bookList)
                            layoutManager = GridLayoutManager(
                                context,
                                2
                            )
                        }
                    }

                }


        }

        Firebase.firestore.collection("books").whereNotEqualTo("bookIssued", true).addSnapshotListener { value, error ->
            if (value != null ) {
                val bookList =  value.toObjects(Book::class.java)
                binding.booksRecyclerView.apply {
                    adapter = BooksRecyclerAdapter(bookList)
                    layoutManager = GridLayoutManager(
                        context,
                        2
                    )
                }
            }
        }

    }

  
}