package com.jossendal.flickerbrowser

import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_photo_details.*

class PhotoDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        activateToolbar(true)

//        val photo = intent.getSerializableExtra(PHOTO_TRANSFER) as Photo
        val photo = intent.extras.getParcelable(PHOTO_TRANSFER) as Photo

        photo_title.text = resources.getString(R.string.photo_title_text, photo.title)
        photo_author.text = resources.getString(R.string.photo_author, photo.title)
//        photo_author.text = resources.getString(R.string.photo_author_text, "my", "red", "car")
//        photo_tags.text = "Tags: " + photo.tags
//        photo_author.text = photo.author
        Picasso.get().load(photo.link)
            .error(R.drawable.baseline_image_white_48dp_lg)
            .placeholder(R.drawable.baseline_image_white_48dp_lg)
            .into(photo_image)
    }

}
