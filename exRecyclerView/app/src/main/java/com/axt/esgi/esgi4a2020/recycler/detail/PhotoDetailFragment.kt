package com.axt.esgi.esgi4a2020.recycler.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.axt.esgi.esgi4a2020.R
import com.axt.esgi.esgi4a2020.common.showError
import com.axt.esgi.esgi4a2020.data.api.FlickrProvider
import com.axt.esgi.esgi4a2020.data.api.Listener
import com.axt.esgi.esgi4a2020.data.model.PhotoDetail
import com.bumptech.glide.Glide

private const val TAG_PREFIX = "#"
private const val TAG_SEPARATOR = "  #"

class PhotoDetailFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var nameTv: TextView
    private lateinit var titleTv: TextView
    private lateinit var descriptionTv: TextView
    private lateinit var tagsTv: TextView

    private val args by navArgs<PhotoDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            imageView = findViewById(R.id.photo_detail_imv)
            nameTv = findViewById(R.id.photo_detail_name_tv)
            titleTv = findViewById(R.id.photo_detail_title_tv)
            descriptionTv = findViewById(R.id.photo_detail_description_tv)
            tagsTv = findViewById(R.id.photo_detail_tags_tv)
        }
        getPhotoDetail()
    }

    private fun getPhotoDetail() {
        val photoId = args.photoId
        FlickrProvider.getPhotoDetail(photoId, object : Listener<PhotoDetail> {
            override fun onSuccess(data: PhotoDetail) {
                updateInfo(data)
            }

            override fun onError(t: Throwable) {
                showError(t)
            }

        })
    }

    private fun updateInfo(photoDetail: PhotoDetail) {
        Glide.with(this).load(photoDetail.url).into(imageView)
        nameTv.text = photoDetail.ownerName
        titleTv.text = photoDetail.title
        descriptionTv.text = photoDetail.description
        val prefix = if (photoDetail.tags.isNotEmpty()) TAG_PREFIX else ""
        tagsTv.text = photoDetail.tags.joinToString(prefix = prefix, separator = TAG_SEPARATOR)
    }
}
