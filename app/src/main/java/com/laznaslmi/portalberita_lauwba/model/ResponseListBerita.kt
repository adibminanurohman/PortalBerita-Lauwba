package com.laznaslmi.portalberita_lauwba.model

import com.google.gson.annotations.SerializedName

// Model data untuk menangani respon daftar berita dari API
data class ResponseListBerita(

	// Daftar berita yang diterima dari API
	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	// Kategori dari daftar berita
	@field:SerializedName("kategori")
	val kategori: String? = null,

	// Pesan yang dihasilkan oleh respon API
	@field:SerializedName("message")
	val message: String? = null,

	// Kode error, jika terjadi kesalahan pada respon
	@field:SerializedName("error")
	val error: Int? = null
)

// Model data untuk setiap item berita dalam daftar berita
data class DataItem(

	// Tanggal posting berita
	@field:SerializedName("post_on")
	val postOn: String? = null,

	// Judul berita
	@field:SerializedName("jdl_news")
	val jdlNews: String? = null,

	// Judul SEO (Search Engine Optimization) berita
	@field:SerializedName("judul_seo")
	val judulSeo: String? = null,

	// ID berita
	@field:SerializedName("id")
	val id: String? = null,

	// Nama kategori berita
	@field:SerializedName("nama_kategori")
	val namaKategori: Any? = null, // Tipe data Any digunakan karena tidak jelas tipe data kategori yang diharapkan

	// URL foto berita
	@field:SerializedName("foto_news")
	val fotoNews: String? = null
)
