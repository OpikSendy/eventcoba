package com.example.eventcoba.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventcoba.R
import com.example.eventcoba.data.model.ListEventsItem
import com.example.eventcoba.databinding.ItemEventsBinding

class EventAdapter : ListAdapter<ListEventsItem, EventAdapter.EventViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((ListEventsItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ListEventsItem) -> Unit) {
        onItemClickListener = listener
    }

//    fun setData(newEvents: List<ListEventsItem>) {
//        val events = newEvents
//        notifyDataSetChanged() // Harus dipanggil agar adapter tahu data berubah
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class EventViewHolder(private val binding: ItemEventsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    onItemClickListener?.invoke(item)
                }
            }
        }

        fun bind(event: ListEventsItem) {
            binding.apply {
                textEventName.text = event.name
                textEventDate.text = event.beginTime
                // Set other views as needed

                Glide.with(itemView)
                    .load(event.imageLogo)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(imageEvent)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ListEventsItem>() {
        override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean =
            oldItem.id == newItem.id // Pastikan ID berbeda untuk setiap item

        override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean =
            oldItem == newItem // Membandingkan konten
    }
}