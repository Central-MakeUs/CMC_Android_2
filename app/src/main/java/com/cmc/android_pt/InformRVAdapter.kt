package com.cmc.android_pt

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cmc.android_pt.R
import com.cmc.android_pt.databinding.ItemInformBinding
import com.cmc.android_pt.domain.notification.NotificationResult

class InformRVAdapter(private var informList: ArrayList<NotificationResult>): RecyclerView.Adapter<InformRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    var index = -1

    interface OnItemClickListener {
        fun onItemClick(data: NotificationResult, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformRVAdapter.ViewHolder {
        val binding: ItemInformBinding = ItemInformBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InformRVAdapter.ViewHolder, position: Int) {
        holder.binding.mainInformArrowIv.setOnClickListener {
            itemClickListener.onItemClick(informList[position], position)
        }
        holder.bind(informList[position], position)
    }

    override fun getItemCount(): Int = informList.size

    inner class ViewHolder(val binding: ItemInformBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: NotificationResult, position: Int) {
            val textData = notification.title
            val builder = SpannableStringBuilder(textData)
            val colorSpan = ForegroundColorSpan(ContextCompat.getColor(binding.root.context, R.color.main))
            var index = textData.indexOf("공지")
            builder.setSpan(colorSpan, 0, index + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            binding.itemInformTv.text = builder
        }
    }

    fun addItem(item: NotificationResult) {
        informList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<NotificationResult>) {
        informList.addAll(items)
        this.notifyDataSetChanged()
    }
}