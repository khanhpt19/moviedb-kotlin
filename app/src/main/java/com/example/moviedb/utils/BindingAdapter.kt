package com.example.moviedb.utils

import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey


@BindingAdapter("imageSrc")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(Constants.BASE_IMAGE_URL + url)
        .into(imageView)
}

@BindingAdapter("onRefreshListener")
fun SwipeRefreshLayout.customRefreshListener(listener: SwipeRefreshLayout.OnRefreshListener?) {
    if (listener != null) setOnRefreshListener(listener)
}

@BindingAdapter("onScrollListener")
fun RecyclerView.customScrollListener(listener: RecyclerView.OnScrollListener?) {
    if (listener != null) addOnScrollListener(listener)
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.customRefreshing(refreshing: Boolean?) {
    isRefreshing = refreshing == true
}

@BindingAdapter("safeClick")
fun View.safeClick(listener: View.OnClickListener?) {
    val blockInMillis: Long = 500
    var lastClickTime: Long = 0
    this.setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime < blockInMillis) {
            return@setOnClickListener
        }
        lastClickTime = SystemClock.elapsedRealtime()
        listener?.onClick(this)
    }
}

@BindingAdapter("onSingleClick")
fun View.setSingleClick(execution: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        val blockInMillis: Long = 500
        var lastClickTime: Long = 0
        override fun onClick(p0: View?) {
            if (SystemClock.elapsedRealtime() - lastClickTime < blockInMillis) {
                return
            }
            lastClickTime = SystemClock.elapsedRealtime()
            execution.invoke()
        }
    })
}

@BindingAdapter("circleUrl")
fun ImageView.circleUrl(url: String?) = url?.let {
    Glide.with(context)
        .load(it)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

@BindingAdapter(
    value = ["loadImage", "thumbnailUrl", "placeholder", "errorDrawable", "centerCrop", "fitCenter", "circleCrop", "diskCacheStrategy", "signatureKey"],
    requireAll = false
)
fun ImageView.loadImage(
    url: String? = null,
    thumbnailUrl: String? = null,
    placeHolder: Drawable? = null,
    errorDrawable: Drawable? = null,
    centerCrop: Boolean = false,
    fitCenter: Boolean = false,
    circleCrop: Boolean = false,
    diskCacheStrategy: DiskCacheStrategy? = null,
    signatureKey: String? = null
) {
    if (url.isNullOrBlank()) {
        setImageDrawable(placeHolder)
        return
    }

    val requestOptions = RequestOptions().apply {
        diskCacheStrategy(diskCacheStrategy ?: DiskCacheStrategy.RESOURCE)

        placeholder(placeHolder)
        error(errorDrawable)

        // crop type
        if (centerCrop) centerCrop()
        if (fitCenter) fitCenter()
        if (circleCrop) circleCrop()

        if (!signatureKey.isNullOrBlank()) {
            signature(ObjectKey(signatureKey))
        }
    }

    Glide.with(context).load(url).apply {
        transition(DrawableTransitionOptions.withCrossFade())
        if (!thumbnailUrl.isNullOrBlank()) {
            thumbnail(Glide.with(context).load(thumbnailUrl).apply(requestOptions))
        } else {
            thumbnail(0.2f)
        }
        apply(requestOptions)
    }.into(this)
}
