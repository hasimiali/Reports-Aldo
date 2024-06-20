package com.example.reports_aldo.api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reports_aldo.databinding.ItemReportBinding
import com.example.reports_aldo.models.Report
import com.squareup.picasso.Picasso

class ReportAdapter : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    private var reports: List<Report> = emptyList()

    fun submitList(reports: List<Report>) {
        this.reports = reports
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(reports[position])
    }

    override fun getItemCount(): Int = reports.size

    class ReportViewHolder(private val binding: ItemReportBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(report: Report) {
            binding.textViewDescription.text = report.description
            binding.textViewUrgency.text = report.urgency
            binding.textViewStatus.text = report.status  // New field displayed
            Picasso.get().load(report.imageUrl).into(binding.imageViewReport)
        }
    }
}

