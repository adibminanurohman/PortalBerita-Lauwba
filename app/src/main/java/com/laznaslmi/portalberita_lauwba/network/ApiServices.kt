package com.laznaslmi.portalberita_lauwba.network

import com.laznaslmi.portalberita_lauwba.model.ResponseDetailBerita
import com.laznaslmi.portalberita_lauwba.model.ResponseListBerita
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Interface untuk mendefinisikan endpoints API
interface ApiServices {

    // Mendapatkan daftar berita terbaru dari API
    @GET("webservices/get_latest_news")
    fun getLatestNews(): retrofit2.Call<ResponseListBerita>

    // Mendapatkan detail berita berdasarkan ID dari API
    @GET("webservices/get_detail_news/{id}")
    fun getDetailNews(@Path("id") id: String?): retrofit2.Call<ResponseDetailBerita>

    // Mencari berita berdasarkan istilah tertentu dari API
    @GET("webservices/search_news")
    fun searchNews(@Query("q") terms: String?): retrofit2.Call<ResponseListBerita>
}
