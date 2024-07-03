package com.example.fetch

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetch.data.ItemData
import java.util.TreeMap

class ItemAdapter(groupedItems: Map<Int, List<ItemData>>?) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private val groupedItems: Map<Int, List<ItemData>> =
        TreeMap(groupedItems)
    private val groupColors = intArrayOf(
        Color.parseColor("#FFCDD2"),  // Light Red
        Color.parseColor("#C8E6C9"),  // Light Green
        Color.parseColor("#BBDEFB"),  // Light Blue
        Color.parseColor("#FFECB3"),  // Light Yellow
        Color.parseColor("#D1C4E9") // Light Purple
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var position = position
        var groupPosition = 0
        for ((_, value) in groupedItems) {
            if (position >= value.size) {
                position -= value.size
            } else {
                val item = value[position]
                holder.bind(item, groupColors[groupPosition % groupColors.size])
                break
            }
            groupPosition++
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        for (itemList in groupedItems.values) {
            count += itemList.size
        }
        return count
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemIdTextView: TextView = itemView.findViewById(R.id.item_id)
        private val listIdTextView: TextView = itemView.findViewById(R.id.list_id)
        private val nameTextView: TextView = itemView.findViewById(R.id.name)

        fun bind(item: ItemData, backgroundColor: Int) {
            itemView.setBackgroundColor(backgroundColor)
            itemIdTextView.text = item.itemId.toString()
            listIdTextView.text = item.listId.toString()
            nameTextView.text = item.name
        }
    }
}