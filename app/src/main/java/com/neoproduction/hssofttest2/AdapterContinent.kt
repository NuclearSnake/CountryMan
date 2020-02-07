package com.neoproduction.hssofttest2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neoproduction.hssofttest2.data.Continent

class AdapterContinent(val continents: List<Continent>, val onClickItem: (Int) -> Unit) :
    RecyclerView.Adapter<AdapterContinent.ContinentViewHolder>() {
    class ContinentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvContinentName)
        val flBackContinent: FrameLayout = view.findViewById(R.id.flBackContinent)
    }

    var selected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContinentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_continent, parent, false)
        return ContinentViewHolder(view)
    }

    override fun getItemCount(): Int = continents.size

    override fun onBindViewHolder(holder: ContinentViewHolder, position: Int) {
        holder.tvName.text = continents[position].name
        holder.flBackContinent.setOnClickListener {
            if(changeSelected(position))
                onClickItem(position)
        }
        if (position == selected)
            holder.flBackContinent.setBackgroundColor(Color.rgb(0xdd, 0xdd, 0xdd))
        else
            holder.flBackContinent.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff))
    }

    /**
     * changes currently selected continent
     * returns false if the selected element is the same as previous time
     */
    private fun changeSelected(newPosition: Int): Boolean {
        if(selected == newPosition)
            return false

        if (selected >= 0) {
            val prev = selected
            selected = -1
            notifyItemChanged(prev)
        }
        selected = newPosition
        notifyItemChanged(selected)
        return true
    }
}