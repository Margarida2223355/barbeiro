package com.camacho.barbeiro.data.error

import com.camacho.barbeiro.data.common.ResultWrapper
import java.lang.ref.WeakReference

abstract class CallbackWrapper<T>(
    view: IErrorCallBack,
    resultWrapper: ResultWrapper<T>
) {
    private val weakReference: WeakReference<IErrorCallBack>?
    protected abstract fun onSuccess(data: T)

    init {
        weakReference = WeakReference(view)

        val viewWeakRef = weakReference.get()

        viewWeakRef.let {
            resultWrapper.result?.let { result ->
                onSuccess(result)
            }

            resultWrapper.error?.let { error ->
                when(error) {
                    is ResultWrapper.NetworkError -> viewWeakRef?.onNetworkError()
                    is ResultWrapper.NetworkTimeOutError -> viewWeakRef?.onTimeOut()
                    is ResultWrapper.SessionExpired -> viewWeakRef?.onSessionExpired()
                    is ResultWrapper.GenericError -> viewWeakRef?.onGenericError(error.message, null)
                    is ResultWrapper.HttpError -> viewWeakRef?.onGenericError(error.message, error.validationErrors)
                    else -> {}
                }
            }
        }
    }
}