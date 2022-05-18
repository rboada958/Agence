package com.app.appagence.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.appagence.app.model.Product
import com.app.appagence.databinding.ProductItemLayoutBinding

class HomeAdapter(
    var items: MutableList<Product>,
    private val listener: OnHomeClickListener? = null
) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(
        private val binding: ProductItemLayoutBinding,
        private val listener: OnHomeClickListener? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.itemHolder = HomeBindingViewHolder(item) {
                listener?.onProductClicked(it, adapterPosition)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) =
        holder.bind(items[position])


    override fun getItemCount(): Int =
        items.size

    fun addItems(list: List<Product>) {
        val positionStart = items.size
        items.addAll(list)
        notifyItemRangeInserted(positionStart, items.size)
    }

    interface OnHomeClickListener {
        fun onProductClicked(item: Product, position: Int)
    }
}