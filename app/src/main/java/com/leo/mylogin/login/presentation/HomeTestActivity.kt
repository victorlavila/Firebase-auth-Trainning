package com.leo.mylogin.login.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.leo.mylogin.R
import com.leo.mylogin.databinding.ActivityHomeTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeTestBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loading.isIndeterminate = false
        setToolbar()
    }

    private fun setToolbar() {
        with(binding) {
            toolbarHome.setNavigationOnClickListener {
                setLogOut()
            }
            toolbarHome.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.logout -> {
                        setLogOut()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setLogOut() {
        loginViewModel.logOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}