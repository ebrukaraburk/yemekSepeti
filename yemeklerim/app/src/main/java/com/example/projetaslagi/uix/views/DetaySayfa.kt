package com.example.projetaslagi.views

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projetaslagi.R
import com.example.projetaslagi.data.entity.Yemekler
import com.example.projetaslagi.uix.viewmodel.DetaySayfaViewModel
import com.skydoves.landscapist.glide.GlideImage
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetaySayfa(
    navController: NavController,
    gelenYemek: Yemekler,
    detaySayfaViewModel: DetaySayfaViewModel = viewModel()
) {
    val miktar by detaySayfaViewModel.miktar.observeAsState(1)
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val toplamFiyat = remember(miktar) { detaySayfaViewModel.toplamFiyat(gelenYemek.yemek_fiyat) }

    Scaffold(
        containerColor = Color.White,

        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detaylar") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("anasayfa") }) {
                        Icon(Icons.Default.Close, contentDescription = "Geri")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.anaRenk),
                    titleContentColor = colorResource(id = R.color.white)
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp)) // Yıldızları daha aşağı almak için boşluk ekledik

            // Derecelendirme yıldızlarını göster
            Text(text = "★★★★☆", fontSize = 20.sp)

            Spacer(modifier = Modifier.height(8.dp))

            // Yemek resmini göster
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f)
                    .padding(top = 16.dp)
            ) {
                GlideImage(
                    imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Yemek adını göster
            Text(
                text = gelenYemek.yemek_adi,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Yemek fiyatını göster
            Text(
                text = " ₺${gelenYemek.yemek_fiyat}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))




            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { detaySayfaViewModel.miktarAzalt() },
                    enabled = miktar > 1,
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.anaRenk))
                ) {
                    Text(text = "-", color = colorResource(id = R.color.white))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = miktar.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { detaySayfaViewModel.miktarArtir() },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.anaRenk))
                ) {
                    Text(text = "+", color = colorResource(id = R.color.white))
                }
            }
            Spacer(modifier = Modifier.width(36.dp))

            // İndirim ve teslimat bilgilerini göster
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "%30 İndirim", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = "Ücretsiz Teslimat", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = "Hızlı Teslimat", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Yemek miktarı arttırma/azaltma butonları


            Spacer(modifier = Modifier.height(8.dp))

            // Toplam Fiyatı ve Sepete Ekle Butonu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "           ₺${toplamFiyat}        ",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = {
                        detaySayfaViewModel.sepeteEkle(
                            yemek_adi = gelenYemek.yemek_adi,
                            yemek_resim_adi = gelenYemek.yemek_resim_adi,
                            yemek_fiyat = gelenYemek.yemek_fiyat,
                            yemek_siparis_adet = miktar,
                            kullanici_adi = "ebru" // Kullanıcı adı
                        )

                        navController.navigate("sepet_sayfasi/${"ebru"}") {


                            popUpTo("detay_sayfasi") { inclusive = true }
                        }

                        Toast.makeText(context, "Sepete eklendi!", Toast.LENGTH_LONG).show()
                    },
                    modifier = Modifier
                        .weight(0.9f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.anaRenk)),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Text("Sepete Ekle", color = colorResource(id = R.color.white))
                }
            }
        }
    }
}
