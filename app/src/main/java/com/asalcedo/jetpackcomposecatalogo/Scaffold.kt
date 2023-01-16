package com.asalcedo.jetpackcomposecatalogo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//El Scaffold necesita guardar su estado para los otros componentes que estan en su interior
// existen estados ya preparados como por ejemplo del Scaffold y no tendríamos que hacer el
// remember{mutableStateOf()}

@Composable
fun ScaffoldExample() {
    //estado propio del Scaffold
    val scaffoldState = rememberScaffoldState()
    //Para lanzar una coroutine
    val coroutineScope = rememberCoroutineScope()

    //Para las acciones de los botones de la toolbar, la toolbar debe recibir una función lambda

    Scaffold(
        topBar = {
            MyTopAppBar(onClickIcon = {
                //Para mostrar un SnackBar se necesita de coroutines
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Has pulsado $it")
                }
            }, onClickDrawer = {
                //Para mostrar un Drawer en necesario usar coroutines
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            })
        },
        scaffoldState = scaffoldState,
        bottomBar = { MyBottomNavigation() },
        floatingActionButton = { MyFAB() },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = false,
        drawerContent = {
            MyDrawer(onCloseDrawer = {
                coroutineScope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        },
        drawerGesturesEnabled = false
    ) {

    }
}

// La lambda que recibe va a devolver un String con el texto que debe pintar
// y otro lambda para abrir el NavigationDrawer
@Composable
fun MyTopAppBar(onClickIcon: (String) -> Unit, onClickDrawer: () -> Unit) {
    TopAppBar(title = { Text(text = "Mi primera toolbar") }, navigationIcon = {
        //Con la lambda onClickIcon lanzamos el NavigationDrawer
        IconButton(onClick = { onClickDrawer() }) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu")
        }
    }, actions = {
        //Con la lambda onClickIcon lanzamos el SnackBar
        IconButton(onClick = { onClickIcon("Buscar") }) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
        }
        //Con la lambda onClickIcon lanzamos el SnackBar
        IconButton(onClick = { onClickIcon("Borrar") }) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete")
        }
    })
}

@Composable
fun MyBottomNavigation() {
    //Variable usada para que almenos un item de navegación esté habilitado
    var index by remember { mutableStateOf(0) }

    BottomNavigation {
        BottomNavigationItem(selected = index == 0, onClick = { index = 0 }, icon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home"
            )
        }, label = { Text(text = "Home") })

        BottomNavigationItem(selected = index == 1, onClick = { index = 1 }, icon = {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite"
            )
        }, label = { Text(text = "Favorite") })

        BottomNavigationItem(selected = index == 2, onClick = { index = 2 }, icon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person"
            )
        }, label = { Text(text = "Person") })
    }
}

@Composable
fun MyFAB() {
    FloatingActionButton(onClick = { }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }
}

@Composable
fun MyDrawer(onCloseDrawer: () -> Unit) {
    Column(Modifier.padding(8.dp)) {
        Text(
            text = "Primera opción", modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onCloseDrawer() }
        )

        Text(
            text = "Segunda opción", modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onCloseDrawer() }
        )

        Text(
            text = "Tercera opción", modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onCloseDrawer() }
        )
        Text(
            text = "Cuarta opción", modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onCloseDrawer() }
        )
    }
}