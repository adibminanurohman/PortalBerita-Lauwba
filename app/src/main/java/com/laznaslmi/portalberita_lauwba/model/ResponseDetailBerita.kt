package com.laznaslmi.portalberita_lauwba.model

import com.google.gson.annotations.SerializedName

// Model data untuk menangani respon detail berita dari API
data class ResponseDetailBerita(

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

	// Keterangan berita
	@field:SerializedName("ket_news")
	val ketNews: String? = null,

	// Pesan yang dihasilkan oleh respon API
	@field:SerializedName("message")
	val message: String? = null,

	// Kode error, jika terjadi kesalahan pada respon
	@field:SerializedName("error")
	val error: Int? = null,

	// Nama kategori berita
	@field:SerializedName("nama_kategori")
	val namaKategori: String? = null,

	// URL foto berita
	@field:SerializedName("foto_news")
	val fotoNews: String? = null
)
