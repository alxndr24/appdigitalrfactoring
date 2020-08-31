package com.example.digitalfactoring

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var myProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var login = findViewById<Button>(R.id.login)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        myProgressBar = findViewById(R.id.progressBar)


        login.setOnClickListener {
            login.setTextColor(Color.parseColor("#ba55d3"))
            getUsers(username.text.toString(),password.text.toString())

            //val intent = Intent(this,MenuActivity::class.java)
            //startActivity(intent)
        }
    }

    fun getUsers(user : String, pass : String) {
        // Pasar usuario y correo al dashboard
        //
        myProgressBar?.visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://dev.digitalfactoring.net/security/loginPR"

        // Request a string response from the provided URL.
        val stringReq = object: JsonObjectRequest(
            Request.Method.POST, url,null,
            Response.Listener {
                    response ->  Toast.makeText(applicationContext, "Bienvenido! " + response["Nombre"].toString(), Toast.LENGTH_SHORT).show()


                    //Ir a pantalla menu
                myProgressBar?.visibility = View.GONE
                login.setTextColor(Color.parseColor("#fffafa"))


                val intent = Intent(this,DashBoardActivity::class.java)
                intent.putExtra("nombre",response["Nombre"].toString())
                intent.putExtra("email",response["Email"].toString())
                intent.putExtra("apellido",response["Apellido"].toString())
                intent.putExtra("idEmpresas",response["idEmpresas"].toString())
                intent.putExtra("numdocumento",response["Ruc"].toString())

                //intent.putExtra("objUsuario", response.toString())
                startActivity(intent)
            },
            Response.ErrorListener {
                    error -> Log.d("errorComprobante", error.toString())
                myProgressBar?.visibility = View.GONE
                login.setTextColor(Color.parseColor("#fffafa"))


                Toast.makeText(applicationContext, "Datos invalidos, volver a intentarlo", Toast.LENGTH_SHORT).show()
            })

            {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "863EOgvVNFYgvVzwEWyvsi6qqX11GNtJN3Mf7kwt"
                    headers["Content-Type"] = "application/json"
                    return headers
                }

                override fun getBody(): ByteArray {
                    val params2 = HashMap<String, String>()
                    params2.put("Usuario",user)
                    params2.put("Password", pass)
                    params2.put("Ruc", "0")
                    params2.put("NombreProyecto", "Extranet")
                    params2.put("Host", "0")
                    params2.put("IP", "0")
                    params2.put("Canal", "0")

                    return JSONObject(params2 as Map<String, String>).toString().toByteArray()
                }
            }

        queue.add(stringReq)
    }


}
