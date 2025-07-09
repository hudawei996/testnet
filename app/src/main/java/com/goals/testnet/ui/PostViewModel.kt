package com.goals.testnet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goals.testnet.network.PostRepository
import com.goals.testnet.network.model.Post
import kotlinx.coroutines.flow.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 数据绑定与生命周期管理
 */
@HiltViewModel
class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _postDetail = MutableStateFlow<Post?>(null)
    val postDetail: StateFlow<Post?> = _postDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchPosts() {
        viewModelScope.launch {
            _isLoading.value = true // 开始请求，显示加载状态
            try {
                repository
                    .getPosts()
                    .onEach { _posts.value = it }
                    .collect()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false // 请求结束，隐藏加载状态
            }
        }
    }

    fun fetchPostDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true // 开始请求，显示加载状态
            try {
                repository
                    .getPost(id)
                    .onEach { _postDetail.value = it }
                    .collect()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false // 请求结束，隐藏加载状态
            }
        }
    }
}
