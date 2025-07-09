package com.goals.testnet

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.goals.testnet.ui.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * UI 展示与用户交互
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    private lateinit var postsTextView: TextView
    private lateinit var postDetailTextView: TextView
    private lateinit var refreshButton: Button
    private lateinit var fetchDetailButton: Button
    private lateinit var loadingTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("test_net","运行到此处...")

        bindViews()
        observeData()
        setupListeners()
    }

    private fun bindViews() {
        postsTextView = findViewById(R.id.postsTextView)
        postDetailTextView = findViewById(R.id.postDetailTextView)
        refreshButton = findViewById(R.id.refreshButton)
        fetchDetailButton = findViewById(R.id.fetchDetailButton)
        loadingTextView = findViewById(R.id.loadingTextView)
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.posts.collect { posts ->
                postsTextView.text = "最新帖子:\n" + posts.joinToString("\n") { post -> post.title }
            }
        }

        lifecycleScope.launch {
            viewModel.postDetail.collect { post ->
                postDetailTextView.text = if (post != null) {
                    "标题: ${post.title}\n内容: ${post.body}"
                } else {
                    "点击按钮获取详情"
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                loadingTextView.visibility = if (isLoading) TextView.VISIBLE else TextView.GONE
                loadingTextView.text = if (isLoading) "加载中..." else ""
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collect { msg ->
                msg?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupListeners() {
        refreshButton.setOnClickListener {
            viewModel.fetchPosts()
        }

        fetchDetailButton.setOnClickListener {
            viewModel.fetchPostDetail(1)
        }
    }
}