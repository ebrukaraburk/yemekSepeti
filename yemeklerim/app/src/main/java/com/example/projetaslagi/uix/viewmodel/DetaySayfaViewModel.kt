package com.example.projetaslagi.uix.viewmodel






import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projetaslagi.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetaySayfaViewModel @Inject constructor(private val yrepo: YemeklerRepository) : ViewModel() {
    // Yemek miktarını tutan LiveData



    val miktar = MutableLiveData(1)


    fun toplamFiyat(yemekFiyati: Int): Int {
        return miktar.value?.let { it * yemekFiyati } ?: 0
    }


    fun miktarArtir() {
        miktar.value = (miktar.value ?: 0) + 1
    }


    fun miktarAzalt() {
        if ((miktar.value ?: 0) > 1) {
            miktar.value = (miktar.value ?: 0) - 1
        }
    }


    fun sepeteEkle(yemek_adi: String, yemek_resim_adi: String, yemek_fiyat: Int, yemek_siparis_adet: Int, kullanici_adi: String) {
        viewModelScope.launch {
            yrepo.ekle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
        }
    }
}