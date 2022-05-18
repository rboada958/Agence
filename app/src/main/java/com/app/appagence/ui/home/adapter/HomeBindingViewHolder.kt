package com.app.appagence.ui.home.adapter

import com.app.appagence.R
import com.app.appagence.app.model.Product
import com.app.appagence.utils.adapter.ItemViewHolder

class HomeBindingViewHolder(val product: Product, val onClick : ((Product) -> Unit)? = null)  : ItemViewHolder {
    override val layoutId: Int = R.layout.product_item_layout
    override val viewType: Int = -3
}