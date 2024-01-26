package dev.arch3rtemp.tvshows.presentation.commonui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<M, VB : ViewBinding>(viewBinding: VB) :
    RecyclerView.ViewHolder(viewBinding.root) {
    private var item: M? = null

    fun doBindings(data: M?) {
        this.item = data
    }

    abstract fun bind()

    fun getRowItem(): M? {
        return item
    }
}