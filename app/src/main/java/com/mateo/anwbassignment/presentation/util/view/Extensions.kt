package com.mateo.anwbassignment.presentation.util.view

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import java.net.URLDecoder
import java.net.URLEncoder
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun String.encode() : String {
    return URLEncoder.encode(this,"UTF-8")
}

fun String.decode() : String {
    return URLDecoder.decode(this,"UTF-8")
}

fun OffsetDateTime.format(): String =
    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.getDefault()).format(this)
