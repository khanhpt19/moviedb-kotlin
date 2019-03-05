package com.example.moviedb.ui.popular

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndLessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {
    private var mPreviousTotal = 0
    private var mLoading = true
    private val visibleThreshold = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager?.itemCount
        var firstVisibleItem: Int? = 0

        if (recyclerView.layoutManager is LinearLayoutManager) {
            firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        }

        if (mLoading) {
            totalItemCount?.let {
                if (totalItemCount > mPreviousTotal) {
                    mLoading = false
                    mPreviousTotal = totalItemCount
                }
            }
        }
        totalItemCount?.let {
            firstVisibleItem?.let {
                if (!mLoading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                    onLoadMore()
                    mLoading = true
                }
            }
        }
    }

    abstract fun onLoadMore()
}
