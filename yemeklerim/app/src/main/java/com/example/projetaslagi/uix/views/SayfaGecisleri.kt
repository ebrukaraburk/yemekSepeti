package com.example.projetaslagi.uix.views

import Anasayfa
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projetaslagi.data.entity.Yemekler
import com.example.projetaslagi.uix.viewmodel.AnasayfaViewModel
import com.example.projetaslagi.uix.viewmodel.DetaySayfaViewModel
import com.example.projetaslagi.uix.viewmodel.SepetViewModel
import com.example.projetaslagi.views.DetaySayfa
import com.google.gson.Gson

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun SayfaGecisleri(
    anasayfaViewModel: AnasayfaViewModel,
    detaySayfaViewModel: DetaySayfaViewModel,
    sepetViewModel: SepetViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "anasayfa") {
        composable("anasayfa") {
            Anasayfa(navController, anasayfaViewModel)


        }
        composable(
            "detay_sayfa/{yemek}",
            arguments = listOf(navArgument("yemek") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("yemek")
            val yemek = Gson().fromJson(json, Yemekler::class.java)

            DetaySayfa(
                navController = navController,
                gelenYemek = yemek,
                detaySayfaViewModel = detaySayfaViewModel
            )
        }


        composable("sepet_sayfasi/{kullaniciAdi}") { backStackEntry ->
            val kullaniciAdi = backStackEntry.arguments?.getString("kullaniciAdi")
            if (!kullaniciAdi.isNullOrEmpty()) {
                SepetSayfa(sepetViewModel, kullaniciAdi, navController)
            } else {

                Log.e("Navigasyon Hatası", "Kullanıcı adı boş veya mevcut değil.")

            }
        }






    }
}
