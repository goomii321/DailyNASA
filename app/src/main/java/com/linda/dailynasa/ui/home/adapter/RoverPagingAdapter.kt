package com.linda.dailynasa.ui.home.adapter

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.linda.dailynasa.R
import com.linda.dailynasa.data.remote.dto.Photo
import com.linda.dailynasa.databinding.ItemRoverRecyclerViewBinding


class RoverPagingAdapter(val context:Context,private val onClickListener:OnClickListener) :
    PagingDataAdapter<Photo, RoverPagingAdapter.RoverViewHolder>(DiffCallback()) {

    class OnClickListener(private val clickListener: (data:Photo) -> Unit) {
        fun onClick(data: Photo) = clickListener(data)
    }

    inner class RoverViewHolder(
        private val binding: ItemRoverRecyclerViewBinding,
        private val clickListener: OnClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.roverImage
        fun bind(photo: Photo) {
            binding.roverEarthDate.text = photo.earth_date

            val uri = photo.img_src.toUri().buildUpon().build()
            binding.roverImage.let {
                Glide.with(it.context)
                    .load(uri).apply(
                        RequestOptions()
                            .placeholder(R.drawable.refresh_48px)
                            .error(R.drawable.broken_image_48px)
                    ).into(it)
            }

            binding.root.setOnClickListener {
                clickListener.onClick(photo)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoverViewHolder {
        return RoverViewHolder(
            ItemRoverRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),onClickListener
        )
    }

    override fun onBindViewHolder(holder: RoverViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.bind(repoItem)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val devicewidth = wm.currentWindowMetrics.bounds.width() / 2
                holder.image.layoutParams.width = devicewidth
            } else {
                val displaymetrics = DisplayMetrics()
                (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
                val devicewidth = displaymetrics.widthPixels / 2

                holder.image.layoutParams.width = devicewidth

            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem == newItem

    }
}