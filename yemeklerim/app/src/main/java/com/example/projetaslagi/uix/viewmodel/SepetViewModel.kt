package com.example.projetaslagi.uix.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetaslagi.data.entity.Sepet
import com.example.projetaslagi.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SepetViewModel @Inject constructor(
    private val yrepo: YemeklerRepository
) : ViewModel() {

    val sepetListesi = MutableLiveData<List<Sepet>>()


    fun sil(sepet_yemek_id: Int, kullaniciAdi: String) {
        viewModelScope.launch {
            try {
                yrepo.sil(sepet_yemek_id, kullaniciAdi) // Yemek silme işlemi
                ara(kullaniciAdi) // Sepeti güncelle
            } catch (e: Exception) {
                Log.e("Silme Hatası", "Hata: ${e.message}")
            }
        }
    }



    fun ara(kullaniciAdi: String) {
        viewModelScope.launch {
            try {
                val sepetYemekleri = yrepo.ara(kullaniciAdi) // Kullanıcıya ait yemekleri ara
                if (sepetYemekleri.isEmpty()) {
                    sepetListesi.value = emptyList() // Sepet boşsa güncelle
                    Log.d("Sepet", "Sepetiniz boş.")
                } else {
                    sepetListesi.value = grupYemekleri(sepetYemekleri) // Yemekleri gruplandır
                }
            } catch (e: Exception) {
                Log.e("Arama Hatası", "Hata: ${e.message}") // Hata loglama
                e.printStackTrace() // Hata yığınını konsola yazdır
            }
        }
    }






    private fun grupYemekleri(sepetYemekleri: List<Sepet>): List<Sepet> {

        return sepetYemekleri.groupBy { it.yemek_adi }
            .map { entry ->
                val yemek = entry.value.first()
                val toplamAdet = entry.value.sumOf { it.yemek_siparis_adet }
                yemek.copy(yemek_siparis_adet = toplamAdet)
            }
    }



    fun sepetiTemizle(kullaniciAdi: String) {
        viewModelScope.launch {
            try {
                val sepetYemekleri = yrepo.ara(kullaniciAdi)
                // Her bir ürünü sil
                for (yemek in sepetYemekleri) {
                    yrepo.sil(yemek.sepet_yemek_id, kullaniciAdi)
                }
                // Tüm silme işlemleri tamamlandıktan sonra sepeti yeniden güncelle
                ara(kullaniciAdi)
            } catch (e: Exception) {
                Log.e("Temizleme Hatası", "Hata: ${e.message}")
            }
        }
    }


}

