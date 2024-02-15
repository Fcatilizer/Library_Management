package com.example.librarymanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.example.librarymanagement.Book.Book
import com.example.librarymanagement.Book.BooksRecyclerAdapter
import com.example.librarymanagement.Book.UserBooksRecyclerAdapter
import com.example.librarymanagement.databinding.FragmentSecondBinding
import com.example.librarymanagement.databinding.FragmentUserAdminBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFragment : Fragment() {

    private var _binding: FragmentUserAdminBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* binding.buttonSecond.setOnClickListener {
             findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
         }*/

        binding.search.doAfterTextChanged {


            Firebase.firestore.collection("users")
                .whereGreaterThanOrEqualTo("rollNo", binding.search.text.toString())
                .get()
                .addOnSuccessListener { value ->

                    if (value != null) {
                        val bookList = value.toObjects(User::class.java)
                        val isbn = arguments?.getString("bookIsbn")
                        if (isbn != null) {

                            binding.booksRecyclerView.apply {
                                adapter = UserBooksRecyclerAdapter(context, bookList, true, isbn)
                                layoutManager = GridLayoutManager(
                                    context,
                                    2
                                )
                            }
                        }
                    }

                }


        }

        Firebase.firestore.collection("users")
            .whereGreaterThanOrEqualTo("rollNo", binding.search.text.toString()).get()
            .addOnSuccessListener { value ->

                if (value != null) {
                    val bookList = value.toObjects(User::class.java)
                    val isbn = arguments?.getString("bookIsbn")
                    if (isbn != null) {

                        binding.booksRecyclerView.apply {
                            adapter = UserBooksRecyclerAdapter(context, bookList, true, isbn)
                            layoutManager = GridLayoutManager(
                                context,
                                2
                            )
                        }
                    }
                }

            }


    }

  

}