package com.axt.esgi.esgi4a2020.data.dto.mapper

import com.axt.esgi.esgi4a2020.data.FlickrUtil
import com.axt.esgi.esgi4a2020.data.dto.PhotoDetailDTO
import com.axt.esgi.esgi4a2020.data.dto.PhotosResponseDTO
import com.axt.esgi.esgi4a2020.data.model.Photo
import com.axt.esgi.esgi4a2020.data.model.PhotoDetail

class PhotosResponseMapper {

    fun map(photosResponse: PhotosResponseDTO): List<Photo> {
        val photoListDTO = photosResponse.photos.photoList

        return photoListDTO.map { photoDto ->
            val url = FlickrUtil.getPhotoPublicUrl(
                photoDto.id,
                photoDto.secret,
                photoDto.server,
                photoDto.farm
            )
            Photo(photoDto.id, url, photoDto.title)
        }
    }
}

class PhotoDetailMapper {
    fun map(ePhotoDetail: PhotoDetailDTO): PhotoDetail {
        return ePhotoDetail.photo.run {
            val url = FlickrUtil.getPhotoPublicUrl(id, secret, server, farm)
            val tags = tags.tag.map { it.raw }

            PhotoDetail(
                id,
                url,
                title.content,
                description.content,
                owner.username,
                owner.realname,
                tags
            )
        }
    }
}