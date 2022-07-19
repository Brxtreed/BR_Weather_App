package com.weather.br_weather.network

class ResponseHolder<T> {
    var responseCode: ResponseCode = ResponseCode.DEFAULT
    var errorCode: ErrorCode = ErrorCode.NONE
    var httpErrorType: Int? = null
    var data: T? = null
    var stringData: String? = null
    var message: String = ""
    var errorMessage: String = ""
    var isCacheResult: Boolean = false

}