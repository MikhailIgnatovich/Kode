package com.bulich.misha.kode.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bulich.misha.kode.R
import com.bulich.misha.kode.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import java.lang.RuntimeException
import java.util.*


class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(binding.ivUserPhotoDetails)
            .load(args.userEnttity.avatarUrl)
            .circleCrop()
            .into(binding.ivUserPhotoDetails)

        binding.tvUserNameDetails.text =
            String.format(args.userEnttity.firstName + " " + args.userEnttity.lastName)

        binding.tvUserTagDetails.text = args.userEnttity.userTag

        binding.ivBackButtonDetails.setOnClickListener {
            findNavController().navigateUp()
        }
        Log.d("DATE", args.userEnttity.birthday.toString())

        binding.tvUserDateDetails.text = args.userEnttity.birthday

        binding.tvUserOldDetails.text = parseOld(args.userEnttity.birthday)

        binding.tvUserPhoneDetails.text = parsePhoneNumber(args.userEnttity.phone)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseOld(string: String): String {
        val year = string.takeLast(4)
        val old = Calendar.getInstance()
        val c = old.get(Calendar.YEAR)
        val old1 = c.minus(year.toInt())
        return when (true) {
            old1 in 1..4 -> String.format("$old1 года")
            old1 in 5..20 -> String.format("$old1 лет")
            old1.toString().takeLast(1).toInt() in 1..4 -> String.format("$old1 года")
            else -> String.format("$old1 лет")
        }
    }

    private fun parsePhoneNumber(string: String): String {
        val number = string.filter { it.isDigit() }
        val number1 = String.format("+7 " + "(" + number.take(3) + ") " + number.drop(3).take(3) +
        " " + number.takeLast(4).take(2) + " " + number.takeLast(2))
        return number1
    }

}