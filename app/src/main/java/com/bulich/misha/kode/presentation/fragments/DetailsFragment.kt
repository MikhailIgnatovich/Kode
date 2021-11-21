package com.bulich.misha.kode.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bulich.misha.kode.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import java.lang.RuntimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {

            Glide.with(ivUserPhotoDetails)
                .load(args.userEnttity.avatarUrl)
                .circleCrop()
                .into(ivUserPhotoDetails)

            tvUserNameDetails.text =
                String.format(args.userEnttity.firstName.replaceFirstChar { it.uppercase() }
                        + " " + args.userEnttity.lastName.replaceFirstChar { it.uppercase() })

            tvUserPositionDetails.text = args.userEnttity.position

            tvUserTagDetails.text = args.userEnttity.userTag

            ivBackButtonDetails.setOnClickListener {
                findNavController().navigateUp()
            }

            tvUserDateDetails.text = parseBirthdayDate(args.userEnttity.birthday)

            tvUserOldDetails.text = parseOld(parseBirthdayDate(args.userEnttity.birthday))

            tvUserPhoneDetails.text = parsePhoneNumber(args.userEnttity.phone)

            tvUserPhoneDetails.setOnClickListener {
                val phoneNumber = "+7" + args.userEnttity.phone.filter { it.isDigit() }
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
                startActivity(intent)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseBirthdayDate(date: LocalDate): String {

        return DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru")).format(date)
    }

    private fun parseOld(string: String): String {
        val yearUser = string.takeLast(4)
        val currentDate = Calendar.getInstance()
        val currentYear = currentDate.get(Calendar.YEAR)
        val old = currentYear.minus(yearUser.toInt())
        return when (true) {
            old in 1..4 -> String.format("$old года")
            old in 5..20 -> String.format("$old лет")
            old.toString().takeLast(1).toInt() in 1..4 -> String.format("$old года")
            else -> String.format("$old лет")
        }
    }

    private fun parsePhoneNumber(string: String): String {
        val number = string.filter { it.isDigit() }
        return String.format(
            "+7 " + "(" + number.take(3) + ") " + number.drop(3).take(3) +
                    " " + number.takeLast(4).take(2) + " " + number.takeLast(2)
        )
    }

}