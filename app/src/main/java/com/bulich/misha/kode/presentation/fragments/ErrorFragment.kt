package com.bulich.misha.kode.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bulich.misha.kode.databinding.FragmentErrorBinding
import java.lang.RuntimeException


class ErrorFragment : Fragment() {

    private var _binding: FragmentErrorBinding? = null
    private val binding: FragmentErrorBinding
        get() = _binding ?: throw RuntimeException("FragmentErrorBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tv3.setOnClickListener {
            findNavController().navigate(ErrorFragmentDirections.actionErrorFragmentToHomeFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}