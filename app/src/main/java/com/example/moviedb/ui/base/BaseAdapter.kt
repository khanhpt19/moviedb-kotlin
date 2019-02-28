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

abstract class BaseAdapter<Item, viewBinding : ViewDataBinding>(callBack: DiffUtil.ItemCallback<Item>) :
    ListAdapter<Item, BaseViewHolder<viewBinding>>(
        AsyncDifferConfig.Builder<Item>(callBack)
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
    ) {

    override fun submitList(list: MutableList<Item>?) {
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<viewBinding> {
        return BaseViewHolder(
            DataBindingUtil.inflate<viewBinding>(
                LayoutInflater.from(parent.context),
                getLayout(viewType), parent, false
            )
        )
    }

    protected abstract fun getLayout(viewType: Int): Int

    override fun onBindViewHolder(holder: BaseViewHolder<viewBinding>, position: Int) {
        try {
            val item: Item = getItem(position)
            holder.binding.setVariable(BR.item, item)
            bindView(holder.binding, item, position)
        } catch (e: IndexOutOfBoundsException) {
            bind(holder.binding, position)
        }
        holder.binding.executePendingBindings()
    }

    protected open fun bindView(binding: viewBinding, item: Item, position: Int) {}

    protected open fun bind(binding: viewBinding, position: Int) {}
}
