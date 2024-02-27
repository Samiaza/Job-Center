package com.example.app.util

import android.content.Context

fun readJsonFromResource(context: Context, resourceId: Int): String {
    return context.resources.openRawResource(resourceId).bufferedReader().use { it.readText() }
}
