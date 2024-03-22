package com.laznaslmi.portalberita_lauwba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.laznaslmi.portalberita_lauwba.databinding.ActivityMainBinding
import com.laznaslmi.portalberita_lauwba.model.DataItem
import com.laznaslmi.portalberita_lauwba.model.ResponseListBerita
import com.laznaslmi.portalberita_lauwba.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menginisialisasi binding menggunakan ActivityMainBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Memanggil fungsi untuk mendapatkan berita terbaru
        getLatestNews()
    }

    // Mendapatkan berita terbaru dari server menggunakan Retrofit
    private fun getLatestNews() {
        // Panggil Retrofit untuk mendapatkan layanan (service)
        NetworkConfig().getService()
            .getLatestNews()
            .enqueue(object : Callback<ResponseListBerita> {
                override fun onResponse(
                    call: Call<ResponseListBerita>,
                    response: Response<ResponseListBerita>
                ) {
                    // Sembunyikan ProgressBar setelah mendapatkan respon
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    if (response.isSuccessful) {
                        val receivedDatas = response.body()?.data
                        // Mengatur data yang diterima ke dalam adapter RecyclerView
                        setToAdapter(receivedDatas)
                    }
                }

                override fun onFailure(call: Call<ResponseListBerita>, t: Throwable) {
                    // Sembunyikan ProgressBar jika terjadi kesalahan saat panggilan API
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    // Tampilkan pesan kesalahan menggunakan Log
                    Log.d("Retrofit: onFailure: ", "onFailure: ${t.stackTrace}")
                }
            })
    }

    // Mengatur data berita ke dalam adapter RecyclerView
    private fun setToAdapter(receivedDatas: List<DataItem?>?) {
        // Reset adapter RecyclerView terlebih dahulu
        this.binding.newsList.adapter = null

        // Inisialisasi adapter RecyclerView dengan data yang diterima
        val adapter = NewsListAdapter(receivedDatas) {
            // Mendefinisikan intent untuk navigasi ke DetailNewsActivity
            val detailNewsIntent = Intent(this@MainActivity, DetailNewsActivity::class.java)
            detailNewsIntent.putExtra("idNews", it?.id)
            detailNewsIntent.putExtra("judulSeo", it?.judulSeo)
            startActivity(detailNewsIntent)
        }
        // Mengatur layout manager dan animator untuk RecyclerView
        val lm = LinearLayoutManager(this)
        this.binding.newsList.layoutManager = lm
        this.binding.newsList.itemAnimator = DefaultItemAnimator()
        this.binding.newsList.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate menu layout
        menuInflater.inflate(R.menu.menu, menu)
        // Dapatkan item pencarian dari menu
        val searchItem = menu?.findItem(R.id.app_bar_search)
        // Dapatkan SearchView dari item pencarian
        val searchView: SearchView = searchItem?.actionView as SearchView
        // Atur listener untuk menghandle event pencarian
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Panggil fungsi pencarian berita
                searchNews(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Tidak melakukan apa-apa saat teks berubah
                return true
            }
        })
        return true
    }

    // Fungsi untuk mencari berita berdasarkan kata kunci
    private fun searchNews(query: String?) {
        // Tampilkan ProgressBar saat pencarian berlangsung
        this.binding.progressIndicator.visibility = View.VISIBLE
        // Panggil Retrofit untuk melakukan pencarian berita
        NetworkConfig()
            .getService()
            .searchNews(query)
            .enqueue(object : Callback<ResponseListBerita> {
                override fun onResponse(
                    call: Call<ResponseListBerita>,
                    response: Response<ResponseListBerita>
                ) {
                    // Sembunyikan ProgressBar setelah mendapatkan respon
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    if (response.isSuccessful) {
                        val receivedDatas = response.body()?.data
                        // Mengatur data yang diterima ke dalam adapter RecyclerView
                        setToAdapter(receivedDatas)
                    }
                }

                override fun onFailure(call: Call<ResponseListBerita>, t: Throwable) {
                    // Sembunyikan ProgressBar jika terjadi kesalahan saat panggilan API
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    // Tampilkan pesan kesalahan menggunakan Log
                    Log.d("Retrofit: onFailure: ", "onFailure: ${t.stackTrace}")
                }
            })
    }
}
