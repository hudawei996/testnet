package com.goals.testnet.network

import com.goals.testnet.network.api.MyApiService
import com.goals.testnet.network.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * 网络数据源统一入口
 */
class PostRepository @Inject constructor(private val apiService: MyApiService) {

    suspend fun getPosts(): Flow<List<Post>> = flow {
        val response = apiService.getPosts()
        if (response.isSuccessful) {
            val posts = response.body() ?: emptyList()
            emit(posts)
        } else {
            // 可选：抛出异常或发射空列表
            emit(emptyList())
        }
    }

    suspend fun getPost(id: Int): Flow<Post> = flow {
        val response = apiService.getPost(id)
        if (response.isSuccessful) {
            val post = response.body()
            if (post != null) {
                emit(post)
            }
        } else {
            // 可选：抛出异常或处理错误
            emit(Post(0, 0, "", ""))
        }
    }
}
