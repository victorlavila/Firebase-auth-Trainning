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
import com.leo.mylogin.databinding.FragmentLoginBinding
import com.leo.mylogin.login.data.utils.Output
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        with(binding) {
            progressLogin.isIndeterminate = false
            loginButton.setOnClickListener {
                val email = emailEdit.text.toString()
                val password = passwordEdit.text.toString()
                setLogin(email, password)
            }
            signUpButton.setOnClickListener {
                redirectToSignUp()
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            loginViewModel.userState.collect { output ->
                when(output) {
                    is Output.Success -> { goToHome() }
                    is Output.Failure -> { showError() }
                    Output.Loading -> setProgressVisibility()
                    else -> {}
                }
            }
        }
    }

    private fun setLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(
                binding.root,
                R.string.notify_validate_form,
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            loginViewModel.login(email, password)
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

    private fun redirectToSignUp() {
        findNavController().navigate(R.id.signUpFragment)
    }

    private fun setProgressVisibility() {
        binding.progressLogin.isIndeterminate = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}