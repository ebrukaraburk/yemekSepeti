package com.example.projetaslagi


import Anasayfa
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


import com.example.projetaslagi.ui.theme.ProjeTaslagiTheme
import com.example.projetaslagi.uix.viewmodel.AnasayfaViewModel
import com.example.projetaslagi.uix.viewmodel.DetaySayfaViewModel
import com.example.projetaslagi.uix.viewmodel.SepetViewModel
import com.example.projetaslagi.uix.views.SayfaGecisleri
import com.example.projetaslagi.uix.views.SepetSayfa
import com.example.projetaslagi.views.DetaySayfa
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjeTaslagiTheme {
                val anasayfaViewModel: AnasayfaViewModel = viewModel()
                val detaySayfaViewModel: DetaySayfaViewModel = viewModel()
                val sepetViewModel: SepetViewModel = viewModel()

                SayfaGecisleri(
                    anasayfaViewModel,
                    detaySayfaViewModel,
                    sepetViewModel)


            }
        }
    }
}


