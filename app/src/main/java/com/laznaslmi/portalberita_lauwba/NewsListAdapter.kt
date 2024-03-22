package com.laznaslmi.portalberita_lauwba

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laznaslmi.portalberita_lauwba.databinding.ItemNewsListAdapterBinding
import com.laznaslmi.portalberita_lauwba.model.DataItem

// Adapter untuk RecyclerView yang menampilkan daftar berita
class NewsListAdapter (
    private val listNews: List<DataItem?>?, // Daftar berita yang akan ditampilkan
    private val onListItemClick: (DataItem?) -> Unit // Callback untuk menangani klik pada item berita
) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>()
{
    // ViewHolder untuk setiap item dalam RecyclerView
    inner class ViewHolder(val itemNewsListAdapterBinding: ItemNewsListAdapterBinding): RecyclerView.ViewHolder(itemNewsListAdapterBinding.root){
        // Mengikat data ke tampilan item
        fun onBindItem(dataItem: DataItem?){
            // Set tanggal posting
            itemNewsListAdapterBinding.datePost.text = dataItem?.postOn
            // Set judul berita
            itemNewsListAdapterBinding.judul.text = dataItem?.jdlNews
            // Memuat gambar berita menggunakan Glide
            Glide.with(itemNewsListAdapterBinding.root.context)
                .load(dataItem?.fotoNews)
                .into(itemNewsListAdapterBinding.fotoBerita)
            // Menangani klik pada item berita
            itemNewsListAdapterBinding.root.setOnClickListener {
                onListItemClick(dataItem)
            }
        }
    }

    // Membuat ViewHolder baru saat RecyclerView memerlukan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Menghubungkan layout item dengan ViewHolder menggunakan ViewBinding
        val binding = ItemNewsListAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    // Memasang data ke ViewHolder saat RecyclerView menggulir
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(listNews?.get(position))
    }

    // Mendapatkan jumlah item dalam daftar berita
    override fun getItemCount(): Int {
        return listNews?.size ?: 0
    }
}
