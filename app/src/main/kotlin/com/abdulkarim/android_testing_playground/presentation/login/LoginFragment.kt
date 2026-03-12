package com.abdulkarim.android_testing_playground.presentation.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.abdulkarim.android_testing_playground.R
import com.abdulkarim.android_testing_playground.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_productFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}