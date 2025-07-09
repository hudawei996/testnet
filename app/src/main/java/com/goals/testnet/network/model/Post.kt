package com.goals.testnet.network.model

import com.google.gson.annotations.SerializedName

/**
 * 示例数据模型：帖子。
 * 用于解析 JSONPlaceholder API 的响应。
 */
data class Post(
    @SerializedName("userId") val userId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)
