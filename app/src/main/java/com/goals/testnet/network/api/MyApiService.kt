package com.goals.testnet.network.api

import com.goals.testnet.network.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 定义 RESTful 接口
 */
interface MyApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Response<Post>

    // 您可以在这里添加更多 API 方法 (POST, PUT, DELETE 等)
}
