package com.shadow.kotlinpractice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.shadow.kotlinpractice.databinding.ActivityMainBinding
import com.shadow.kotlinpractice.presentation.login.LoginActivity
import com.shadow.kotlinpractice.presentation.login.LoginViewModel
import com.shadow.kotlinpractice.presentation.login.LoginViewModelFactory
import com.shadow.kotlinpractice.presentation.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModel = ViewModelProvider(this, LoginViewModelFactory(this)).get(LoginViewModel::class.java)
        if (!loginViewModel.isLogin) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}