package com.example.mapapp.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mapapp.R
import com.example.mapapp.ui.home.HomeFragment
import com.example.mapapp.ui.like.LikeFragment
import com.example.mapapp.ui.post.PostActivity
import com.example.mapapp.ui.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , View.OnClickListener {
    private val homeFragment: HomeFragment =
        HomeFragment()
    private val likeFragment: LikeFragment =
        LikeFragment()
    private val settingFragment: SettingFragment =
        SettingFragment()

    private lateinit var selectedBtn: ImageButton
    private var selectedNum = 0

    private val images = listOf<Int>(R.drawable.home_img, R.drawable.home_img ,R.drawable.home_img , R.drawable.ic_baseline_settings_24)
    private val vectors = listOf<Int>(R.drawable.home_anim, R.drawable.home_anim ,R.drawable.home_anim , R.drawable.setting_anim)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(homeFragment)

        selectedBtn = btn_home
        setBtnAnimation(selectedBtn, vectors[0])


        fab.setOnClickListener(this)
        btn_home.setOnClickListener(this)
        btn_like.setOnClickListener(this)
        btn_search.setOnClickListener(this)
        btn_setting.setOnClickListener(this)

    }



    override fun onClick(v: View) {
        when(v.id){
            R.id.fab -> {
                setBtnAnimation(fab, R.drawable.fab_icon)
                val intent = Intent(this, PostActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_home -> {
                toggleBtn(btn_home, 0)
                setFragment(homeFragment)
            }
            R.id.btn_like -> {
                toggleBtn(btn_like, 1)
                setFragment(likeFragment)
            }
            R.id.btn_search -> {
                toggleBtn(btn_search, 2)
               // setFragment(likeFragment)
            }
            R.id.btn_setting -> {
                toggleBtn(btn_setting, 3)
                setFragment(settingFragment)
                System.out.println("cdd")
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_frame, fragment)
        ft.commit()
    }

    private fun toggleBtn(btn: ImageButton, num: Int){
        selectedBtn.setImageResource(images[selectedNum])
        setBtnAnimation(btn, vectors[num])
        selectedBtn = btn
        selectedNum = num
    }

    private fun setBtnAnimation(v: ImageButton, source: Int){
        val vector = ContextCompat.getDrawable(this, source) as AnimatedVectorDrawable
        v.setImageDrawable(vector)
        vector.start()
    }
}