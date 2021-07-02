package com.dzungvu.testvowao.utils

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CustomLinearLayoutManagement(context: Context) :
    LinearLayoutManager(context, RecyclerView.VERTICAL, false) {
    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
        val h = (height / 2.5).toInt()
        Log.d("checkLayoutParams", "layoutParamH: $h")
        lp?.height = h
        return true
    }
}