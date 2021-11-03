package com.bulich.misha.kode.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Visibility
import com.bulich.misha.kode.R
import com.bulich.misha.kode.databinding.FragmentHomeBinding
import com.bulich.misha.kode.presentation.adapters.UserListAdapter
import com.bulich.misha.kode.presentation.appComponent
import com.bulich.misha.kode.presentation.factories.HomeViewModelFactory
import com.bulich.misha.kode.presentation.viewmodels.HomeViewModel
import com.google.android.material.tabs.TabLayout
import java.lang.RuntimeException
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var factory: HomeViewModelFactory

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    private lateinit var userAdapter: UserListAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding == null")

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.filterNameList(newText)
                return true
            }

        })

        viewModel.checkList.observe(viewLifecycleOwner){
            visibilityCheck(it)
        }


        if (binding.tabLayoutCategories.selectedTabPosition == 0) {
            viewModel.userFilterList.observe(viewLifecycleOwner){
                userAdapter.submitList(it)
            }
        }

        binding.tabLayoutCategories.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                viewModel.userFilterList.observe(viewLifecycleOwner){
                    userAdapter.submitList(viewModel.departmentFilter(it, tab?.position))
                }

//                when(tab?.position){
//                    0 -> {
//                        viewModel.userFilterList.observe(viewLifecycleOwner){
//
//                            userAdapter.submitList(it)
//                        }
//                    }
//                    1 -> {
//                        viewModel.userFilterList.observe(viewLifecycleOwner){
//                            userAdapter.submitList(viewModel.departmentFilter(it, tab.position))
//                        }
//                    }
//                    2 -> {
//                        viewModel.userFilterList.observe(viewLifecycleOwner){
//                            userAdapter.submitList(viewModel.departmentFilter(it, tab.position))
//                        }
//                    }
//                    3 -> {
//                        viewModel.userFilterList.observe(viewLifecycleOwner){
//                            userAdapter.submitList(viewModel.departmentFilter(it, tab.position))
//                        }
//                    }
//                    4 -> {
//                        viewModel.userFilterList.observe(viewLifecycleOwner){
//                            userAdapter.submitList(viewModel.departmentFilter(it, tab.position))
//                        }
//                    }
//                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }

    private fun visibilityCheck(it: Boolean) {
        if (!it) {
            binding.recyclerViewPeople.visibility = View.GONE
            binding.emptySearch.visibility = View.VISIBLE
        } else {
            binding.recyclerViewPeople.visibility = View.VISIBLE
            binding.emptySearch.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        with(binding.recyclerViewPeople) {
            userAdapter = UserListAdapter()
            adapter = userAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}