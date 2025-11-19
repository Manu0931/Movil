package com.example.appinterface.producto

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class EditarProductoActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etStock: EditText
    private lateinit var etProveedor: EditText
    private lateinit var etEstado: EditText
    private lateinit var previewImagen: ImageView
    private lateinit var btnSeleccionar: Button
    private lateinit var btnActualizarProducto: Button
    private lateinit var btnVolverEditar: ImageView

    private var imagenUri: Uri? = null
    private var idProducto: Int = 0

    companion object {
        private const val REQUEST_IMAGE_PICK = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)

        etNombre = findViewById(R.id.etNombre)
        etDescripcion = findViewById(R.id.etDescripcion)
        etPrecio = findViewById(R.id.etPrecio)
        etStock = findViewById(R.id.etStock)
        etProveedor = findViewById(R.id.etIdProveedor)
        etEstado = findViewById(R.id.etEstado)
        previewImagen = findViewById(R.id.imgProducto)
        btnSeleccionar = findViewById(R.id.btnSeleccionarImagen)
        btnActualizarProducto = findViewById(R.id.btnActualizarProducto)
        btnVolverEditar = findViewById(R.id.btnVolverEditar)

        idProducto = intent.getIntExtra("idProducto", 0)

        etNombre.setText(intent.getStringExtra("nombre") ?: "")
        etDescripcion.setText(intent.getStringExtra("descripcion") ?: "")
        etPrecio.setText(intent.getDoubleExtra("precio", 0.0).toString())
        etStock.setText(intent.getIntExtra("stock", 0).toString())
        etProveedor.setText(intent.getIntExtra("idProveedor", 0).toString())
        etEstado.setText(intent.getStringExtra("estado") ?: "")

        val imagenUrl = intent.getStringExtra("imagen")
        if (!imagenUrl.isNullOrEmpty()) {
            Picasso.get().load(imagenUrl).into(previewImagen)
        }

        btnVolverEditar.setOnClickListener { finish() }

        btnSeleccionar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        btnActualizarProducto.setOnClickListener {
            actualizarProducto()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            imagenUri = data?.data
            previewImagen.setImageURI(imagenUri)
        }
    }

    // --- Convertir String/Int/Double a RequestBody ---
    private fun toPlain(value: Any): RequestBody {
        return value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
    }

    // Convertir URI → File
    private fun uriToFile(uri: Uri): File {
        val cursor = contentResolver.query(uri, null, null, null, null)
        val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: -1
        cursor?.moveToFirst()
        val fileName = if (nameIndex != -1) cursor?.getString(nameIndex) else "temp_image.jpg"
        cursor?.close()

        val input = contentResolver.openInputStream(uri)
        val file = File(cacheDir, fileName!!)
        val output = FileOutputStream(file)

        input?.copyTo(output)
        input?.close()
        output.close()

        return file
    }

    private fun actualizarProducto() {

        if (idProducto == 0) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val estadoTexto = if (etEstado.text.toString().trim().equals("Activo", ignoreCase = true)) {
            "Activo"
        } else {
            "Inactivo"
        }

        var imagenPart: MultipartBody.Part? = null

        // Procesar imagen si fue seleccionada
        if (imagenUri != null) {
            val file = uriToFile(imagenUri!!)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            imagenPart = MultipartBody.Part.createFormData("imagen", file.name, requestFile)
        }

        // Llamada Retrofit
        RetrofitInstance.productosApi.actualizarProducto(
            idProducto,
            toPlain(etNombre.text.toString()),
            toPlain(etDescripcion.text.toString()),
            toPlain(etPrecio.text.toString().toDoubleOrNull() ?: 0.0),
            toPlain(etStock.text.toString().toIntOrNull() ?: 0),
            toPlain(etProveedor.text.toString().toIntOrNull() ?: 0),
            toPlain(estadoTexto),
            imagenPart
        ).enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarProductoActivity, "Producto actualizado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarProductoActivity, "Error (${response.code()})", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditarProductoActivity, "Fallo: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
