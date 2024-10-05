import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projetaslagi.R
import com.example.projetaslagi.uix.viewmodel.AnasayfaViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Anasayfa(navController: NavController, viewModel: AnasayfaViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val yemeklerListesi by viewModel.yemeklerListesi.observeAsState(emptyList())
    val aramaYapiliyorMu = remember { mutableStateOf(false) }
    val kullaniciAdi by viewModel.kullaniciAdi.observeAsState("ebru")
    viewModel.setKullaniciAdi("KullaniciAdi")
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Yemekler") },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = colorResource(id = R.color.anaRenk),
                        titleContentColor = colorResource(id = R.color.white)
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { newValue -> searchQuery = newValue },
                        label = { Text(text = "Ara") },
                        trailingIcon = {
                            if (aramaYapiliyorMu.value) {
                                IconButton(onClick = {
                                    searchQuery = ""
                                    aramaYapiliyorMu.value = false
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.kapat_resim),
                                        contentDescription = "Kapat",
                                        tint = Color.Black
                                    )
                                }
                            } else {
                                IconButton(onClick = {
                                    aramaYapiliyorMu.value = true
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ara_resim),
                                        contentDescription = "Ara",
                                        tint = Color.Black
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = colorResource(id = R.color.white),
                            focusedLabelColor = Color.Black,
                            focusedIndicatorColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }




                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            viewModel.yemeklerListesi.value = yemeklerListesi?.sortedBy { it.yemek_fiyat }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.black)) // Anarenk
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.yukari),
                            contentDescription = "Yukarı Ok",
                            modifier = Modifier.size(8.dp), // İkon boyutu
                            tint = Color.White // İkon rengi
                        )
                        Spacer(modifier = Modifier.width(4.dp)) // İkon ile metin arasında boşluk
                        Text("Fiyat ", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(
                        onClick = {
                            viewModel.yemeklerListesi.value = yemeklerListesi?.sortedByDescending { it.yemek_fiyat }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.black)) // Anarenk
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.asagi), // Aşağı bakan ok simgesi
                            contentDescription = "Aşağı Ok",
                            modifier = Modifier.size(8.dp), // İkon boyutu
                            tint = Color.White // İkon rengi
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Fiyat ", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(4.dp))
                    Button(
                        onClick = {
                            viewModel.yemeklerListesi.value = yemeklerListesi?.sortedBy { it.yemek_adi }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.black)) // Anarenk
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.yukari), // Yukarı bakan ok simgesi
                            contentDescription = "Yukarı Ok",
                            modifier = Modifier.size(8.dp), // İkon boyutu
                            tint = Color.White // İkon rengi
                        )
                        Spacer(modifier = Modifier.width(4.dp)) // İkon ile metin arasında boşluk
                        Text("İsim ", color = Color.White)
                    }


                    Spacer(modifier = Modifier.width(4.dp))
                    Button(
                        onClick = {
                            viewModel.yemeklerListesi.value = yemeklerListesi?.sortedByDescending { it.yemek_adi }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.black)) // Anarenk
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.asagi), // Aşağı bakan ok simgesi
                            contentDescription = "Aşağı Ok",
                            modifier = Modifier.size(8.dp), // İkon boyutu
                            tint = Color.White // İkon rengi
                        )
                        Spacer(modifier = Modifier.width(10.dp)) // İkon ile metin arasında boşluk
                        Text("İsim ", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                }




            }
        },












        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.white)
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null, tint = Color.Black) },
                    label = { Text("Ana Sayfa", color = Color.Black) },
                    onClick = { navController.navigate("anasayfa") },
                    selected = false,
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.Black) },
                    label = { Text("sepet", color = Color.Black) },
                    onClick = {
                        coroutineScope.launch{
                            navController.navigate("sepet_sayfasi/${"ebru"}") {
                                popUpTo("anasayfa") { inclusive = true }
                            }
                        }

                        },
                    selected = false,
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.Black) },
                    label = { Text("Beğenilen", color = Color.Black) },
                    onClick = { /* no action */ },
                    selected = false,
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, contentDescription = null, tint = Color.Black) },
                    label = { Text("katagori", color = Color.Black) },
                    onClick = { /* no action */ },
                    selected = false,
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Black) },
                    label = { Text("Profil", color = Color.Black) },
                    onClick = { /* no action */ },
                    selected = false,
                    alwaysShowLabel = true
                )
            }
        }


    )

    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(count = 2),
                contentPadding = PaddingValues(8.dp)
            ) {
                val filteredList = yemeklerListesi?.filter {
                    it.yemek_adi?.contains(searchQuery, ignoreCase = true) == true
                }

                if (filteredList != null) {
                    items(filteredList.size) { index ->
                        val yemek = filteredList[index]
                        var isLiked by remember { mutableStateOf(false) }

                        Card(
                            modifier = Modifier
                                .padding(all = 5.dp)
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(all = 20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    GlideImage(
                                        imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(3f / 4f)
                                    )

                                    Spacer(modifier = Modifier.size(1.dp))

                                    Text(
                                        text = yemek.yemek_adi,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.size(5.dp))

                                    Text(
                                        text = "Ücretsiz Gönderim",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.size(12.dp))
                                }

                                Icon(
                                    painter = painterResource(id = R.drawable.begen),
                                    contentDescription = "Beğen",
                                    tint = if (isLiked) Color.Red else Color.Black,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                        .clickable { isLiked = !isLiked }
                                )

                                Text(
                                    text = "₺ ${yemek.yemek_fiyat}",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(8.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .border(2.dp, Color.Black)
                                        .align(Alignment.BottomEnd)
                                        .padding(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ekle_resim),
                                        contentDescription = "Ekle",
                                        modifier = Modifier
                                            .size(32.dp)
                                            .align(Alignment.Center)
                                            .clickable {
                                                val yemekJson = Gson().toJson(yemek)
                                                navController.navigate("detay_sayfa/$yemekJson")
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

