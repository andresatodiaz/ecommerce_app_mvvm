package com.example.ecommmerceapp.presentation.Producto

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecommmerceapp.MainApplication
import com.example.ecommmerceapp.data.Entities.Producto
import com.example.ecommmerceapp.presentation.Home.ViewModel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductoScreen(
    photo: String,
    producto:Producto,
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    Log.i("photo",photo)
    val coroutine = rememberCoroutineScope()
    val brightness = -50f
    val colorMatrix = floatArrayOf(
        1f, 0f, 0f, 0f, brightness,
        0f, 1f, 0f, 0f, brightness,
        0f, 0f, 1f, 0f, brightness,
        0f, 0f, 0f, 1f, 0f
    )
    val id = remember{ mutableStateOf("") }
    LaunchedEffect(key1 = true){
        val sp = MainApplication.applicationContext().getSharedPreferences(
            "preferences",
            Context.MODE_PRIVATE
        )
        id.value=sp.getString("LOGGED_ID","")!!
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        AsyncImage(model = photo, contentDescription = "background",
            modifier= Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
        )
        Text(producto.titulo,modifier= Modifier.padding(top=80.dp), fontWeight = FontWeight.Black, color = Color.White, fontSize = 20.sp)
        LazyColumn(
            modifier= Modifier
                .fillMaxWidth()
                .padding(top = 230.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Text("Descripcion")
                    Text(producto.descripcion)
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Text("Precio")
                    Text(producto.precio+" $")
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Text("Estado")
                    Text(producto.estado.toString())

                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Column(modifier= Modifier.fillMaxWidth(0.9f)) {
                    Text("Vendido por")
                    Text(producto.vendidoPor.toString())
                }
                Spacer(Modifier.padding(10.dp))
            }
            item{
                Button(
                    onClick = {
                        coroutine.launch {
                            homeViewModel.borrarProducto(producto)
                            navController.navigate("home")
                            homeViewModel.getProductos()
                        }
                    },
                    modifier= Modifier.fillMaxWidth(0.9f)
                ) {
                    Text("Borrar producto")
                }
            }

        }
    }
}