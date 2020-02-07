package com.neoproduction.hssofttest2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neoproduction.hssofttest2.data.Country

class AdapterCountries(val countries: List<Country>) :
    RecyclerView.Adapter<AdapterCountries.ContinentViewHolder>() {
    class ContinentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvCountryName)
        val tvCode: TextView = view.findViewById(R.id.tvCode)
        //        val tvEmoji: TextView = view.findViewById(R.id.tvEmoji)
        val tvCurrency: TextView = view.findViewById(R.id.tvCurrency)
        val tvPhone: TextView = view.findViewById(R.id.tvPhone)
        val tvLanguages: TextView = view.findViewById(R.id.tvLanguages)
    }

    override fun getItemViewType(position: Int): Int = position % 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContinentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_country, parent, false)
        view.setBackgroundColor(
            if (viewType == 0)
                Color.rgb(0xff, 0xff, 0xff)
            else
                Color.rgb(0xee, 0xee, 0xee)
        )
        return ContinentViewHolder(view)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: ContinentViewHolder, position: Int) {
        val country = countries[position]
        holder.tvName.text = "${country.name} ${country.emoji}"
        holder.tvCode.text = "code: \"${country.code}\""
//        holder.tvEmoji.text = countries[position].emoji
        holder.tvCurrency.text = "currency: ${country.currency}"
        holder.tvPhone.text = "phone: +${country.phone}"
        if (country.languagesNames.isNotEmpty())
            holder.tvLanguages.text = "• " + country.languagesNames.joinToString("\n• ")
        else
            holder.tvLanguages.text = "NO TIME FOR IDLE TALK IN ANTARCTICA"
    }
}