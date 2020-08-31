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
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cotiza_contrato.view.*
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.android.synthetic.main.row.view.moneda

class CotizaYContratoAdapter(private val tareas : ArrayList<CotizaYContratoObj> = ArrayList()): RecyclerView.Adapter<CotizaYContratoAdapter.TareaViewHolder>()  {

    class TareaViewHolder(view : View) : RecyclerView.ViewHolder(view){
//        var cliente = itemView.cliente

/*        fun initialize (item: CotizaYContratoObj,action: CotizaYContratoAdapter.onCarClickListener){
            itemView.setOnClickListener {
                action.onItemClick(item,adapterPosition)
                var verPdf: Button?
                verPdf = itemView.findViewById(R.id.btnVerPdf)

                var estado: TextView?
                estado = itemView.findViewById(R.id.estado1)

                var cardView: CardView?
                cardView = itemView.findViewById(R.id.cardView)

                var expandableView: RelativeLayout?
                expandableView = itemView.findViewById(R.id.expandableView)

                verPdf.setOnClickListener {
                    Toast.makeText(itemView.context, "Se visualizara el PDF", Toast.LENGTH_SHORT).show()

                }
                expandableView.visibility = View.VISIBLE

                Toast.makeText(itemView.context, item.cliente, Toast.LENGTH_SHORT).show()






*//*                val expandir = itemView.findViewById<Button>(R.id.arrowBtn)
                expandir.setOnClickListener {
                    Toast.makeText(itemView.context,"btn clicked", Toast.LENGTH_SHORT).show()

                    if (expandableView.visibility == View.GONE) {

                        TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                        expandableView.visibility = View.VISIBLE
                        expandir.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp)

                    } else if (expandableView.visibility == View.VISIBLE){
                        TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                        expandableView.visibility = View.GONE
                        expandir.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
                    }
                }*//*



            }
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
       val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.item_cotiza_contrato, parent, false)
        var verPdf: Button?
        verPdf = view.findViewById(R.id.btnVerPdf)

        var estado: TextView?
        estado = view.findViewById(R.id.estado1)

        var cardView: CardView?
        cardView = view.findViewById(R.id.cardView)

        var expandableView: RelativeLayout?
        expandableView = view.findViewById(R.id.expandableView)

        verPdf.setOnClickListener {
            Toast.makeText(view.context, "Se visualizara el PDF", Toast.LENGTH_SHORT).show()

        }

        val expandir = view.findViewById<Button>(R.id.arrowBtn)
        expandir.setOnClickListener {
            if (expandableView.visibility == View.GONE) {

                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                expandableView.visibility = View.VISIBLE
                expandir.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp)

            } else if (expandableView.visibility == View.VISIBLE){
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
/*
        holder.initialize(tareas.get(position),clickListener)
        holder.itemView.setOnClickListener {
            var expandableView: RelativeLayout?
            expandableView = holder.itemView.findViewById(R.id.expandableView)

            expandableView.visibility = View.VISIBLE

            Toast.makeText(holder.itemView.context, "bindViewHolder", Toast.LENGTH_SHORT).show()
        }*/


        holder.itemView.cliente1.text = tareas[position].cliente // el primero refiere al xml
        holder.itemView.estado1.text = tareas[position].estado

        holder.itemView.moneda.text = tareas[position].moneda // el primero refiere al xml
        holder.itemView.adelanto.text = tareas[position].adelanto // el ultimo es el nombre del objeto
        holder.itemView.tasa.text = tareas[position].tasa
        holder.itemView.fechadesembolso.text = tareas[position].fechaDesembolso
        holder.itemView.fechavigencia.text = tareas[position].fechaVigencia

        holder.itemView.desembolsototal.text = tareas[position].desembolsoTotal
        holder.itemView.remanentetotal.text = tareas[position].remanenteTotal
        holder.itemView.interes.text = tareas[position].interes


    }

    interface onCarClickListener{
        fun onItemClick(item: CotizaYContratoObj, position: Int)
    }

}
