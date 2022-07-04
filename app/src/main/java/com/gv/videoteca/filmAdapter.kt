package com.gv.videoteca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class filmAdapter(private val filmList : ArrayList<Film>) :
    RecyclerView.Adapter<filmAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener=clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): filmAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.film_list_item, parent, false)
        return ViewHolder(itemView, mListener)

    }


    override fun onBindViewHolder(holder: filmAdapter.ViewHolder, position: Int) {
        val currentFilm = filmList[position]
        holder.tvFilm.text=currentFilm.name
    }

    class ViewHolder(itemView : View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvFilm : TextView= itemView.findViewById(R.id.tvFilmName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return filmList.size
    }
}