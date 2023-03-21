package br.com.raynerweb.movies.ext

import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

fun ImageView.loadFrom(
    url: String,
    @DrawableRes idImage: Int? = null,
    callback: Callback? = null
) {
    if (!TextUtils.isEmpty(url)) {
        Picasso.get()
            .load(url)
            .centerCrop()
            .fit()
            .apply { if (idImage != null) placeholder(idImage) }
            .fit()
            .into(this, callback)
    }

}

fun ImageView.loadRoundedFrom(
    url: String,
    @DrawableRes idImage: Int? = null,
    callback: Callback? = null
) {
    if (!TextUtils.isEmpty(url)) {
        Picasso.get()
            .load(url)
            .transform(RoundedCornersTransformation(10,2))
            .centerCrop()
            .fit()
            .apply { if (idImage != null) placeholder(idImage) }
            .fit()
            .into(this, callback)
    }

}
