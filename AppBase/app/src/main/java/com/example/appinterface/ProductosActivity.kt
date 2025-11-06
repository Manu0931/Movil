package com.example.appinterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductosActivity : AppCompatActivity() {

    private lateinit var recyclerCategorias: RecyclerView
    private lateinit var adapter: CategoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        // Vincular el RecyclerView del layout
        recyclerCategorias = findViewById(R.id.recyclerCategorias)

        // Lista de categorías con nombre e imagen
        val listaCategorias = listOf(
            Categoria("Camisetas", R.mipmap.camiseta_boxy),
            Categoria("Pantalones", R.mipmap.pantalon_cargo),
            Categoria("Zapatos", R.mipmap.tenisnb),
        )

        // Configurar adaptador y layout en forma de cuadrícula (2 columnas)
        adapter = CategoriaAdapter(listaCategorias)
        recyclerCategorias.layoutManager = GridLayoutManager(this, 2)
        recyclerCategorias.adapter = adapter
    }
}
