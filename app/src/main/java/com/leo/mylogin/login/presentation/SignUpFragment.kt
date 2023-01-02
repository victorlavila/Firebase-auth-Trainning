package com.leo.mylogin.login.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.leo.mylogin.R
import com.leo.mylogin.databinding.FragmentSignUpBinding
import com.leo.mylogin.login.data.utils.Output
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        setObserver()
        with(binding) {
            progressSignUp.isIndeterminate = false
            signUpButton.setOnClickListener {
                val email = signUpEmailEdit.text.toString()
                val password = signUpPasswordEdit.text.toString()
                val confirmPassword = confirmPasswordEdit.text.toString()
                validatePassword(email, password, confirmPassword)
            }
        }
    }

    private fun setToolbar() {
        binding.toolbarSignUp.setNavigationOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            loginViewModel.userState.collect { output ->
                when(output) {
                    is Output.Success -> goToHome()
                    is Output.Failure -> showError()
                    Output.Loading -> setLoading()
                    else -> {}
                }
            }
        }
    }

    private fun goToHome() {
        findNavController().navigate(R.id.homeTestActivity)
    }

    private fun showError() {
        Snackbar.make(
            binding.root,
            R.string.text_login_error,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun setLoading() {
        binding.progressSignUp.isIndeterminate = true
    }

    private fun validatePassword(email: String, password: String, confirmPassword: String) {
        if (password == confirmPassword) {
            setSignUp(email, password)
        } else {
            Snackbar.make(
                binding.root,
                R.string.text_password_comparison,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun setSignUp(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(
                binding.root,
                R.string.notify_validate_form,
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            loginViewModel.signUp(email, password)
        }
    }
}