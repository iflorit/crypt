package es.florit.crypt.ui.list.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by ismael on 18/3/18.
 */
abstract class EndlessOnScrollListener(val mLinearLayoutManager: LinearLayoutManager, mVisibleThreshold: Int=0) : RecyclerView.OnScrollListener() {

    val TAG = EndlessOnScrollListener::class.java.simpleName

    private var mPreviousTotal = 0 // The total number of items in the dataset after the last load
    var isLoading = true // True if we are still waiting for the last set of data to load.
    private var mVisibleThreshold = 2 // The minimum amount of items to have below your current scroll position before loading more.
    internal var mFirstVisibleItem: Int = 0
    internal var mVisibleItemCount: Int = 0
    internal var mTotalItemCount: Int = 0

    private var mCurrentPage = 1

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        // Log.d(TAG, "onScrolled");
        mVisibleItemCount = recyclerView!!.childCount
        mTotalItemCount = mLinearLayoutManager.itemCount
        mFirstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()

        /*  Log.d(TAG, "mVisibleItemCount: "+mVisibleItemCount);
        Log.d(TAG, "mTotalItemCount: "+mTotalItemCount);
        Log.d(TAG, "mFirstVisibleItem: "+mFirstVisibleItem);
        Log.d(TAG, "mPreviousTotal: "+mPreviousTotal);
        Log.d(TAG, "-------------------------------------------------------------------------------------------------------------------------------");
       */
        if (isLoading) {
            if (mTotalItemCount > mPreviousTotal) {
                isLoading = false
                mPreviousTotal = if (mPreviousTotal >= mFirstVisibleItem + mVisibleItemCount) mPreviousTotal else mFirstVisibleItem + mVisibleItemCount
            }
        }
        if (!isLoading && mTotalItemCount - mVisibleItemCount <= mFirstVisibleItem + mVisibleThreshold) {
            // End has been reached

            // Do something
            mCurrentPage++

            onLoadMore(mCurrentPage)

            isLoading = true
        }
    }

    abstract fun onLoadMore(current_page: Int)

    fun clear() {
        mCurrentPage = 1
    }

}