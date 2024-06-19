package com.camacho.barbeiro.data.common.objects

import com.google.gson.annotations.SerializedName


data class SuccessResponse(
    @SerializedName("message") val message: String,
    @SerializedName("id") val id: String?
)