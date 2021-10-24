package com.hari.mycat.utils

import android.accounts.NetworkErrorException
import android.util.Log
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Int.errorCodeNetwork(moreMessage: String?) =
    when (this) {
        400 -> "Masalah validasi pengiriman, $moreMessage"
        401 -> "Terdapat Masalah Kredensial, $moreMessage"
        403 -> "Tidak berhak mengakses data, $moreMessage"
        404 -> "Tidak dapat menemukan data, $moreMessage"
        500 -> "Terdapat Masalah pada Server, $moreMessage"
        503 -> "Maaf server sedang Maintenace, $moreMessage"
        else -> "Maaf, mohon coba lagi"
    }

fun resolveError(e: Exception): String =
    when (e) {
        is SocketTimeoutException -> "internet lambat, coba lagi"
        is ConnectException -> "internet akses tidak diizinkan, coba lagi"
        is UnknownHostException -> "Server sibuk, coba lagi nanti"
        is NetworkErrorException -> "Masalah jaringan, coba lagi"
        else -> {
            Log.i("networkError", e.message ?: "")
            "Maaf, mohon coba lagi"
        }
    }