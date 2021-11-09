package com.bulich.misha.kode.presentation.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.bulich.misha.kode.R
import com.bulich.misha.kode.databinding.FragmentSortBottomSheetBinding
import com.bulich.misha.kode.presentation.appComponent
import com.bulich.misha.kode.presentation.factories.HomeViewModelFactory
import com.bulich.misha.kode.presentation.viewmodels.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.RuntimeException
import javax.inject.Inject


class SortBottomSheetFragment() : BottomSheetDialogFragment() {


    private var _binding: FragmentSortBottomSheetBinding? = null
    private val binding: FragmentSortBottomSheetBinding
        get() = _binding ?: throw RuntimeException("FragmentSortBottomSheetBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSortBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.radioButtonAlphabet.isChecked = updateSharedPrefs(BUTTON_ALPHABET)
        binding.radioButtonBirthday.isChecked = updateSharedPrefs(BUTTON_BIRTHDAY)


        binding.radioButtonAlphabet.setOnCheckedChangeListener { buttonView, isChecked ->
            saveIntoSharedPrefs(BUTTON_ALPHABET, isChecked)
            findNavController().navigateUp()
            dismiss()

        }

        binding.radioButtonBirthday.setOnCheckedChangeListener { buttonView, isChecked ->
            saveIntoSharedPrefs(BUTTON_BIRTHDAY, isChecked)
            findNavController().navigateUp()
            dismiss()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveIntoSharedPrefs(key: String, boolean: Boolean) {
        val sp: SharedPreferences =
            requireContext().getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.apply {
            putBoolean(key, boolean)
        }.apply()
    }

    private fun updateSharedPrefs(nameButton: String): Boolean {
        val sp: SharedPreferences =
            requireContext().getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)
        return sp.getBoolean(nameButton, false)
    }


    companion object {
        const val BUTTON_ALPHABET = "selected"
        const val BUTTON_BIRTHDAY = "birthday"
        const val SHARED_KEY = "shared"
    }
}

