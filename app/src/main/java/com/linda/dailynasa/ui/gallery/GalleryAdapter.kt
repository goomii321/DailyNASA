package com.linda.dailynasa.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.linda.dailynasa.R
import com.linda.dailynasa.databinding.ItemGalleryRecyclerViewBinding
import com.linda.dailynasa.domain.model.Favorite

class GalleryAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Favorite, GalleryAdapter.GalleryViewHolder>(DiffCallback()) {

    class OnClickListener(private val clickListener: (data: Favorite) -> Unit) {
        fun onClick(data: Favorite) = clickListener(data)
    }

    class DiffCallback : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }

    }

    inner class GalleryViewHolder(
        private val binding: ItemGalleryRecyclerViewBinding,
        private val onClickListener: OnClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Favorite) {
            binding.galleryNameText.text = data.name

            val uri = data.imgUrl.toUri().buildUpon().build()
            binding.galleryImage.let {
                Glide.with(it.context)
                    .load(uri).apply (
                        RequestOptions()
                            .placeholder(R.drawable.refresh_48px)
                            .error(R.drawable.broken_image_48px)
                    ).dontTransform().into(it)
            }

            binding.root.setOnClickListener {
                onClickListener.onClick(data)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            ItemGalleryRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickListener
        )
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        return holder.bind(getItem(position) as Favorite)
    }
}