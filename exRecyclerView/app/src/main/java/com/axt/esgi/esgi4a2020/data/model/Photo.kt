package com.axt.esgi.esgi4a2020.data.model

data class Photo(val id: String, val url: String, val title: String) {
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass)
            return false

        other as Photo

        if(id != other.id)
            return false
        if(id != other.title)
            return false
        if(id != other.url)
            return false

        return true
    }
}