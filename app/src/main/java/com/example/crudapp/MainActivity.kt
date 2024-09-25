package com.example.crudapp

import AppDatabase
import Item
import ItemRepository
import ItemViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración de la base de datos
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()

        val repository = ItemRepository(db.itemDao())
        val itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        // Cargar contenido de Compose
        setContent {
            MyApp(itemViewModel)
        }
    }
}

@Composable
fun MyApp(itemViewModel: ItemViewModel) {
    var itemName by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }
    var items by remember { mutableStateOf(listOf<Item>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Campo para el nombre del ítem
        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Nombre del ítem") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para la descripción del ítem
        TextField(
            value = itemDescription,
            onValueChange = { itemDescription = it },
            label = { Text("Descripción del ítem") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para añadir un nuevo ítem
        Button(onClick = {
            if (itemName.isNotEmpty() && itemDescription.isNotEmpty()) {
                val newItem = Item(name = itemName, description = itemDescription)
                itemViewModel.insert(newItem)
                itemName = ""
                itemDescription = ""
            }
        }) {
            Text("Añadir ítem")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para cargar todos los ítems
        Button(onClick = {
            itemViewModel.getAllItems { retrievedItems ->
                items = retrievedItems
            }
        }) {
            Text("Ver todos los ítems")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la lista de ítems
        LazyColumn {
            items(items) { item ->
                Text("${item.name}: ${item.description}")
            }
        }
    }
}
