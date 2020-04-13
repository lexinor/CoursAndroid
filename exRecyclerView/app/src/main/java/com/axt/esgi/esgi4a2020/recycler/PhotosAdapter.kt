package com.axt.esgi.esgi4a2020.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.axt.esgi.esgi4a2020.R
import com.axt.esgi.esgi4a2020.data.model.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    var data: List<Photo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: ((Photo) -> Unit)? = null

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = data[position]

        with(holder) {
            titleTv.text = photo.title
            Glide.with(itemView)
                .load(photo.url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(thumbnailImv)

            itemView.setOnClickListener { listener?.invoke(photo) }
        }
    }

    fun removeItem(position: Int){
        val oldList = data
        val newList = data.toMutableList()
        newList.removeAt(position)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            PhotoItemDiffCallBack(
                oldList,
                newList
            )
        )

        data = newList
        diffResult.dispatchUpdatesTo(this)
    }


    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbnailImv: ImageView = itemView.findViewById(R.id.item_photo_imv)
        var titleTv: TextView = itemView.findViewById(R.id.item_photo_tv)
    }

    class PhotoItemDiffCallBack(var oldPhotoList: List<Photo>, var newPhotoList: List<Photo>): DiffUtil.Callback(){
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldPhotoList.get(oldItemPosition).id == newPhotoList.get(newItemPosition).id)
        }

        override fun getOldListSize(): Int {
            return oldPhotoList.size
        }

        override fun getNewListSize(): Int {
            return newPhotoList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPhotoList.get(oldItemPosition).equals(newPhotoList.get(newItemPosition))
        }

    }
}