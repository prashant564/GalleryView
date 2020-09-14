package com.example.gallerytest.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerytest.R
import kotlinx.android.synthetic.main.item_all_images_layout.view.*


class GalleryPhotoAdapter(var list: ArrayList<String>): RecyclerView.Adapter<GalleryPhotoAdapter.GalleryImagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryImagesViewHolder {
        return GalleryImagesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GalleryImagesViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class GalleryImagesViewHolder private constructor(var view: View): RecyclerView.ViewHolder(view){
        fun bind(item: String){
            view.iv_photo.setImageURI(Uri.parse("file://$item"))
        }
        companion object{
            fun from(parent: ViewGroup): GalleryImagesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_all_images_layout, parent, false)
                return GalleryImagesViewHolder(view)
            }
        }
    }
}