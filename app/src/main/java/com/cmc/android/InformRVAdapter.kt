package com.cmc.android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmc.android.databinding.ItemAttendanceBinding
import com.cmc.android.databinding.ItemInformBinding

class InformRVAdapter(private var informList: ArrayList<String>): RecyclerView.Adapter<InformRVAdapter.ViewHolder>() {

    var index = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformRVAdapter.ViewHolder {
        val binding: ItemInformBinding = ItemInformBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InformRVAdapter.ViewHolder, position: Int) {
        holder.bind(informList[position], position)
    }

    override fun getItemCount(): Int = informList.size

    inner class ViewHolder(val binding: ItemInformBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(informText: String, position: Int) {
            index = position
            binding.itemInformTv.text = informText
        }
    }
}