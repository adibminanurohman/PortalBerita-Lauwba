package com.laznaslmi.portalberita_lauwba.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Kelas untuk konfigurasi jaringan Retrofit
class NetworkConfig {
    // Base URL dari API
    val BASE_URL: String = "https://lauwba.com/"

    // Fungsi untuk mengatur OkHttpClient dengan logging interceptor dan timeout
    private fun setOkHttp(): OkHttpClient {
        // Membuat interceptor untuk logging HTTP requests dan responses
        val interceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

        // Konfigurasi OkHttpClient
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(15L, TimeUnit.SECONDS) // Set timeout koneksi ke 15 detik
            .build()
    }

    // Fungsi untuk mengatur Retrofit dengan base URL dan konverter Gson
    private fun setRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Menggunakan GsonConverterFactory untuk parsing JSON
            .client(setOkHttp()) // Menggunakan OkHttpClient yang sudah dikonfigurasi
            .build()
    }

    // Fungsi untuk mendapatkan instance dari ApiService
    fun getService(): ApiServices {
        return setRetrofit().create(ApiServices::class.java)
    }
}
