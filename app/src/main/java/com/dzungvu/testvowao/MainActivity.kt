package com.dzungvu.testvowao

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dzungvu.testvowao.adapters.EPGGeneralAdapter
import com.dzungvu.testvowao.utils.CustomLinearLayoutManagement
import com.dzungvu.testvowao.utils.DataUtils
import com.dzungvu.testvowao.utils.GravitySnapHelper

class MainActivity : AppCompatActivity() {
    private val tag = this::class.java.simpleName
    private val rcvMain: RecyclerView by lazy { findViewById(R.id.rcvEpg) }
    private var itemHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManagement = CustomLinearLayoutManagement(rcvMain.context)

        rcvMain.apply {
            adapter = EPGGeneralAdapter(DataUtils.createListData())
            layoutManager = layoutManagement
        }

        val snapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelper.attachToRecyclerView(rcvMain)

        rcvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (itemHeight == 0) {
                    itemHeight = (layoutManagement.height / 2.5).toInt()
                }

                val curOffset = recyclerView.computeVerticalScrollOffset()
                val alpha = 1 - (curOffset % itemHeight) / itemHeight.toFloat()

                if (dy > 0) {
                    val interactView =
                        layoutManagement.findViewByPosition(curOffset / itemHeight + 1)
                    setViewWithAlpha(interactView, alpha)

                } else if (dy < 0) {
                    val preView = layoutManagement.findViewByPosition(curOffset / itemHeight)
                    setViewWithAlpha(preView, 0f)

                    val interactView =
                        layoutManagement.findViewByPosition(curOffset / itemHeight + 1)
                    setViewWithAlpha(interactView, alpha)
                }
            }
        })
    }

    private fun setViewWithAlpha(view: View?, alpha: Float) {
        view?.alpha = alpha
    }
}