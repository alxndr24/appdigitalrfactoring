package com.example.digitalfactoring

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.digitalfactoring.custom.DayAxisValueFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_facturas.*
import org.json.JSONException

class DashBoardActivity : AppCompatActivity(), OnChartValueSelectedListener {

    private var chart: LineChart? = null
    private var soles: TextView? = null
    private var dolares: TextView? = null

    private var cantRegSoles: TextView? = null
    private var cantRegDolares: TextView? = null

    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        getDashBoard()

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)


        setSupportActionBar(toolbar)


        var header: View? = navigationView?.inflateHeaderView(R.layout.header)

        var usuario: TextView = header!!.findViewById(R.id.usuario)
        var correo: TextView = header!!.findViewById(R.id.correo)

        val nombre=intent.getStringExtra("nombre") + ", " + intent.getStringExtra("apellido")
        val correoUser=intent.getStringExtra("email")
        val idProveedor=intent.getStringExtra("idEmpresas")
        val numdocumento=intent.getStringExtra("numdocumento")

        usuario.setText(nombre)
        correo.setText(correoUser)


        navigationView?.bringToFront()
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        drawerLayout?.openDrawer(Gravity.LEFT);

        navigationView?.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.facturas -> {

                    //startActivity(Intent(this,FacturasActivity::class.java))

                    val intentfac = Intent(this,FacturasActivity::class.java)
                    intentfac.putExtra("idProveedor",idProveedor.toString())
                    startActivity(intentfac)

                    true
                }
                R.id.finalizar -> {
                    finish()
                    true
                }

                R.id.cotizaciones ->{

                    //startActivity(Intent(this,CotizacionesActivity::class.java))

                    val intentfac = Intent(this,CotizacionesActivity::class.java)
                    intentfac.putExtra("numdocumento",numdocumento.toString())
                    startActivity(intentfac)

                    true
                }

                R.id.contratos ->{

                    //startActivity(Intent(this,ContratosActivity::class.java))

                    val intentfac = Intent(this,ContratosActivity::class.java)
                    intentfac.putExtra("idProveedor",idProveedor.toString())
                    startActivity(intentfac)

                    true
                }

/*                R.id.menu ->{
                    startActivity(Intent(this,MenuActivity::class.java))
                    true
                }*/


                else -> super.onOptionsItemSelected(it)
            }
        }

        cantRegSoles = findViewById(R.id.tvRegistrosSoles)
        cantRegDolares = findViewById(R.id.tvRegistrosDolares)

        soles = findViewById(R.id.tvCantSoles)
        dolares = findViewById(R.id.tvCantDolares)

        chart = findViewById(R.id.chart1)
        chart?.setOnChartValueSelectedListener(this)

        chart?.setDrawGridBackground(false)
        chart?.getDescription()?.isEnabled = false
        chart?.setDrawBorders(false)

        chart?.getAxisLeft()?.isEnabled = false
        chart?.getAxisRight()?.setDrawAxisLine(false)
        chart?.getAxisRight()?.setDrawGridLines(false)
        chart?.getXAxis()?.setDrawAxisLine(false)
        chart?.getXAxis()?.setDrawGridLines(false)

        var xAxisFormatter: ValueFormatter = DayAxisValueFormatter(chart)
        var xAxis = chart?.getXAxis()
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.setDrawGridLines(false)
        xAxis?.granularity = 1f // intervalo para 1 dia

        xAxis?.labelCount = 7
        xAxis?.valueFormatter = xAxisFormatter

        // enable touch gestures
        chart?.setTouchEnabled(true)

        // enable scaling and dragging
        chart?.setDragEnabled(true)
        chart?.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        chart?.setPinchZoom(false)


        val Valores1 = ArrayList<Double>() // VALORES EN SOLES, esta lista se traerá del API
        Valores1.add(0.0)
        Valores1.add(0.0)
        Valores1.add(0.0)
        Valores1.add(0.0)
        Valores1.add(0.0)
        Valores1.add(0.0)
        Valores1.add(0.0)
        Valores1.add(0.0)
        Valores1.add(0.0)



        val Valores2 = ArrayList<Double>() // VALORES EN DOLARES
        Valores2.add(0.0)
        Valores2.add(0.0)
        Valores2.add(0.0)
        Valores2.add(0.0)
        Valores2.add(0.0)
        Valores2.add(0.0)
        Valores2.add(0.0)
        Valores2.add(0.0)
        Valores2.add(0.0)



        var sumaSoles = 0;
        var sumaDolares = 0;

        for(i in 0..(Valores1.size-1)){
            sumaSoles += Valores1.get(i).toInt()
        }

        for(i in 0..(Valores1.size-1)){
            sumaDolares += Valores2.get(i).toInt()
        }

        soles?.setText("S/." + sumaSoles+".00")
        dolares?.setText("$" + sumaDolares+".00")

        cantRegSoles?.setText(Valores1.size.toString() + " Registros")
        cantRegDolares?.setText(Valores2.size.toString() + " Registros")


        val tipoCambio = ArrayList<String>()
        tipoCambio.add("S/.")
        tipoCambio.add("$")

        val coloresLineas = ArrayList<String>()
        coloresLineas.add("#662d91")
        coloresLineas.add("#21409a")


        var someList: ArrayList<List<Double>> = arrayListOf(ArrayList<Double>()) // LISTA QUE ALMACENA 2 LISTAS (VALORES1: SOLES, VALORES2:DOLARES)

        someList.plusAssign(Valores1)
        someList.plusAssign(Valores2)

        val progress = Valores1.size // define la cantidad de valores,
        Log.d("Valor de progress",progress.toString())

        val dataSets = ArrayList<ILineDataSet>()

        for (z in 0..1 ) { //  ESTE LOOP DEFINE LA CANTIDAD DE LINEAS
            val values =
                ArrayList<Entry>()
            for (i in 0..(progress-3) ) {  /// AQUI VA EL VALOR SIZE DE PROGRESS O EL TAMAÑO DE LA LISTA DE SOLES
                values.add(Entry(i.toFloat(), someList.get(z+1).get(i).toFloat())) //  ESTO REPRESENTA 1 SOLO ENTRY
            }

            val d = LineDataSet(values, "Valor en " + tipoCambio.get(z))
            d.lineWidth = 2.5f
            d.circleRadius = 4f
            val color = Color.parseColor(coloresLineas.get(z))
            d.setColor(color)
            d.setCircleColor(color)
            dataSets.add(d)
        }

        val data = LineData(dataSets)
        chart?.setData(data)
        chart?.invalidate()



    }


    fun getDashBoard() {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://dev.digitalfactoring.net/reporte/Extranet/gral/1126"

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
                        val moneda = recibo.getString("moneda")
                        val Tipo = recibo.getString("Tipo")
                        val Feb = recibo.getString("Feb")
                        val Mar = recibo.getString("Mar")
                        val Abr = recibo.getString("Abr")
                        val May = recibo.getString("May")
                        val Jun = recibo.getString("Jun")
                        val Jul = recibo.getString("Jul")
                        val Total_Desembolso = recibo.getString("Total_Desembolso")
                        val Total_Interes = recibo.getString("Total_Interes")

                    }


                    Log.d("OK", response.toString())

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            },
            Response.ErrorListener {

                    error -> Log.d("error", error.toString())

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


    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.i(
            "VAL SELECTED",
            "Value: " + e!!.y + ", xIndex: " + e.x
                    + ", DataSet index: " + h!!.dataSetIndex
        )
    }


}
