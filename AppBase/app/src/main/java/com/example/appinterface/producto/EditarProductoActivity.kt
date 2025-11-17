package com.example.appinterface.producto

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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

        // Binding
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

        // Regresar
        btnVolverEditar.setOnClickListener { finish() }

        // Recibir datos enviados desde ProductosActivity
        val producto = intent.getSerializableExtra("producto") as? Producto

        if (producto != null) {
            idProducto = producto.idProducto ?: 0
            etNombre.setText(producto.nombre)
            etDescripcion.setText(producto.descripcion)
            etPrecio.setText(producto.precio.toString())
            etStock.setText(producto.stock.toString())
            etProveedor.setText(producto.idProveedor.toString())
            etEstado.setText(producto.estado)

            // Cargar imagen actual
            com.squareup.picasso.Picasso.get().load(producto.imagen).into(previewImagen)
        }

        // Seleccionar imagen
        btnSeleccionar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        // Actualizar
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

    // Convertir URI → File
    private fun uriToFile(uri: Uri): File {
        val cursor = contentResolver.query(uri, null, null, null, null)
        val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: -1
        cursor?.moveToFirst()
        val fileName =
            if (nameIndex != -1) cursor?.getString(nameIndex) else "temp_image.jpg"
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

        // RequestBody
        val nombreBody = RequestBody.create("text/plain".toMediaTypeOrNull(), etNombre.text.toString())
        val descripcionBody = RequestBody.create("text/plain".toMediaTypeOrNull(), etDescripcion.text.toString())
        val precioBody = RequestBody.create("text/plain".toMediaTypeOrNull(), etPrecio.text.toString())
        val stockBody = RequestBody.create("text/plain".toMediaTypeOrNull(), etStock.text.toString())
        val proveedorBody = RequestBody.create("text/plain".toMediaTypeOrNull(), etProveedor.text.toString())
        val estadoBody = RequestBody.create("text/plain".toMediaTypeOrNull(), etEstado.text.toString())

        var imagenBody: MultipartBody.Part? = null

        if (imagenUri != null) {
            val file = uriToFile(imagenUri!!)
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            imagenBody = MultipartBody.Part.createFormData("imagen", file.name, requestFile)
        }

        RetrofitInstance.productosApi.actualizarProducto(
            idProducto,
            nombreBody,
            descripcionBody,
            precioBody,
            stockBody,
            proveedorBody,
            estadoBody,
            imagenBody
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditarProductoActivity,
                        "Producto actualizado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@EditarProductoActivity,
                        "Error al actualizar (${response.code()})",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@EditarProductoActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}



