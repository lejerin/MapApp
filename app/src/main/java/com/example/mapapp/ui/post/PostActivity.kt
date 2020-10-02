package com.example.mapapp.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mapapp.R
import com.example.mapapp.base.BaseActivity
import com.example.mapapp.databinding.ActivityLoginBinding
import com.example.mapapp.ui.login.*
import kotlinx.android.synthetic.main.activity_post.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class PostActivity : BaseActivity<ActivityLoginBinding>() , KodeinAware {
    override val layoutResourceId: Int
        get() = R.layout.activity_post

    override val kodein by kodein()
    private lateinit var factory: PostViewModelFactory
    private lateinit var viewModel: PostViewModel

    private val TAG = "PostActivity"

    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }


}
