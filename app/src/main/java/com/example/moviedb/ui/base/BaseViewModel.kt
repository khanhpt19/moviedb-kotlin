package com.example.moviedb.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.model.Dialog
import com.example.moviedb.data.model.Tag
import com.example.moviedb.data.remote.factory.BaseException
import com.example.moviedb.utils.SingleLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val errorMessage = SingleLiveData<String>()
    val isLoadingMore = MutableLiveData<Boolean>()
    var navBackClick = SingleLiveData<Unit>()

    val compositeDisposable = CompositeDisposable()

    val snackBarMessage = MutableLiveData<String>()
    val toastMessage = MutableLiveData<String>()
    val inlineException = MutableLiveData<List<Tag>>()
    val alertException = MutableLiveData<Pair<String?, String>>()
    val dialogException = MutableLiveData<Dialog>()
    val redirectException = MutableLiveData<ProcessBuilder.Redirect>()

    // fail to refresh expired token
    val refreshTokenExpired = SingleLiveData<Unit>()
    // force update app
    val forceUpdateApp = SingleLiveData<Unit>()
    val unexpectedError = SingleLiveData<Unit>()
    val httpUnavailableError = SingleLiveData<Unit>()
    val rxMapperError = SingleLiveData<Unit>()
    val httpForbiddenError = SingleLiveData<Unit>()
    val httpGatewayTimeoutError = SingleLiveData<Unit>()
    // exception handler for coroutine
    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        viewModelScope.launch {
            onLoadFail(throwable)
        }
    }
    protected val viewModelScopeExceptionHandler = viewModelScope + exceptionHandler

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    open fun onError(throwable: Throwable) {
        val rxMapperNullErrorMessage = "The mapper function returned a null value."
        when (throwable) {
            is BaseException -> {
                when (throwable.httpCode) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> {
                        unexpectedError.call()
                    }
                    HttpURLConnection.HTTP_UNAVAILABLE -> {
                        httpUnavailableError.call()
                    }
                    HttpURLConnection.HTTP_FORBIDDEN -> {
                        httpForbiddenError.call()
                    }
                    HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> {
                        httpGatewayTimeoutError.call()
                    }
                    else -> {
                        when (throwable.cause) {
                            is UnknownHostException -> {
                                httpGatewayTimeoutError.call()
                            }
                            is ConnectException -> {
                                httpGatewayTimeoutError.call()
                            }
                            is SocketTimeoutException -> {
                                httpGatewayTimeoutError.call()
                            }
                            else -> {
                                val message = throwable.message
                                when {
                                    message?.contains(rxMapperNullErrorMessage) == true -> {
                                        rxMapperError.call()
                                    }
                                    else -> {
                                        unexpectedError.call()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else -> {
                val message = throwable.message
                when {
                    message?.contains(rxMapperNullErrorMessage) == true -> {
                        rxMapperError.call()
                    }
                    else -> {
                        unexpectedError.call()
                    }
                }
            }
        }
        hideLoading()
    }

    open suspend fun onLoadFail(throwable: Throwable) {
        withContext(Dispatchers.Main) {
            when (throwable) {
                is BaseException -> {
                    when (throwable.httpCode) {
                        HttpURLConnection.HTTP_BAD_REQUEST -> {
                            unexpectedError.call()
                        }
                        HttpURLConnection.HTTP_UNAVAILABLE -> {
                            httpUnavailableError.call()
                        }
                        HttpURLConnection.HTTP_FORBIDDEN -> {
                            httpForbiddenError.call()
                        }
                        HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> {
                            httpGatewayTimeoutError.call()
                        }
                        else -> {
                            when (throwable.cause) {
                                is UnknownHostException -> {
                                    httpGatewayTimeoutError.call()
                                }
                                is ConnectException -> {
                                    httpGatewayTimeoutError.call()
                                }
                                is SocketTimeoutException -> {
                                    httpGatewayTimeoutError.call()
                                }
                                else -> {
                                    val message = throwable.message

                                    unexpectedError.call()
                                }
                            }
                        }
                    }
                }
                else -> {
                    unexpectedError.call()
                }
            }
            hideLoading()
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun showLoading() {
        isLoading.value = true
    }

    fun hideLoading() {
        isLoading.value = false
    }

    fun showMessageError(e: String) {
        errorMessage.value = e
    }
}
