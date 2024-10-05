package com.example.projetaslagi.uix.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projetaslagi.R
import com.example.projetaslagi.uix.viewmodel.SepetViewModel
import com.example.projetaslagi.data.entity.Sepet
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SepetSayfa(
    sepetViewModel: SepetViewModel,
    kullaniciAdi: String,
    navController: NavController,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val sepetListesi = sepetViewModel.sepetListesi.observeAsState(emptyList()).value
    val coroutineScope = rememberCoroutineScope()



    LaunchedEffect(kullaniciAdi) {
        sepetViewModel.ara(kullaniciAdi)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sepetim") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.Close, contentDescription = "Geri")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.anaRenk),
                    titleContentColor = colorResource(id = R.color.white)
                )
            )
        },
        bottomBar = {

            BottomAppBar {
                val toplamFiyat = sepetListesi.sumOf { it.yemek_fiyat * it.yemek_siparis_adet }
                Text(
                    text = "        Toplam Sipariş Fiyatı: ₺$toplamFiyat",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium
                )
                Button(
                    onClick = {
                        coroutineScope.launch {
                            sepetViewModel.sepetiTemizle(kullaniciAdi) // Sepeti temizle
                            navController.navigate("anaSayfa") // Ana sayfaya yönlendirme

                            snackbarHostState.showSnackbar("Sepetiniz onaylanıyor......") // Snackbar mesajı
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor  = colorResource(id = R.color.anaRenk)) // Buton rengi
                ) {
                    Text(text = "Sepeti Onayla", color = colorResource(id = R.color.white)) // Yazı rengi
                }

            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
                containerColor = colorResource(id = R.color.white)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Ekranda gösterilecek UI
            // Ekranda gösterilecek UI
            if (sepetListesi.isNullOrEmpty()) {
                // Sepet boşsa
                Text(
                    text = "Sepetiniz boş.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Ortalamak için
                )
            } else {
                // Sepet doluysa yemekleri göster
                LazyColumn {
                    items(sepetListesi) { yemek ->
                        SepetItem(
                            yemek = yemek,
                            sepetViewModel = sepetViewModel,
                            kullaniciAdi = kullaniciAdi
                        )
                    }
                }
            }


        }
    }
}

@Composable
fun SepetItem(yemek: Sepet, sepetViewModel: SepetViewModel, kullaniciAdi: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                GlideImage(
                    imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 16.dp)
                )
                Column(verticalArrangement = Arrangement.Center) {
                    Text(text = yemek.yemek_adi, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Fiyat: ₺${yemek.yemek_fiyat}", fontSize = 16.sp)
                    Text(text = "Adet: ${yemek.yemek_siparis_adet}", fontSize = 14.sp)
                    Text(text = "Toplam: ₺${yemek.yemek_fiyat * yemek.yemek_siparis_adet}", fontSize = 14.sp)
                }
            }

            IconButton(
                onClick = {
                    sepetViewModel.sil(yemek.sepet_yemek_id, kullaniciAdi)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Sil",
                    tint = colorResource(id = R.color.anaRenk)
                )
            }

        }
}}

