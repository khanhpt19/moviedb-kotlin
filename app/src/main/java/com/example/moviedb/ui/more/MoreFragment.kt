package com.example.moviedb.ui.more

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.P
import androidx.databinding.ViewDataBinding
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderBuilder
import coil.api.load
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.LoadRequest
import coil.transform.CircleCropTransformation
import com.example.moviedb.R
import com.example.moviedb.databinding.FragmentMoreBinding
import com.example.moviedb.ui.base.BaseFragment
import com.example.moviedb.utils.Constants
import kotlinx.android.synthetic.main.fragment_more.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoreFragment : BaseFragment<FragmentMoreBinding, MoreViewModel>() {
    companion object {
        val TAG = "MORE"
        fun newInstance() = MoreFragment()
    }

    override val viewModel by viewModel<MoreViewModel>()
    override val layoutId: Int = R.layout.fragment_more

    override fun initComponents(viewBinding: ViewDataBinding) {

        viewModel.loadMovie("299536")
//        image_back_drop.load(Constants.BASE_IMAGE_URL + "/p15fLYp0X04mS8cbHVj7mZ6PBBE.jpg"){
//            crossfade(true)
//            placeholder(R.drawable.ic_launcher_foreground)
//            transformations(CircleCropTransformation())
//        }

        val imageLoader = context?.let {
            ImageLoader(it){
                componentRegistry {
                    if(SDK_INT >= P){
                        add(ImageDecoderDecoder())
                    } else {
                        add(GifDecoder())
                    }
                }
            }
        }
        imageLoader?.let { image_back_drop.load("https://user-images.githubusercontent.com/24453308/62932870-05d2e580-bdd2-11e9-833a-87d26d36c3b2.gif", it) }
    }

}
