package com.example.bookify

import BookAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerViewBooks: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookList: MutableList<Book>
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerViewBooks = findViewById(R.id.recyclerViewRecentlyAdded)
        recyclerViewBooks.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        bookList = ArrayList()
        bookAdapter = BookAdapter(this, bookList)
        recyclerViewBooks.adapter = bookAdapter

        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        // Load book images from Firebase Storage
        loadBooksFromStorage()
    }

    private fun loadBooksFromStorage() {
        // Define the image names
        val imageNames = listOf("ASparkOfLight.png", "TheLostChild.png", "TheWarehouse.png")

        for (imageName in imageNames) {
            // Create a reference to the image
            val imageRef = storageRef.child("Books/$imageName")
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val book = Book(title = imageName, author = "Unknown Author", imageUrl = uri.toString())
                bookList.add(book)
                bookAdapter.notifyDataSetChanged() // Update the RecyclerView
            }.addOnFailureListener { exception ->
                Log.e("HomeActivity", "Failed to load image: $imageName", exception)
            }
        }
    }
}

