package com.example.librarymanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.librarymanagement.Book.Book
import com.example.librarymanagement.Book.BooksRecyclerAdapter
import com.example.librarymanagement.databinding.FragmentFirstBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid.toString())
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                val bookHistory = user?.bookHistory

                if (user != null && bookHistory!!.isNotEmpty()) {
                    Firebase.firestore.collection("books").whereIn(
                        FieldPath.documentId(),
                        bookHistory!!
                    ).get().addOnSuccessListener { value ->
                        val bookList = value.toObjects(Book::class.java)

                        binding.booksRecyclerView.adapter = BooksRecyclerAdapter(bookList)
                        binding.booksRecyclerView.layoutManager = GridLayoutManager(
                            context,
                            2
                        )


                    }


                }
            }

        /* binding.buttonFirst.setOnClickListener {
             findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
         }
    */
    }
}