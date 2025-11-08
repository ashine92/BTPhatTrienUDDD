package com.example.bt3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bt3.adapter.ImageAdapter
import com.example.bt3.model.ImageItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var fabRefresh: FloatingActionButton

    private val sampleImages = listOf(
        ImageItem(1, "https://picsum.photos/400/300?random=1", "Ảnh phong cảnh 1"),
        ImageItem(2, "https://picsum.photos/400/300?random=2", "Ảnh phong cảnh 2"),
        ImageItem(3, "https://picsum.photos/400/300?random=3", "Ảnh phong cảnh 3"),
        ImageItem(4, "https://picsum.photos/400/300?random=4", "Ảnh phong cảnh 4"),
        ImageItem(5, "https://picsum.photos/400/300?random=5", "Ảnh phong cảnh 5"),
        ImageItem(6, "https://picsum.photos/400/300?random=6", "Ảnh phong cảnh 6"),
        ImageItem(7, "https://picsum.photos/400/300?random=7", "Ảnh phong cảnh 7"),
        ImageItem(8, "https://picsum.photos/400/300?random=8", "Ảnh phong cảnh 8"),
        ImageItem(9, "https://picsum.photos/400/300?random=9", "Ảnh phong cảnh 9"),
        ImageItem(10, "https://picsum.photos/400/300?random=10", "Ảnh phong cảnh 10")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupFab()
        loadImages()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        imageAdapter = ImageAdapter(mutableListOf())

        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = imageAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupFab() {
        fabRefresh = findViewById(R.id.fabRefresh)
        fabRefresh.setOnClickListener {
            loadImages()
        }
    }

    private fun loadImages() {
        // Reset trạng thái và load lại
        val freshImages = sampleImages.map { it.copy() }
        imageAdapter.updateImages(freshImages)
    }
}