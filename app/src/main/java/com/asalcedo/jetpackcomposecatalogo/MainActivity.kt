package com.asalcedo.jetpackcomposecatalogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.asalcedo.jetpackcomposecatalogo.model.Routes.*
import com.asalcedo.jetpackcomposecatalogo.ui.theme.JetpackComposeCatalogoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeCatalogoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    /*var show by remember {
                        mutableStateOf(false)
                    }
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button(onClick = { show = true }) {
                            Text(text = "Mostrar diálogo")
                        }
                        /*MyAlertDialog(
                            show = show,
                            onDismiss = { show = false },
                            onConfirm = { Log.i("Alex", "click") })*/
                        //MySimpleCustomDialog(show = show, onDismiss = { show = false })
                        //MyCustomDialog(show = show, onDismiss = { show = false }, modifier = Modifier)
                        MyConfirmationDialog(show = show, onDismiss = {false})
                    }*/

                    //SimpleRecyclerView()
                    //SuperHeroView()
                    //SuperHeroGridView()
                    //SuperHeroWithSpecialControlsView()
                    //SuperHeroStickyView()
                    //ScaffoldExample()

                    //Navegación
                    //La variable gestiona los estados de la navegación
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Pantalla1.route
                    ) {
                        //Para navegar entre pantallas se necesita tener una ID única
                        composable(Pantalla1.route) { Screen1(navigationController) }
                        composable(Pantalla2.route) { Screen2(navigationController) }
                        composable(Pantalla3.route) { Screen3(navigationController) }
                        //en backStackEntry estará el argumento llamado name
                        composable(
                            Pantalla4.route,
                            arguments = listOf(navArgument("age") { type = NavType.IntType })
                        ) { backStackEntry ->
                            Screen4(
                                navigationController,
                                backStackEntry.arguments?.getInt("age") ?: 0
                            )

                        }
                        composable(
                            Pantalla5.route,
                            arguments = listOf(navArgument("name") { defaultValue = "Alex" })
                        ) { backStackEntry ->
                            Screen5(
                                navigationController,
                                backStackEntry.arguments?.getString("name")
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MyDropDownMenu() {
    var selectedText by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val desserts = listOf<String>("Helado", "Chocolate", "Café", "Fruta", "Natillas", "Chilaquiles")

    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .clickable { expanded = true }
                .fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            desserts.forEach { dessert ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    selectedText = dessert
                }) {
                    Text(text = dessert)
                }
            }
        }
    }
}

@Composable
fun MyDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )
}

@Composable
fun MyBadgeBox() {
    BadgedBox(
        modifier = Modifier.padding(16.dp),
        badge = { Text(text = "1", modifier = Modifier.background(Color.Red)) }) {
        Icon(imageVector = Icons.Default.Star, contentDescription = "")
    }
}

@Composable
fun MyCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 12.dp,
        shape = MaterialTheme.shapes.small,
        backgroundColor = Color.Cyan,
        contentColor = Color.DarkGray,
        border = BorderStroke(5.dp, Color.Gray)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Ejemplo1")
            Text(text = "Ejemplo2")
            Text(text = "Ejemplo3")
        }
    }
}

@Composable
fun MyStateExample() {
    var counter by rememberSaveable {
        mutableStateOf(0)
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { counter += 1 }) {
            Text(text = "Pulsar")
        }
        Text(text = "He sido pulsado $counter veces")
    }
}

@Composable
fun MySpacer(size: Int) {
    Spacer(modifier = Modifier.height(size.dp))
}

@Composable
fun MyComplexLayout() {
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Cyan), contentAlignment = Alignment.Center
        ) {
            Text(text = "Alex")
        }
        MySpacer(30)
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.Red), contentAlignment = Alignment.Center
            ) {
                Text(text = "Derek")
            }
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.Green), contentAlignment = Alignment.Center
            ) {
                Text(text = "Hola soy Alex")
            }
        }
        MySpacer(size = 80)
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Magenta), contentAlignment = Alignment.BottomCenter
        ) {
            Text(text = "Yessica", fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MyRow() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Ejemplo1")
        Text(text = "Ejemplo2")
        Text(text = "Ejemplo3")
    }
}

@Composable
fun MyColumn() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Ejemplo1",
            modifier = Modifier
                .background(Color.Red)
                .fillMaxWidth()
                .height(100.dp)
        )
        Text(
            text = "Ejemplo2",
            modifier = Modifier
                .background(Color.Blue)
                .fillMaxWidth()
                .height(100.dp)
        )
        Text(
            text = "Ejemplo3",
            modifier = Modifier
                .background(Color.Green)
                .fillMaxWidth()
                .height(100.dp)
        )
        Text(
            text = "Ejemplo4",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .height(100.dp)
        )
    }

}

@Composable
fun MyBox() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .background(Color.Cyan)
                .verticalScroll(rememberScrollState()), contentAlignment = Alignment.Center
        ) {
            Text(text = "Esto es un ejemplo")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeCatalogoTheme {
        //MyBox()
        //MyColumn()
        //MyRow()
        //MyComplexLayout()
        //MyStateExample()
        //MyCard()
        //MyBadgeBox()
        //MyDivider()
        MyDropDownMenu()

    }
}