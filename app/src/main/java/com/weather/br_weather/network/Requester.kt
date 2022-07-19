package com.weather.br_weather.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

abstract class Requester<T> : Callback<T> {

    val responseHolder: ResponseHolder<T> = ResponseHolder()


    val liveResponseHolder: MutableLiveData<ResponseHolder<T>> by lazy {
        MutableLiveData<ResponseHolder<T>>()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        responseHolder.message = t.localizedMessage
        responseHolder.responseCode = ResponseCode.ERROR
        if (t is SocketTimeoutException)
        {
            responseHolder.errorCode = ErrorCode.TIMEOUT
            responseHolder.httpErrorType = 504
        }
        else {
            responseHolder.errorCode = ErrorCode.UPLOAD_LIMIT
            responseHolder.httpErrorType = 413

        }
        Log.e("Requester Error", t.localizedMessage)
        liveResponseHolder.postValue(responseHolder)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if(response.raw().cacheResponse() != null) {
            responseHolder.isCacheResult = true
        }

        if (response.isSuccessful) {
            responseHolder.responseCode = ResponseCode.SUCCESS
            responseHolder.data = response.body()
            Log.d("Response", Gson().toJson(response.body()))
        } else {
            responseHolder.responseCode = ResponseCode.ERROR
            responseHolder.message = response.body().toString()
            responseHolder.errorMessage = response.raw().message()
            responseHolder.httpErrorType = response.code()
            Log.e("Requester Error", response.body().toString())
        }
        liveResponseHolder.postValue(responseHolder)
    }

    abstract fun retry(call: Call<T>, response: Response<T>)


}