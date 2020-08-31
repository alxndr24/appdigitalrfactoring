package com.example.digitalfactoring

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_facturas.*
import org.json.JSONException

class ContratosActivity : AppCompatActivity() {
    var myProgressBar: ProgressBar? = null
    var myIndicador: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contratos)
        myProgressBar = findViewById(R.id.progressBar)
        myIndicador = findViewById(R.id.indicador3)





/*        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = getDatasetFeed()?.let {
            testAdapter(this, it){
                Toast.makeText(applicationContext, "esto no funciona", Toast.LENGTH_SHORT).show()
            }
        }*/







//        getComprobante1()
        // otro tipo de recycler


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
        val url: String = "https://dev.digitalfactoring.net/Contrato?id_Empresa=1&NumeroPagina=1&NumeroRegistro=20&IdContrato=&cEstado=&IdProveedor=" + idProveedor

        // Request a string response from the provided URL.
        val stringReq = object: JsonArrayRequest(
            Request.Method.GET, url,null,
            //<JSONArray>
            Response.Listener {
                    response ->

                // Process the JSON
                try {

                    val tareas = ArrayList<CotizaYContratoObj>()

                    // Loop through the array elements
                    for (i in 0 until response.length()) {
                        // Get current json object
                        val recibo = response.getJSONObject(i)

                        // Get the current recibo (json object) data

                        val nomcomercial = recibo.getString("pNomComercial")

                      //  val estado = recibo.getString("Estado")

                        val estado: String

                        if(recibo.getString("Estado") == "B"){
                            estado = "Baja"
                        }else if (recibo.getString("Estado") == "D"){
                            estado = "Desembolso"
                        }else if (recibo.getString("Estado") == "S"){
                            estado = "Con Saldo" //Con saldo por desembolsar
                        }else if (recibo.getString("Estado") == "P"){
                            estado = "Pendiente"
                        }else{
                            estado = ""
                        }

                        val moneda : String

                        if(recibo.getString("Moneda") == "D"){
                            moneda = "Dólares"
                        }else {
                            moneda = "Soles"
                        }

                        val adelanto = recibo.getString("Adelanto_porcentaje")
                        val tasa = recibo.getString("Tasa_Mensual")
                        val fechaDesembolso = recibo.getString("Alta_Fecha")
                        val fechaVigencia = recibo.getString("Fecha_del_contrato")
                        val desembolsoTotal = recibo.getString("VNPP")
                        val remanenteTotal = recibo.getString("Remanente_importe")
                        val interes = recibo.getString("Moneda")



                        tareas.add(
                            CotizaYContratoObj( nomcomercial.toString(),
                                estado.toString(),
                                moneda.toString(),
                                adelanto.toString(),
                                tasa.toString(),
                                fechaDesembolso.toString(),
                                fechaVigencia.toString(),
                                desembolsoTotal.toString(),
                                remanenteTotal.toString(),
                                interes.toString()
                        )
                        )

                    }

                    recycler_view.adapter = CotizaYContratoAdapter(tareas)
                    recycler_view.layoutManager = LinearLayoutManager(this)
                    myProgressBar?.visibility = View.GONE
                    if (tareas.size == 0){
                        myIndicador?.visibility = View.VISIBLE
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                    myProgressBar?.visibility = View.GONE

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
