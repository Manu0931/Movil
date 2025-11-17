package com.example.appinterface.producto

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    private lateinit var etIdProveedor: EditText
    private lateinit var etEstado: EditText
    private lateinit var imgProducto: ImageView
    private lateinit var btnSeleccionarImagen: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnVolver: ImageView

    private var imagenUri: Uri? = null
    private var productoId: Int = 0

    companion object {
        private const val REQUEST_IMAGE_PICK = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)

        // ENLAZAR XML
        imgProducto = findViewById(R.id.imgProducto)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        btnActualizar = findViewById(R.id.btnActualizarProducto)
        btnVolver = findViewById(R.id.btnVolverEditar)

        etNombre = findViewById(R.id.etNombre)
        etDescripcion = findViewById(R.id.etDescripcion)
        etPrecio = findViewById(R.id.etPrecio)
        etStock = findViewById(R.id.etStock)
        etIdProveedor = findViewById(R.id.etIdProveedor)
        etEstado = findViewById(R.id.etEstado)

        // OBTENER DATOS DEL INTENT
        productoId = intent.getIntExtra("idProducto", 0)
        etNombre.setText(intent.getStringExtra("nombre"))
        etDescripcion.setText(intent.getStringExtra("descripcion"))
        etPrecio.setText(intent.getDoubleExtra("precio", 0.0).toString())
        etStock.setText(intent.getIntExtra("stock", 0).toString())
        etIdProveedor.setText(intent.getIntExtra("idProveedor", 0).toString())
        etEstado.setText(intent.getStringExtra("estado"))

        val imagenUrl = intent.getStringExtra("imagen")
        if (!imagenUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(imagenUrl)
                .into(imgProducto)
        }

        // EVENTOS
        btnVolver.setOnClickListener { finish() }

        btnSeleccionarImagen.setOnClickListener {
            seleccionarImagen()
        }

        btnActualizar.setOnClickListener {
            actualizarProducto()
        }
    }

    // ABRIR GALERÍA
    private fun seleccionarImagen() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            imagenUri = data?.data
            imgProducto.setImageURI(imagenUri)
        }
    }

    // CONVERTIR URI → FILE (SIN PATHUTIL)
    private fun uriToFile(uri: Uri): File {
        val returnCursor = contentResolver.query(uri, null, null, null, null)
        val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: -1
        returnCursor?.moveToFirst()
        val fileName = if (nameIndex != -1) returnCursor?.getString(nameIndex) else "imagen_temp.jpg"
        returnCursor?.close()

        val inputStream = contentResolver.openInputStream(uri)
        val file = File(cacheDir, fileName!!)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()

        return file
    }

    private fun actualizarProducto() {
        val nombre = etNombre.text.toString()
        val descripcion = etDescripcion.text.toString()
        val precio = etPrecio.text.toString()
        val stock = etStock.text.toString()
        val proveedor = etIdProveedor.text.toString()
        val estado = etEstado.text.toString()

        val bodyNombre = RequestBody.create("text/plain".toMediaTypeOrNull(), nombre)
        val bodyDescripcion = RequestBody.create("text/plain".toMediaTypeOrNull(), descripcion)
        val bodyPrecio = RequestBody.create("text/plain".toMediaTypeOrNull(), precio)
        val bodyStock = RequestBody.create("text/plain".toMediaTypeOrNull(), stock)
        val bodyProveedor = RequestBody.create("text/plain".toMediaTypeOrNull(), proveedor)
        val bodyEstado = RequestBody.create("text/plain".toMediaTypeOrNull(), estado)

        var imagenBody: MultipartBody.Part? = null

        if (imagenUri != null) {
            val file = uriToFile(imagenUri!!)
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            imagenBody = MultipartBody.Part.createFormData("imagen", file.name, requestFile)
        }

        RetrofitInstance.productosApi.actualizarProducto(
            productoId,
            bodyNombre,
            bodyDescripcion,
            bodyPrecio,
            bodyStock,
            bodyProveedor,
            bodyEstado,
            imagenBody
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarProductoActivity, "Producto actualizado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarProductoActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@EditarProductoActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

