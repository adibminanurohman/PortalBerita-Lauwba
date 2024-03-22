package com.laznaslmi.portalberita_lauwba

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import com.laznaslmi.portalberita_lauwba.databinding.ActivityDetailNewsBinding
import com.laznaslmi.portalberita_lauwba.model.ResponseDetailBerita
import com.laznaslmi.portalberita_lauwba.network.NetworkConfig
import retrofit2.Call
import retrofit2.Response

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNewsBinding
    private var urlSeo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set judul halaman
        supportActionBar?.title = "Detail Berita"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Mendapatkan data yang diterima dari intent
        val idNews = intent.getStringExtra("idNews")
        urlSeo = intent.getStringExtra("judulSeo")

        // Memanggil fungsi untuk mendapatkan detail berita
        getDetailNews(idNews)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Menambahkan menu untuk membuka halaman berita pada browser
        menu?.add(0, 0, 0, "View On Browser")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            0 -> openInBrowser() // Menangani klik pada menu untuk membuka halaman berita pada browser
        }
        return super.onOptionsItemSelected(item)
    }

    // Fungsi untuk membuka halaman berita pada browser menggunakan CustomTabsIntent
    private fun openInBrowser() {
        val initialUrl = NetworkConfig().BASE_URL + "reader/" + this.urlSeo
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(initialUrl))
    }

    // Fungsi untuk memanggil API dan mendapatkan detail berita
    private fun getDetailNews(idNews: String?) {
        NetworkConfig().getService()
            .getDetailNews(idNews)
            .enqueue(object : retrofit2.Callback<ResponseDetailBerita> {
                override fun onResponse(
                    call: Call<ResponseDetailBerita>,
                    response: Response<ResponseDetailBerita>
                ) {
                    // Sembunyikan ProgressBar setelah mendapatkan respon
                    this@DetailNewsActivity.binding.progressIndicator.visibility = View.GONE
                    if (response.isSuccessful) {
                        val detailData = response.body()
                        // Set data ke tampilan
                        setToView(detailData)
                    }
                }

                override fun onFailure(call: Call<ResponseDetailBerita>, t: Throwable) {
                    // Sembunyikan ProgressBar jika terjadi kesalahan
                    this@DetailNewsActivity.binding.progressIndicator.visibility = View.GONE
                    // Tampilkan pesan kesalahan menggunakan Toast
                    t.printStackTrace()
                    Toast.makeText(this@DetailNewsActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    // Fungsi untuk menetapkan data detail berita ke tampilan
    private fun setToView(detailData: ResponseDetailBerita?) {
        this.binding.judul.text = detailData?.jdlNews
        this.binding.isi.text = detailData?.ketNews
        Glide.with(this)
            .load(detailData?.fotoNews)
            .into(this.binding.coverBerita)
    }
}
