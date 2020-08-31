package com.example.digitalfactoring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_facturas.*
import org.json.JSONException


class FacturasActivity : AppCompatActivity() {

    var myProgressBar: ProgressBar? = null
    var myIndicador: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facturas)
        myProgressBar = findViewById(R.id.progressBar)
        myIndicador = findViewById(R.id.indicador1)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val idProveedor=intent.getStringExtra("idProveedor")
        getComprobante(idProveedor)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home ->{
                onBackPressed()
                true
            }
           else->super.onOptionsItemSelected(item)
        }

    }

    fun getComprobante(idProveedor : String) {


        myProgressBar?.visibility = View.VISIBLE

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://api.digitalfactoring.net/comprobante?id_Empresa=1&NumeroPagina=1&NumeroRegistro=10&NumeroCp=&ExisteAdjunto=&Tipo_Emision=&Tipo_Comprobante=&idProveedor="+ idProveedor +"&Estado="

        // Request a string response from the provided URL.
        val stringReq = object: JsonArrayRequest(
            Request.Method.GET, url,null,
            //<JSONArray>
            Response.Listener {
                    response ->

                // Process the JSON
                try {

                    val tareas = ArrayList<Tarea>()

                    // Loop through the array elements
                    for (i in 0 until response.length()) {
                        // Get current json object
                        val recibo = response.getJSONObject(i)

                        // Get the current recibo (json object) data
                        val numfactura =
                                recibo.getString("Tipo_Comprobante") + " " + recibo.getString("Tipo_Emision") + ": " +
                                recibo.getString("Serie") + "-" + recibo.getString("Numero")

                        val nomcomercial = recibo.getString("NombreComercial")

                        val monto = recibo.getString("VNPP") + " " + recibo.getString("Moneda")
                        val estado = recibo.getString("Estado")
                        val fecha = recibo.getString("Fecha_Emision")

                        val serie = recibo.getString("Serie")
                        val numero = recibo.getString("Numero")
                        val fecha_pago = recibo.getString("Fecha_de_Pago")
                        val moneda = recibo.getString("Moneda")

                         tareas.add(Tarea( nomcomercial.toString(),
                             numfactura.toString(),
                             monto.toString(),
                             estado.toString(),
                             fecha.toString(),serie,numero,fecha_pago,moneda
                            )) // Modificar objeto para agregar nuevos atributos

                    }

                    recycler_view.adapter = TareaAdapter(tareas)
                    recycler_view.layoutManager = LinearLayoutManager(this)
                    myProgressBar?.visibility = View.GONE
                    if (tareas.size == 0){
                        myIndicador?.visibility = View.VISIBLE
                    }


                } catch (e: JSONException) {
                    myProgressBar?.visibility = View.GONE

                    e.printStackTrace()
                }

            },
            Response.ErrorListener {

                    error -> Log.d("error", error.toString())
                myProgressBar?.visibility = View.GONE

            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "863EOgvVNFYgvVzwEWyvsi6qqX11GNtJN3Mf7kwt"
                headers["Content-Type"] = "application/json"
                return headers
            }

        }
        queue.add(stringReq)
    }
}
