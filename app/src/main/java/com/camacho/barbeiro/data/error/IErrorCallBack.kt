package com.camacho.barbeiro.data.error

interface IErrorCallBack {
    fun onGenericError(
        message: String?,
        validationErrors: Map<String, ArrayList<String>>?
    )

    fun onTimeOut()
    fun onNetworkError()
    fun onSessionExpired()
}