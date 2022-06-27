package com.linda.dailynasa.ui.home.adapter

//class RoverAdapter(private val viewModel: HomeViewModel) :
//    ListAdapter<Photo, RoverAdapter.RoverViewHolder>(DiffCallback()) {
//
//    class DiffCallback : DiffUtil.ItemCallback<Photo>() {
//        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
//            return oldItem == newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//
//    inner class RoverViewHolder(
//        private val binding: ItemRoverRecyclerViewBinding,
//        private val viewModel: HomeViewModel
//    ) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(photo: Photo) {
//            binding.roverEarthDate.text = photo.earth_date
//
//            val uri = photo.img_src.toUri().buildUpon().build()
//            binding.roverImage.let {
//                Glide.with(it.context)
//                    .load(uri).apply (
//                        RequestOptions()
//                            .placeholder(R.drawable.refresh_48px)
//                            .error(R.drawable.broken_image_48px)
//                    ).into(it)
//            }
//
//            binding.executePendingBindings()
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoverViewHolder {
//        return RoverViewHolder(
//            ItemRoverRecyclerViewBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            ), viewModel
//        )
//    }
//
//    override fun onBindViewHolder(holder: RoverViewHolder, position: Int) {
//        return holder.bind(getItem(position) as Photo)
//    }
//}