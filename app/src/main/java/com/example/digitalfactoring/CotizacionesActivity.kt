package com.example.digitalfactoring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_facturas.*
import org.json.JSONException

class CotizacionesActivity : AppCompatActivity() {//
    var myProgressBar: ProgressBar? = null
    var myIndicador: TextView? = null

/*    companion object{
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }*/



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizaciones)
        myProgressBar = findViewById(R.id.progressBar)
        myIndicador = findViewById(R.id.indicador2)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //val Usuario = intent.extras!!["objUsuario"]

        /*
        getComprobante1()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = testAdapter(this,objList){

        }*/

        val numdocumento=intent.getStringExtra("numdocumento")
        getComprobante(numdocumento)


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

    fun getComprobante(numdocumento : String) {
        myProgressBar?.visibility = View.VISIBLE

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://dev.digitalfactoring.net/api-operacion/ObtenerOperacion?NumeroPagina=1&NumeroRegistro=20&id_Empresa=1&Num_Doc_Identificacion_ADQ=&Num_Doc_Identificacion_Prov="+ numdocumento +"&Estado=&cDesdeOp=2019-10-01&cHastaOp=2020-04-30"

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
/*                        val numfactura =
                            recibo.getString("Tipo_Comprobante") + " " + recibo.getString("Tipo_Emision") + ": " +
                                    recibo.getString("Serie") + "-" + recibo.getString("Numero")*/

                        val nomcomercial = recibo.getString("NombreComercialProv")

//                        val monto = recibo.getString("VNPP") + " " + recibo.getString("Moneda")
                        //val estado = recibo.getString("Estado")

                        val estado: String

                        if(recibo.getString("Estado") == "R"){
                            estado = "Registrada"
                        }else if (recibo.getString("Estado") == "A"){
                            estado = "Aceptada"
                        }else if (recibo.getString("Estado") == "C"){
                            estado = "Con Contrato" //Con saldo por desembolsar
                        }else if (recibo.getString("Estado") == "Z"){
                            estado = "Rechazada"
                        }else if (recibo.getString("Estado") == "V"){
                            estado = "Vencida" //Con saldo por desembolsar
                        }else if (recibo.getString("Estado") == "B"){
                            estado = "Baja"
                        }else{
                            estado = ""
                        }


//                        val fecha = recibo.getString("Fecha_Emision")


                        val moneda : String

                        if(recibo.getString("TipoMoneda") == "D"){
                            moneda = "Dólares"
                        }else {
                            moneda = "Soles"
                        }

                        val adelanto = recibo.getString("VNPP")
                        val tasa = recibo.getString("Cantidad_de_Documentos_Negociables")
                        val fechaDesembolso = recibo.getString("Registro")
                        val fechaVigencia = recibo.getString("Fecha_Vigencia")
                        val desembolsoTotal = recibo.getString("VNPP")
                        val remanenteTotal = recibo.getString("VNPP")
                        val interes = recibo.getString("Tasa_Mensual")

                        tareas.add(CotizaYContratoObj( nomcomercial,
                            estado,
                            moneda,
                            adelanto,
                            tasa,
                            fechaDesembolso,
                            fechaVigencia,
                            desembolsoTotal,
                            remanenteTotal,
                            interes
                        )) // Modificar objeto para agregar nuevos atributos

                    }

                    recycler_view.adapter = CotizaYContratoAdapter(tareas)//
                    recycler_view.layoutManager = LinearLayoutManager(this)
                    myProgressBar?.visibility = View.GONE
                    if (tareas.size == 0){
                        myIndicador?.visibility = View.VISIBLE
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                    myProgressBar?.visibility = View.GONE

                    // Log.d("error", e.printStackTrace().toString())
                }

            },
            Response.ErrorListener {
                    error -> Log.d("error", error.toString())
                myProgressBar?.visibility = View.GONE

            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "9b8a5d6199d120d8"
                headers["Content-Type"] = "application/json"
                return headers
            }

        }
        queue.add(stringReq)
    }

//    override fun onItemClick(item: CotizaYContratoObj, position: Int) {//
//        Toast.makeText(this,"ra", Toast.LENGTH_SHORT).show()
    }



