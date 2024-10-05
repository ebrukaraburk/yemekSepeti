package com.example.projetaslagi.data.repo

import com.example.projetaslagi.data.datasource.YemeklerDataSource
import com.example.projetaslagi.data.entity.CRUDCevap
import com.example.projetaslagi.data.entity.Sepet
import com.example.projetaslagi.data.entity.SepetCevap
import com.example.projetaslagi.data.entity.Yemekler
import retrofit2.Response

class YemeklerRepository(var yds: YemeklerDataSource) {

    suspend fun sil(sepet_yemek_id:Int, kullanici_adi: String) = yds.sil(sepet_yemek_id, kullanici_adi)

    suspend fun yemekleriYukle(): List<Yemekler> = yds.yemekleriYukle()
    suspend fun ekle(yemek_adi: String, yemek_resim_adi: String, yemek_fiyat: Int, yemek_siparis_adet: Int, kullanici_adi: String) =
        yds.ekle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)

    suspend fun ara(kullaniciAdi: String): List<Sepet> {
        return yds.ara(kullaniciAdi)
    }


}
