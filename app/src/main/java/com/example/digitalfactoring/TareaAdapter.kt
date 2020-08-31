package com.example.digitalfactoring

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*

class TareaAdapter(private val tareas : ArrayList<Tarea> = ArrayList()): RecyclerView.Adapter<TareaAdapter.TareaViewHolder>()   {

    class TareaViewHolder(view : View) : RecyclerView.ViewHolder(view){
/*        var cliente = itemView.cliente

        fun initialize (item: MenuItem,action:onCarClickListener){
            itemView.setOnClickListener {
                action.onItemClick(item,adapterPosition)
            }
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
       val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.row, parent, false)

        var estado: TextView?
        estado = view.findViewById(R.id.estado)

        var cardView: CardView?
        cardView = view.findViewById(R.id.cardView)

        var expandableView: RelativeLayout?
        expandableView = view.findViewById(R.id.expandableView)

        val expandir = view.findViewById<Button>(R.id.arrowBtn)
        expandir.setOnClickListener {
            if (expandableView.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                expandableView.visibility = View.VISIBLE
                expandir.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp)

            } else {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                expandableView.visibility = View.GONE
                expandir.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp)

            }
        }

        return TareaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tareas.size
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {


        holder.itemView.cliente.text = tareas[position].cliente // el primero refiere al xml
        holder.itemView.numfactura.text = tareas[position].numFactura // el ultimo es el nombre del objeto
        holder.itemView.monto.text = tareas[position].monto
        holder.itemView.estado.text = tareas[position].estado
        holder.itemView.fechaemision.text = tareas[position].fecha

        holder.itemView.serie.text = tareas[position].serie
        holder.itemView.numero.text = tareas[position].numero
        holder.itemView.fechapago.text = tareas[position].fechapago
        holder.itemView.moneda.text = tareas[position].moneda



        // Agregar y setear los otros valores, de la API
    }

/*    interface onCarClickListener{
        fun onItemClick(item: MenuItem,position: Int)
    }*/

}
