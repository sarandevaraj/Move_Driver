package com.taximobility

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Picasso
import java.util.*

object ProfileImageSetupClass {

    @JvmStatic
    fun setupProfileImage(mFirstName: String, profileImage: ImageView) {


        var mName = ""
        mName = if (mFirstName == "")
            "A"
        else
            mFirstName[0].toString().toUpperCase(Locale.ROOT)

        Picasso.get().load("${"http://mongo.tmobility.ai/public/no_image/"}$mName.png").placeholder(R.drawable.loadingimage).error(R.drawable.loadingimage).into(profileImage)


    }

    fun loadImage(mUrl: String, mImageView: AppCompatImageView) {
        Picasso.get().load(mUrl).placeholder(R.drawable.loadingimage).error(R.drawable.loadingimage).into(mImageView)
    }


}