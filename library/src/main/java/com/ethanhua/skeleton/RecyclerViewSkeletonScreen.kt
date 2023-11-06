package com.ethanhua.skeleton

import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ethanhua on 2017/7/29.
 */
class RecyclerViewSkeletonScreen private constructor(builder: Builder) : SkeletonScreen {
    override var isSowing: Boolean = false
        private set

    private val mRecyclerView = builder.mRecyclerView
    private val mActualAdapter = builder.mActualAdapter
    private val mSkeletonAdapter = SkeletonAdapter().apply {
        useAlpha = builder.mUseAlpha
        itemCount = builder.mItemCount
        layoutReference = builder.mSkeletonLayoutResID
        layoutArrayReferences = builder.mItemsResIDArray
        shimmer = builder.mShimmer
        alpha = builder.mShimmerAlpha
        baseAlpha = builder.mShimmerBaseAlpha
        color = builder.mShimmerColor
        baseColor = builder.mShimmerBaseColor
        shimmerAngle = builder.mShimmerAngle
        shimmerDuration = builder.mShimmerDuration
        direction = builder.mShimmerDirection
        shape = builder.mShimmerShape
        repeatCount = builder.mRepeatCount
        repeatDelay = builder.mRepeatDelay
        repeatMode = builder.mRepeatMode
    }
    private val mRecyclerViewFrozen = builder.mFrozen
    override fun show(): RecyclerViewSkeletonScreen {
        isSowing = true
        mRecyclerView.adapter = mSkeletonAdapter
        if (!mRecyclerView.isComputingLayout && mRecyclerViewFrozen) {
            mRecyclerView.suppressLayout(true)
        }
        return this
    }

    override fun hide() {
        isSowing = false
        mRecyclerView.adapter = mActualAdapter
    }

    class Builder(val mRecyclerView: RecyclerView) : SkeletonBuilder(mRecyclerView) {
        internal var mActualAdapter: RecyclerView.Adapter<*>? = null
        override var mSkeletonLayoutResID = R.layout.layout_default_item_skeleton
        //internal var mDirection = Direction.LEFT_TO_RIGHT
        internal var mItemCount = 10
        internal var mItemsResIDArray: IntArray = IntArray(0)
        internal var mFrozen = true

        /**
         * @param adapter the target recyclerView actual adapter
         */
        fun adapter(adapter: RecyclerView.Adapter<*>?): Builder {
            mActualAdapter = adapter
            return this
        }

        /**
         * @param itemCount the child item count in recyclerView
         */
        fun count(itemCount: Int): Builder {
            mItemCount = itemCount
            return this
        }

        /**
         * @param skeletonLayoutResIDs the loading array of skeleton layoutResID
         */
        fun loadArrayOfLayouts(@ArrayRes skeletonLayoutResIDs: IntArray): Builder {
            mItemsResIDArray = skeletonLayoutResIDs
            return this
        }

        /**
         * @param frozen whether frozen recyclerView during skeleton showing
         * @return
         */
        fun frozen(frozen: Boolean): Builder {
            mFrozen = frozen
            return this
        }

        override fun build(): RecyclerViewSkeletonScreen {
            return RecyclerViewSkeletonScreen(this)
        }

        /**
         * build and show skeleton screen
         */
        @Deprecated("use build() then show()")
        fun show(): RecyclerViewSkeletonScreen {
            val recyclerViewSkeleton = RecyclerViewSkeletonScreen(this)
            recyclerViewSkeleton.show()
            return recyclerViewSkeleton
        }

        init {
            mShimmerColor = ContextCompat.getColor(mRecyclerView.context, R.color.shimmer_color)
            mShimmerBaseColor = ContextCompat.getColor(mRecyclerView.context, R.color.shimmer_base_color)
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

        override fun baseColorRes(@ColorRes shimmerBaseColor: Int): Builder {
            return super.baseColorRes(shimmerBaseColor) as Builder
        }

        override fun baseColor(shimmerBaseColor: Int): Builder {
            return super.baseColor(shimmerBaseColor) as Builder
        }

        override fun colorRes(@ColorRes shimmerColor: Int): Builder {
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