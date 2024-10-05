package com.example.projetaslagi.data.datasource

import android.annotation.SuppressLint
import android.util.Log
import com.example.projetaslagi.data.entity.CRUDCevap
import com.example.projetaslagi.data.entity.Sepet
import com.example.projetaslagi.data.entity.Yemekler
import com.example.projetaslagi.retrofit.YemeklerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class YemeklerDataSource(var ydao: YemeklerDao) {




    suspend fun yemekleriYukle() : List<Yemekler> = withContext(Dispatchers.IO){
        return@withContext ydao.yemekleriYukle().yemekler
    }


    suspend fun ekle(yemek_adi:String, yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kullanici_adi:String){
        ydao.ekle(yemek_adi, yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }



    @SuppressLint("SuspiciousIndentation")
    suspend fun ara(aramaKelimesi: String): List<Sepet> = withContext(Dispatchers.IO) {
        return@withContext try {
            val degis = ydao.ara(aramaKelimesi)
            degis.sepet_yemekler
        } catch (e: Exception) {
            Log.e("arama hatası", e.toString())
            emptyList() // Hata durumunda boş bir liste döner
        }
    }

    suspend fun sil(sepet_yemek_id: Int, kullanici_adi: String): Result<Unit> {
        return try {
            ydao.sil(sepet_yemek_id, kullanici_adi)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("yemek silme hatasi", e.toString())
            Result.failure(e)
        }
    }





}