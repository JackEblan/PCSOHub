package com.android.pcsohub.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LottoDtoItem(
    @SerializedName("content_badge") val contentBadge: String,
    @SerializedName("content_body") val contentBody: String,
    @SerializedName("content_data1") val contentData1: Int,
    @SerializedName("content_data2") val contentData2: String,
    @SerializedName("content_id") val contentId: String,
    @SerializedName("content_photo") val contentPhoto: String,
    @SerializedName("content_subtitle1") val contentSubtitle1: String,
    @SerializedName("content_subtitle2") val contentSubtitle2: String,
    @SerializedName("content_title") val contentTitle: String,
    @SerializedName("content_url") val contentUrl: String,
    @SerializedName("id") val id: String
)