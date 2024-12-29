package com.exercicios.dailynews.models

import com.exercicios.dailynews.encodeURL
import com.exercicios.dailynews.parseDate
import com.exercicios.dailynews.toServerDate
import org.json.JSONObject
import java.util.Date

class Article(
    var title: String? = null,
    var description: String? = null,
    var urlToImage: String? = null,
    var url: String,
    var publishedAt: Date? = null
) {
    companion object {
        fun fromJson(json: JSONObject): Article {
            return Article(
                title = json.getString("title"),
                description = json.getString("description"),
                urlToImage = json.getString("urlToImage"),
                url = json.getString("url") ?: "no url",
                publishedAt = json.getString("publishedAt").parseDate()
            )
        }
    }

    fun toJsonString(): String {
        val jsonObject = JSONObject()
        jsonObject.put("title", title)
        jsonObject.put("description", description)
        jsonObject.put("urlToImage", urlToImage?.encodeURL())
        jsonObject.put("url", url?.encodeURL())
        jsonObject.put("publishedAt", publishedAt?.toServerDate())
        return jsonObject.toString()
    }
}