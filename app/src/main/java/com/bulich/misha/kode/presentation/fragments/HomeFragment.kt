package com.bulich.misha.kode.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bulich.misha.kode.R
import com.bulich.misha.kode.databinding.FragmentHomeBinding
import com.bulich.misha.kode.presentation.adapters.UserListAdapter
import com.bulich.misha.kode.presentation.appComponent
import com.bulich.misha.kode.presentation.factories.HomeViewModelFactory
import com.bulich.misha.kode.presentation.viewmodels.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import java.lang.RuntimeException
import javax.inject.Inject


class HomeFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private lateinit var snackBar: Snackbar
    private var alphabetRadioButton = false
    private var birthdayRadioButton = false
    private var tabSavePosition: TabLayout.Tab? = null

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

        radioButtonStatus()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.toolbarHomeFragment.setOnMenuItemClickListener(this)

        snackBar = snackBarStatus()

        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.swipeRecycler.setOnRefreshListener {
            viewModel.loadUserEntityList()
        }

        if (tabSavePosition != null) {
            binding.tabLayoutCategories.selectTab(tabSavePosition)
            viewModel.userFilterList.observe(viewLifecycleOwner) {
                userAdapter.submitList(
                    viewModel.getDepartmentFilterList(
                        it,
                        tabSavePosition?.position
                    )
                )
            }
        }


        searchInList()


        if (binding.tabLayoutCategories.selectedTabPosition == 0) {
            viewModel.userFilterList.observe(viewLifecycleOwner) {
                if (it != null) {
                    userAdapter.submitList(
                        viewModel.getDepartmentFilterList(
                            it,
                            binding.tabLayoutCategories.selectedTabPosition
                        )
                    )
                }
            }
        }


        tabLayoutListener()

    }

    private fun tabLayoutListener() {
        binding.tabLayoutCategories.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabSavePosition = tab
                viewModel.userFilterList.observe(viewLifecycleOwner) {
                    userAdapter.submitList(viewModel.getDepartmentFilterList(it, tab?.position))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun observeViewModel() {
        viewModel.loadingStatus.observe(viewLifecycleOwner) {
            if (it) {
                snackBar.show()
            } else {
                snackBar.dismiss()
                binding.swipeRecycler.isRefreshing = false
            }
        }

        viewModel.loadingError.observe(viewLifecycleOwner) {
            if (it != null) {
                snackBarApiError(it).show()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToErrorFragment())
            }
        }

        viewModel.internetConnectionStatus.observe(viewLifecycleOwner) {
            if (!it) {
                snackBarInternetStatus().show()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToErrorFragment())
            }
        }

        viewModel.checkListEmpty.observe(viewLifecycleOwner) {
            visibilityCheck(it)
        }
    }

    private fun searchInList() {
        binding.mySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.getFilterSearchList(newText)
                return true
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
            setupRecyclerOnClickListener()
        }
    }

    private fun setupRecyclerOnClickListener() {
        userAdapter.onUserEntityClickListener = {
            findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment2(it))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.filter_item -> {
                findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToSortBottomSheetFragment())
            }
        }
        return true
    }

    private fun radioButtonStatus() {
        val sp: SharedPreferences = requireContext()
            .getSharedPreferences(SortBottomSheetFragment.SHARED_KEY, Context.MODE_PRIVATE)

        alphabetRadioButton = sp.getBoolean(SortBottomSheetFragment.BUTTON_ALPHABET, false)
        birthdayRadioButton = sp.getBoolean(SortBottomSheetFragment.BUTTON_BIRTHDAY, false)

        when (true) {
            alphabetRadioButton -> viewModel.setSortMode(false)
            birthdayRadioButton -> viewModel.setSortMode(true)
            else -> viewModel.setSortMode(false)
        }


    }

    private fun snackBarStatus(): Snackbar {
        return Snackbar.make(
            binding.ivSearchEmpty,
            R.string.snackBarStatus,
            Snackbar.LENGTH_INDEFINITE
        )
            .setBackgroundTint(resources.getColor(R.color.background_snackBar_color_status))
    }

    private fun snackBarApiError(string: String): Snackbar {
        return Snackbar.make(
            binding.ivSearchEmpty,
            R.string.snackBarApiError,
            Snackbar.LENGTH_LONG
        )
            .setBackgroundTint(resources.getColor(R.color.background_snackBar_color_error))
    }

    private fun snackBarInternetStatus(): Snackbar {
        return Snackbar.make(
            binding.ivSearchEmpty,
            R.string.snackBarInternetError,
            Snackbar.LENGTH_LONG
        )
            .setBackgroundTint(resources.getColor(R.color.background_snackBar_color_error))
    }

}