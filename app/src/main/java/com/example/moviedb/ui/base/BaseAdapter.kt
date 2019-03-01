package com.example.moviedb.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviedb.BR
import java.util.concurrent.Executors

abstract class BaseAdapter<Item, ViewBinding : ViewDataBinding>(callBack: DiffUtil.ItemCallback<Item>) :
    ListAdapter<Item, BaseViewHolder<ViewBinding>>(
        AsyncDifferConfig.Builder<Item>(callBack)
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
    ) {

    lateinit var listener: ItemClickListener

    override fun submitList(list: List<Item>?) {
        super.submitList(ArrayList<Item>(list ?: listOf()))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(
            DataBindingUtil.inflate<ViewBinding>(
                LayoutInflater.from(parent.context),
                getLayout(viewType), parent, false
            )
        )
    }

    protected abstract fun getLayout(viewType: Int): Int

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        try {
            val item: Item = getItem(position)
            holder.binding.setVariable(BR.item, item)
            bindView(holder.binding, item, position)
        } catch (e: IndexOutOfBoundsException) {
            bind(holder.binding, position)
        }
        holder.itemView.setOnClickListener({ listener.onItemClick(position) })
        holder.binding.executePendingBindings()
    }

    fun setOnItemClick(itemClickListener: ItemClickListener) {
        listener = itemClickListener
    }

    protected open fun bindView(binding: ViewBinding, item: Item, position: Int) {}

    protected open fun bind(binding: ViewBinding, position: Int) {}
}

interface ItemClickListener {
    fun onItemClick(position: Int)
}
