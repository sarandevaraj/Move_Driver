package com.taximobility.bookingmodule.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.taximobility.R
import com.taximobility.bookingmodule.LocationData
import com.taximobility.databinding.LocationFavouriteListBinding
import com.taximobility.databinding.LocationListBinding
import com.taximobility.util.*


class LocationListAdapter(var mContext: Activity, var data: List<LocationData>, val listener: DeleteClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var layoutInflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == 1) {
            val binding = DataBindingUtil.inflate<LocationFavouriteListBinding>(layoutInflater, R.layout.location_favourite_list, parent, false)
            CustomFavViewHolder(binding.root)
        } else {
            val binding = DataBindingUtil.inflate<LocationListBinding>(layoutInflater, R.layout.location_list, parent, false)
            CustomViewHolder(binding.root)
        }
    }

    fun updateList(list: List<LocationData>) {
        this.data = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = holder.itemViewType
        if (viewType != 1 || viewType < 0) {
            val vHs = holder as CustomViewHolder
            vHs.binding!!.mData = data[position]
            if (viewType < 0) {
                vHs.binding!!.imgLoc.visibility = View.GONE
                vHs.binding!!.txtLocationType.visibility = View.GONE
                vHs.binding!!.lineSep!!.visibility = View.GONE
//                vHs.binding!!.txtLoctype.typeface = Typeface.DEFAULT_BOLD
                vHs.binding!!.txtLoctype.setTextColor(ContextCompat.getColor(mContext, R.color.textviewcolor_light))
            } else {
                vHs.binding!!.imgLoc.visibility = View.VISIBLE
            }
            val locType = data[position].type
            if (locType == PopularPlaceType) {
                vHs.binding!!.txtLocationType.visibility = View.VISIBLE
                vHs.binding!!.txtLocationType.text = data[position].label_name
                vHs.binding!!.txtLocationType.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                vHs.binding!!.imgLoc.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.fav_others))
            } else if (locType == RecentPlaceType) {
                vHs.binding!!.txtLocationType.visibility = View.GONE
//                vHs.binding!!.imgLoc.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_recent))
                vHs.binding!!.imgLoc.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_drop_pin))
            }

            vHs.binding!!.locationLay.setOnClickListener {
                if (viewType > 0) {
                    listener.itemClick(position)
                }
            }
        } else {
            val vH = holder as CustomFavViewHolder
            vH.binding!!.mLocationData = data[position]
            val locType = data[position].label_name
            when (locType) {
                mContext.getString(R.string.home) -> vH.binding!!.imgLocType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.home))
                mContext.getString(R.string.office) -> vH.binding!!.imgLocType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.fav_office))
                mContext.getString(R.string.airport) -> vH.binding!!.imgLocType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.fav_airport))
                else -> vH.binding!!.imgLocType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.fav_others))
            }
            vH.binding!!.imgLocDelete.setOnClickListener {
                Utility.alert_view_dialog(mContext, "",
                        "" + mContext.resources.getString(R.string.confirm_delete),
                        "" + mContext.resources.getString(R.string.ok),
                        "" + mContext.resources.getString(R.string.cancel),
                        false, { dialog, which ->
                    listener.deleteClick(data[position])
                    dialog.dismiss()
                }, { dialog, which -> dialog.dismiss() }, "")

            }

            vH.binding!!.mainLocLay.setOnClickListener {
                listener.itemClick(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item.type.toInt()
    }

    fun getItem(position: Int): LocationData {
        return data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var binding: LocationListBinding? = null

        init {
            binding = DataBindingUtil.bind(view)
        }
    }

    inner class CustomFavViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var binding: LocationFavouriteListBinding? = null

        init {
            binding = DataBindingUtil.bind(view)
        }
    }

    interface DeleteClickListener {
        fun deleteClick(locationName: LocationData)
        fun itemClick(position: Int)
    }
}