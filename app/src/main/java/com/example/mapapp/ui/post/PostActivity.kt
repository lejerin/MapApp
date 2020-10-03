package com.example.mapapp.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.R
import com.example.mapapp.base.BaseActivity
import com.example.mapapp.data.repositories.LoginRepository
import com.example.mapapp.data.repositories.PostRepository
import com.example.mapapp.databinding.ActivityLoginBinding
import com.example.mapapp.databinding.ActivityPostBinding
import com.example.mapapp.ui.login.*
import kotlinx.android.synthetic.main.activity_post.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class PostActivity : BaseActivity<ActivityPostBinding>() , KodeinAware {
    override val layoutResourceId: Int
        get() = R.layout.activity_post

    override val kodein by kodein()
    private lateinit var factory: PostViewModelFactory
    private lateinit var viewModel: PostViewModel

    private val TAG = "PostActivity"

    override fun initStartView() {

    }

    override fun initDataBinding() {

        val repository = PostRepository()
        factory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
        viewDataBinding.postVM = viewModel

    }

    override fun initAfterBinding() {

    }

    override fun finish() {
        super.finish()

        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }

}
