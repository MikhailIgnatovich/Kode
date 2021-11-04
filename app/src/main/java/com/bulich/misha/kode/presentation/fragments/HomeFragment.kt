package com.bulich.misha.kode.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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


class HomeFragment : Fragment(), Toolbar.OnMenuItemClickListener {

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
        setHasOptionsMenu(true)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        binding.toolbarHomeFragment.inflateMenu(R.menu.filter_menu)
        binding.toolbarHomeFragment.setOnMenuItemClickListener(this)
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

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.filter_item -> {findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSortBottomSheetFragment())}
        }
        return true
    }

}