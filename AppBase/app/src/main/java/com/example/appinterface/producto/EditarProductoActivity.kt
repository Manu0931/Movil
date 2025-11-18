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
        // Determinar estado
        val estadoTexto = if (etEstado.text.toString().trim().equals("Activo", ignoreCase = true)) {
            "Activo"
        } else {
            "Inactivo"
        }

        // Crear objeto Producto con datos actuales
        val productoActualizado = Producto(
            idProducto = idProducto,
            nombre = etNombre.text.toString(),
            descripcion = etDescripcion.text.toString(),
            precio = etPrecio.text.toString().toDoubleOrNull() ?: 0.0,
            stock = etStock.text.toString().toIntOrNull() ?: 0,
            idProveedor = etProveedor.text.toString().toIntOrNull() ?: 0,
            estado = estadoTexto,
            imagen = null // La imagen la manejaremos aparte si se selection
        )

        // Preparar Multipart
        var imagenPart: MultipartBody.Part? = null
        if (imagenUri != null) {
            val file = uriToFile(imagenUri!!)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            imagenPart = MultipartBody.Part.createFormData("imagen", file.name, requestFile)
        }

        // Llamada a Retrofit
        RetrofitInstance.productosApi.actualizarProducto(
            idProducto,
            productoActualizado.nombre.toRequestBody("text/plain".toMediaTypeOrNull()),
            productoActualizado.descripcion.toRequestBody("text/plain".toMediaTypeOrNull()),
            productoActualizado.precio.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            productoActualizado.stock.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            productoActualizado.idProveedor.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            productoActualizado.estado.toRequestBody("text/plain".toMediaTypeOrNull()),
            imagenPart
        ).enqueue(object : Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
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

            override fun onFailure(call: Call<Producto>, t: Throwable) {
                Toast.makeText(this@EditarProductoActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
