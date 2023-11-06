package com.ethanhua.skeleton

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

/**
 * Created by ethanhua on 2017/7/29.
 */
class LinearLayoutViewSkeletonScreen private constructor(builder: Builder) : SkeletonScreen {
    private val mViewReplacer = ViewReplacer(builder.mView)
    private val mActualView = builder.mView
    private val mUseAlpha = builder.mUseAlpha
    private val mShimmerAngle = builder.mShimmerAngle
    private val mSkeletonResID = builder.mSkeletonLayoutResID
    private val mShimmerColor = builder.mShimmerColor
    private val mShimmerBaseColor = builder.mShimmerBaseColor
    private val mShimmerAlpha = builder.mShimmerAlpha
    private val mShimmerBaseAlpha = builder.mShimmerBaseAlpha
    private val mShimmer = builder.mShimmer
    private val mShimmerDuration = builder.mShimmerDuration
    private val mShimmerDirection = builder.mShimmerDirection
    private val mShimmerShape = builder.mShimmerShape
    private val mRepeatCount = builder.mRepeatCount
    private val mRepeatDelay = builder.mRepeatDelay
    private val mRepeatMode = builder.mRepeatMode
    private val mItemCount = builder.mItemCount
    private val mItemsOrientation = builder.mOrientation

    private fun generateShimmerContainerLayout(parentView: ViewGroup): ShimmerFrameLayout {
        val shimmerLayout = LayoutInflater.from(mActualView.context).inflate(R.layout.linear_layout_shimmer, parentView, false) as ShimmerFrameLayout
        val shimmer = if (mUseAlpha) {
            Shimmer.AlphaHighlightBuilder()
                .setBaseAlpha(mShimmerBaseAlpha)
                .setHighlightAlpha(mShimmerAlpha)

        } else {
            Shimmer.ColorHighlightBuilder()
                .setBaseColor(mShimmerBaseColor)
                .setHighlightColor(mShimmerColor)
        }.apply {
            setTilt(mShimmerAngle.toFloat())
            setShape(mShimmerShape.value)
            setDirection(mShimmerDirection.value)
            setDuration(mShimmerDuration)
            setRepeatCount(mRepeatCount)
            setRepeatDelay(mRepeatDelay)
            setRepeatMode(mRepeatMode.value)
        }.build()
        shimmerLayout.setShimmer(shimmer)

        val linearLayout = shimmerLayout.findViewById<LinearLayout>(R.id.items)
        linearLayout.orientation = mItemsOrientation
        repeat(mItemCount) {
            val innerView = LayoutInflater.from(mActualView.context).inflate(mSkeletonResID, shimmerLayout, false)
            innerView.layoutParams?.let {
                shimmerLayout.layoutParams = it
            }

            linearLayout.addView(innerView)
        }

        shimmerLayout.addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                shimmerLayout.startShimmer()
            }

            override fun onViewDetachedFromWindow(v: View) {
                shimmerLayout.stopShimmer()
            }
        })
        shimmerLayout.startShimmer()
        return shimmerLayout
    }

    private fun generateSkeletonLoadingView(): View? {
        val viewParent = mActualView.parent
        if (viewParent == null) {
            Log.e(TAG, "the source view have not attach to any view")
            return null
        }
        val parentView = viewParent as ViewGroup
        return if (mShimmer) {
            generateShimmerContainerLayout(parentView)
        } else LayoutInflater.from(mActualView.context).inflate(mSkeletonResID, parentView, false)
    }

    override fun show(): LinearLayoutViewSkeletonScreen {
        val skeletonLoadingView = generateSkeletonLoadingView()
        if (skeletonLoadingView != null) {
            mViewReplacer.replace(skeletonLoadingView)
        }
        return this
    }

    override fun hide() {
        (mViewReplacer.targetView as? ShimmerFrameLayout)?.stopShimmer()
        mViewReplacer.restore()
    }

    companion object {
        private val TAG = LinearLayoutViewSkeletonScreen::class.java.name
    }

    open class Builder(mLinearLayout: LinearLayout) : SkeletonBuilder(mLinearLayout) {
        internal open var mItemCount = 10
        internal open var mOrientation = LinearLayout.VERTICAL

        override fun build(): LinearLayoutViewSkeletonScreen {
            return LinearLayoutViewSkeletonScreen(this)
        }

        open fun count(count: Int): Builder {
            mItemCount = count
            return this
        }

        open fun orientation(@LinearLayoutCompat.OrientationMode orientation: Int): Builder {
            mOrientation = orientation
            return this
        }

        /**
         * Below functions just override for cast
         */

        override fun load(skeletonLayoutResID: Int): Builder {
            return super.load(skeletonLayoutResID) as Builder
        }

        override fun baseAlpha(alpha: Float): Builder {
            return super.baseAlpha(alpha) as Builder
        }

        override fun alpha(alpha: Float): Builder {
            return super.alpha(alpha) as Builder
        }

        override fun baseColorRes(shimmerBaseColor: Int): Builder {
            return super.baseColorRes(shimmerBaseColor) as Builder
        }

        override fun baseColor(shimmerBaseColor: Int): Builder {
            return super.baseColor(shimmerBaseColor) as Builder
        }

        override fun colorRes(shimmerColor: Int): Builder {
            return super.colorRes(shimmerColor) as Builder
        }

        override fun color(shimmerColor: Int): Builder {
            return super.color(shimmerColor) as Builder
        }

        override fun shimmer(shimmer: Boolean): Builder {
            return super.shimmer(shimmer) as Builder
        }

        override fun duration(shimmerDuration: Long): Builder {
            return super.duration(shimmerDuration) as Builder
        }

        override fun angle(shimmerAngle: Int): Builder {
            return super.angle(shimmerAngle) as Builder
        }

        override fun direction(direction: Direction): Builder {
            return super.direction(direction) as Builder
        }

        override fun setUseAlpha(use: Boolean): Builder {
            return super.setUseAlpha(use) as Builder
        }

        override fun shape(shape: Shape): Builder {
            return super.shape(shape) as Builder
        }

        override fun repeatCount(repeatCount: Int): Builder {
            return super.repeatCount(repeatCount) as Builder
        }

        override fun repeatMode(repeatMode: RepeatMode): Builder {
            return super.repeatMode(repeatMode) as Builder
        }

        override fun repeatDelay(repeatDelay: Long): Builder {
            return super.repeatDelay(repeatDelay) as Builder
        }
    }
}