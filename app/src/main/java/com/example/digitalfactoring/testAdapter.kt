package com.example.digitalfactoring

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class testAdapter(private val context: Context,
                  private val images: List<CotizaYContratoObj>,
                  val listener: (CotizaYContratoObj) -> Unit) : RecyclerView.Adapter<testAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val cardView = view.findViewById<CardView>(R.id.cardView)
        val expandableView = view.findViewById<RelativeLayout>(R.id.expandableView)


        fun bindView(image: CotizaYContratoObj, listener: (CotizaYContratoObj) -> Unit) {

            itemView.setOnClickListener {
                listener(image)
                Toast.makeText(itemView.context, "Esto va dentro", Toast.LENGTH_SHORT).show()
            expandableView.visibility = View.VISIBLE}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder = ImageViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_cotiza_contrato, parent, false))

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(images[position],listener)
    }
}