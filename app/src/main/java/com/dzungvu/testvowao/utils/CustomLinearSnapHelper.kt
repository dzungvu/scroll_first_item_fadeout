package com.dzungvu.testvowao.utils

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider


class GravitySnapHelper @SuppressLint("RtlHardcoded") constructor(private val gravity: Int) :
    LinearSnapHelper() {
    private var verticalHelper: OrientationHelper? = null
    private var horizontalHelper: OrientationHelper? = null
    private var isSupportRtL = true

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        if (layoutManager !is ScrollVectorProvider) {
            return RecyclerView.NO_POSITION
        }
        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        val myLayoutManager = layoutManager as LinearLayoutManager
        val position1 = myLayoutManager.findFirstVisibleItemPosition()
        val position2 = myLayoutManager.findLastVisibleItemPosition()
        var currentPosition = layoutManager.getPosition(currentView)
        if (velocityX > 0) {
            currentPosition = position2
        } else if (velocityX < 0) {
            currentPosition = position1
        }
        return if (currentPosition == RecyclerView.NO_POSITION) {
            RecyclerView.NO_POSITION
        } else currentPosition
    }

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            if (gravity == Gravity.START) {
                out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
            } else { // END
                out[0] = distanceToEnd(targetView, getHorizontalHelper(layoutManager))
            }
        } else {
            out[0] = 0
        }
        if (layoutManager.canScrollVertically()) {
            if (gravity == Gravity.TOP) {
                out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager))
            } else { // BOTTOM
                out[1] = distanceToEnd(targetView, getVerticalHelper(layoutManager))
            }
        } else {
            out[1] = 0
        }
        return out
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        if (layoutManager is LinearLayoutManager) {
            when (gravity) {
                Gravity.START -> return findStartView(
                    layoutManager,
                    getHorizontalHelper(layoutManager)
                )
                Gravity.TOP -> return findStartView(layoutManager, getVerticalHelper(layoutManager))
                Gravity.END -> return findEndView(layoutManager, getHorizontalHelper(layoutManager))
                Gravity.BOTTOM -> return findEndView(
                    layoutManager,
                    getVerticalHelper(layoutManager)
                )
            }
        }
        return super.findSnapView(layoutManager)
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper?): Int {
        return if (isSupportRtL) {
            distanceToEnd(targetView, helper)
        } else helper!!.getDecoratedStart(targetView) - helper.startAfterPadding
    }

    private fun distanceToEnd(targetView: View, helper: OrientationHelper?): Int {
        return if (isSupportRtL) {
            helper!!.getDecoratedStart(targetView) - helper.startAfterPadding
        } else helper!!.getDecoratedEnd(targetView) - helper.endAfterPadding
    }

    private fun findStartView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper?
    ): View? {
        if (layoutManager is LinearLayoutManager) {
            val firstChild = layoutManager.findFirstVisibleItemPosition()
            if (firstChild == RecyclerView.NO_POSITION) {
                return null
            }
            val child = layoutManager.findViewByPosition(firstChild)
            return if (helper!!.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
                && helper.getDecoratedEnd(child) > 0
            ) {
                child
            } else {
                if (layoutManager.findLastCompletelyVisibleItemPosition()
                    == layoutManager.getItemCount() - 1
                ) {
                    null
                } else {
                    layoutManager.findViewByPosition(firstChild + 1)
                }
            }
        }
        return super.findSnapView(layoutManager)
    }

    private fun findEndView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper?
    ): View? {
        if (layoutManager is LinearLayoutManager) {
            val lastChild = layoutManager.findLastVisibleItemPosition()
            if (lastChild == RecyclerView.NO_POSITION) {
                return null
            }
            val child = layoutManager.findViewByPosition(lastChild)
            return if (helper!!.getDecoratedStart(child) + helper.getDecoratedMeasurement(child) / 2
                <= helper.totalSpace
            ) {
                child
            } else {
                if (layoutManager.findFirstCompletelyVisibleItemPosition()
                    == 0
                ) {
                    null
                } else {
                    layoutManager.findViewByPosition(lastChild - 1)
                }
            }
        }
        return super.findSnapView(layoutManager)
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (verticalHelper == null) {
            verticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return verticalHelper
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (horizontalHelper == null) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return horizontalHelper
    }
}