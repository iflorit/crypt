package es.florit.crypt.ui.list.util

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import es.florit.crypt.R
import es.florit.crypt.dagger.RetrofitModule
import es.florit.crypt.datasource.repository.model.Crypto
import es.florit.crypt.ui.detail.CryptoDetailActivity
import es.florit.crypt.ui.detail.CryptoDetailFragment
import es.florit.crypt.ui.list.CrytoListActivity
import kotlinx.android.synthetic.main.crypto_list_content.view.*


/**
 * Created by ismael on 18/3/18.
 */
class CryptoListAdapter(private val mParentActivity: CrytoListActivity,
                        private val mValues: MutableList<Crypto>,
                        private val mTwoPane: Boolean) :
        RecyclerView.Adapter<CryptoListAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Crypto
            if (mTwoPane) {
                val fragment = CryptoDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(CryptoDetailFragment.ARG_ITEM, item)
                    }
                }
                mParentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.crypto_detail_container, fragment)
                        .commit()
            } else {
                v.context.startActivity(Intent(v.context, CryptoDetailActivity::class.java).apply {
                    putExtra(CryptoDetailFragment.ARG_ITEM, item)
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mNameView.text = item.name
        holder.mOverviewView.text = "USD " + item.price + " (" + item.percentage + ")"
        holder.mRating.rating = item.rank.toFloat()

        Glide.with(mParentActivity)
                .load("${RetrofitModule.BASE_IMG}${item.logo}")
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .apply(RequestOptions
                        .placeholderOf(R.drawable.poster_placeholder)
                        .centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.mPoster)

        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }


        setAnimation(holder.itemView, position);
    }


    private var lastPosition = -1
    private fun setAnimation(viewToAnimate: View, position: Int) {
        val animation = AnimationUtils.loadAnimation(mParentActivity,
                if (position > lastPosition)
                    R.anim.up_from_bottom
                else
                    R.anim.down_from_top)
        viewToAnimate.startAnimation(animation)
        lastPosition = position
    }


    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mCardPoster: CardView = mView.card_poster
        val mPoster: ImageView = mView.poster

        val mCard: CardView = mView.card_view
        val mNameView: TextView = mView.name
        val mOverviewView: TextView = mView.overview
        val mRating: RatingBar = mView.rating
    }
}