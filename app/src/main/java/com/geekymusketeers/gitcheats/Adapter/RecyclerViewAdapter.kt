package com.geekymusketeers.gitcheats.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.gitcheats.Click
import com.geekymusketeers.gitcheats.Model.SecondaryModel
import com.geekymusketeers.gitcheats.R

class RecyclerViewAdapter(
val context: Context,
val list: List<SecondaryModel>,
val clickInterface: Click
)  : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(
                R.layout.list,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int =  list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemTitle.text = list[position].usage
        holder.itemContent.text = list[position].label.capitalize()

        holder.itemView.setOnClickListener {
            clickInterface.clickListener(position)
        }

    }
}

class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
    val itemContent: TextView = itemView.findViewById(R.id.itemContent)

}