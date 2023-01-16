package com.asalcedo.jetpackcomposecatalogo

import android.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asalcedo.jetpackcomposecatalogo.model.SuperHero
import kotlinx.coroutines.launch

@Composable
fun SimpleRecyclerView() {
    val myList = listOf(
        "Alex",
        "Pepe",
        "Luis",
        "Derek",
        "Alex",
        "Pepe",
        "Luis",
        "Derek",
        "Alex",
        "Pepe",
        "Luis",
        "Derek",
        "Alex", "Pepe", "Luis", "Derek",
        "Alex",
        "Pepe",
        "Luis",
        "Derek",
        "Alex",
        "Pepe",
        "Luis",
        "Derek",
        "Alex",
        "Pepe",
        "Luis",
        "Derek",
        "Alex", "Pepe", "Luis", "Derek"
    )
    LazyColumn {
        //Todos los recyclerView reciben objetos item
        item {
            Text(text = "Primer item")
        }
        items(7) {
            Text(text = "Este es el item $it")
        }
        items(myList) {
            Text(text = "Hola me llamo $it")
        }
    }
}

@Composable
fun SuperHeroView() {
    var context = LocalContext.current
    //Horizontal
    /*LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(getSuperHeroes()) { superHero ->
            ItemHero(superHero = superHero)

        }
    }*/

    //Vertical
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(getSuperHeroes()) { superHero ->
            ItemHero(superHero = superHero) {
                Toast.makeText(
                    context,
                    it.superHeroName,
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuperHeroStickyView() {
    val context = LocalContext.current
    //Para un Sticky se necesita un Map por eso lo agrupamos de otra forma
    val superHero = getSuperHeroes().groupBy { it.publisher }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        superHero.forEach { (publisher, mySuperHero) ->
            stickyHeader {
                Text(
                    text = publisher,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(androidx.compose.ui.graphics.Color.Green),
                    fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.White
                )
            }
            items(mySuperHero) { superHero ->
                ItemHero(superHero = superHero) {
                    Toast.makeText(
                        context,
                        it.superHeroName,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }


    }
}

@Composable
fun SuperHeroWithSpecialControlsView() {
    val context = LocalContext.current
    val rvState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column {
        LazyColumn(
            state = rvState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(getSuperHeroes()) { superHero ->
                ItemHero(superHero = superHero) {
                    Toast.makeText(
                        context,
                        it.superHeroName,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
        val showButton by remember {
            derivedStateOf {
                rvState.firstVisibleItemIndex > 0
            }
        }

        if (showButton) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        rvState.animateScrollToItem(0)
                    }

                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(text = "Púlsame")
            }
        }


    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuperHeroGridView() {
    var context = LocalContext.current
    LazyVerticalGrid(cells = GridCells.Fixed(2), content = {
        items(getSuperHeroes()) { superHero ->
            ItemHero(superHero = superHero) {
                Toast.makeText(
                    context,
                    it.superHeroName,
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }, contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp))
}

@Composable
fun ItemHero(superHero: SuperHero, onItemSelected: (SuperHero) -> Unit) {
    Card(
        border = BorderStroke(2.dp, androidx.compose.ui.graphics.Color.Red),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemSelected(superHero) }
    ) {
        Column {
            Image(
                painter = painterResource(id = superHero.photo),
                contentDescription = "SuperHero Avatar",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = superHero.superHeroName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = superHero.realName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 12.sp
            )
            Text(
                text = superHero.publisher,
                fontSize = 10.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 6.dp, bottom = 6.dp)
            )
        }
    }
}

fun getSuperHeroes(): List<SuperHero> {
    return listOf(
        SuperHero("Spiderman", "Petter Parker", "Marvel", R.drawable.spiderman),
        SuperHero("Wolverine", "James Howlett", "Marvel", R.drawable.logan),
        SuperHero("Batman", "Bruce Wayne", "DC", R.drawable.batman),
        SuperHero("Thor", "Thor Odinson", "Marvel", R.drawable.thor),
        SuperHero("Flash", "Jay Garrick", "DC", R.drawable.flash),
        SuperHero("Green Lantern", "Alan Scott", "DC", R.drawable.green_lantern),
        SuperHero("Wonder Woman", "Princes Diana", "DC", R.drawable.wonder_woman),
    )
}