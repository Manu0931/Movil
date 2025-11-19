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

class NuevoProductoActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var descripcion: EditText
    private lateinit var precio: EditText
    private lateinit var stock: EditText
    private lateinit var IdProveedor: EditText
    private lateinit var estado: EditText
    private lateinit var previewImagen: ImageView
    private lateinit var btnSeleccionarImagen: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnVolver: ImageButton

    private var imagenUri: Uri? = null

    companion object {
        private const val REQUEST_IMAGE_PICK = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_form)

        // Binding XML
        nombre = findViewById(R.id.nombre)
        descripcion = findViewById(R.id.descripcion)
        precio = findViewById(R.id.precio)
        stock = findViewById(R.id.stock)
        IdProveedor = findViewById(R.id.idProveedor)
        estado = findViewById(R.id.estado)
        previewImagen = findViewById(R.id.previewImagen)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnVolver = findViewById(R.id.btnVolver)

        // Volver
        btnVolver.setOnClickListener { finish() }

        // Seleccionar imagen
        btnSeleccionarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        // Guardar
        btnGuardar.setOnClickListener {
            guardarProducto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            imagenUri = data?.data
            previewImagen.setImageURI(imagenUri)
        }
    }

    // Convertir URI a File sin PathUtil
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

    private fun guardarProducto() {
        val nombre = nombre.text.toString()
        val descripcion = descripcion.text.toString()
        val precio = precio.text.toString()
        val stock = stock.text.toString()
        val idProveedor = IdProveedor.text.toString()
        val estado = estado.text.toString()

        if (nombre.isEmpty() || descripcion.isEmpty() || precio.isEmpty() ||
            stock.isEmpty() || idProveedor.isEmpty() || estado.isEmpty()
        ) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // RequestBody
        val bodyNombre = RequestBody.create("text/plain".toMediaTypeOrNull(), nombre)
        val bodyDescripcion = RequestBody.create("text/plain".toMediaTypeOrNull(), descripcion)
        val bodyPrecio = RequestBody.create("text/plain".toMediaTypeOrNull(), precio)
        val bodyStock = RequestBody.create("text/plain".toMediaTypeOrNull(), stock)
        val bodyProveedor = RequestBody.create("text/plain".toMediaTypeOrNull(), idProveedor)
        val bodyEstado = RequestBody.create("text/plain".toMediaTypeOrNull(), estado)

        var imagenBody: MultipartBody.Part? = null

        if (imagenUri != null) {
            val file = uriToFile(imagenUri!!)
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            imagenBody =
                MultipartBody.Part.createFormData("imagen", file.name, requestFile)
        }

        RetrofitInstance.productosApi.insertarProducto(
            bodyNombre,
            bodyDescripcion,
            bodyPrecio,
            bodyStock,
            bodyProveedor,
            bodyEstado,
            imagenBody
        ).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@NuevoProductoActivity,
                        "Producto guardado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@NuevoProductoActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@NuevoProductoActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
