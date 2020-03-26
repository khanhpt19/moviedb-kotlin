package com.example.moviedb.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    val currentId = MutableLiveData<Int>()
}
