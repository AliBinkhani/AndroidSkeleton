package com.ethanhua.skeleton

/**
 * @author darren
 * @date 20-11-11
 */
class SkeletonGroup: SkeletonScreen {
    override var isShowing: Boolean = false
        private set

    private val skeletonSet = mutableSetOf<SkeletonScreen>()

    fun addSkeleton(skeleton: SkeletonScreen) {
        skeletonSet.add(skeleton)
    }

    fun removeSkeleton(skeleton: SkeletonScreen) {
        skeletonSet.remove(skeleton)
    }

    override fun show(): SkeletonScreen {
        isShowing = true
        skeletonSet.forEach {
            it.show()
        }
        return this
    }

    override fun hide() {
        isShowing = false
        skeletonSet.forEach {
            it.hide()
        }
    }
}