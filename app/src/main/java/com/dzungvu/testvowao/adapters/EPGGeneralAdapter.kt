package com.dzungvu.testvowao.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dzungvu.testvowao.R
import com.dzungvu.testvowao.models.EPGGeneral


class EPGGeneralAdapter(private val data: List<EPGGeneral>) :
    RecyclerView.Adapter<EPGGeneralAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_epg_general, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvContent.text = position.toString()
        holder.itemView.alpha = 1F
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvContent: TextView = itemView.findViewById(R.id.tvContent)
        var clContainer: ConstraintLayout = itemView.findViewById(R.id.clContainer)
    }
}