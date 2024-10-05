package com.example.projetaslagi.retrofit

import com.example.projetaslagi.data.entity.CRUDCevap
import com.example.projetaslagi.data.entity.Sepet
import com.example.projetaslagi.data.entity.SepetCevap
import com.example.projetaslagi.data.entity.YemeklerCevap
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface YemeklerDao {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun yemekleriYukle() : YemeklerCevap




    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun ekle(@Field("yemek_adi") yemek_adi:String,
                     @Field("yemek_resim_adi") yemek_resim_adi:String,
                     @Field("yemek_fiyat") yemek_fiyat:Int,
                     @Field("yemek_siparis_adet") yemek_siparis_adet:Int,
                     @Field("kullanici_adi") kullanici_adi:String
    ) : CRUDCevap


    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun ara(@Field("kullanici_adi") aramaKelimesi:String) : SepetCevap

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun sil(@Field("sepet_yemek_id") sepet_yemek_id:Int,
                    @Field("kullanici_adi") kullanici_adi:String):Response <CRUDCevap>


    // http://kasimadalan.pe.hu/yemekler/sepettenYemekSil.php



}