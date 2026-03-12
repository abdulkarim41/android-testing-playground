package com.abdulkarim.android_testing_playground.presentation.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.abdulkarim.android_testing_playground.R
import com.abdulkarim.android_testing_playground.databinding.FragmentProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_product) {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProductBinding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}