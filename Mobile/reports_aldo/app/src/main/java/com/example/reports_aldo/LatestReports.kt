package com.example.reports_aldo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reports_aldo.api.ReportAdapter
import com.example.reports_aldo.databinding.ActivityLatestReportsBinding
import com.example.reports_aldo.models.Report
import com.google.firebase.firestore.FirebaseFirestore

class LatestReports : AppCompatActivity() {

    private lateinit var binding: ActivityLatestReportsBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ReportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLatestReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        binding.addReports.setOnClickListener {
            val intent = Intent(this, Reports::class.java)
            startActivity(intent)
        }

        binding.refresh.setOnClickListener {
            fetchReports()
        }

        setupRecyclerView()
        fetchReports()
    }

    private fun setupRecyclerView() {
        adapter = ReportAdapter()
        binding.recyclerViewReports.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewReports.adapter = adapter
    }

    private fun fetchReports() {
        firestore.collection("images")
            .get()
            .addOnSuccessListener { documents ->
                val reports = documents.map { document ->
                    Report(
                        imageUrl = document.getString("imageUrl") ?: "",
                        description = document.getString("description") ?: "",
                        urgency = document.getString("urgency") ?: "",
                        timestamp = document.getLong("timestamp") ?: 0L,
                        status = document.getString("status") ?: ""  // Fetch status from Firestore
                    )
                }
                adapter.submitList(reports)
            }
            .addOnFailureListener {
                // Handle the error
            }
    }
}
