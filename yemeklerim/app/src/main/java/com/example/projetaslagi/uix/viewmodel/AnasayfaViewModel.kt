package com.example.projetaslagi.uix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetaslagi.data.entity.Yemekler
import com.example.projetaslagi.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnasayfaViewModel @Inject constructor(private val yrepo: YemeklerRepository) : ViewModel() {
    var yemeklerListesi = MutableLiveData<List<Yemekler>>()

    private val _kullaniciAdi = MutableLiveData<String>()
    val kullaniciAdi: LiveData<String> get() = _kullaniciAdi

    init {
        yemekleriYukle()
    }


    fun setKullaniciAdi(ad: String) {
        CoroutineScope(Dispatchers.Main).launch {
            _kullaniciAdi.value = ad
        }
    }



    private fun yemekleriYukle() {
        CoroutineScope(Dispatchers.Main).launch {
            yemeklerListesi.value = yrepo.yemekleriYukle()
        }
    }
}
