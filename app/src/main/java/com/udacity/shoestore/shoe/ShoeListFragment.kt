package com.udacity.shoestore.shoe

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.BaseApplication
import com.udacity.shoestore.R
import com.udacity.shoestore.UserViewModel
import com.udacity.shoestore.databinding.ItemShoeBinding
import com.udacity.shoestore.databinding.ShoeListFragmentBinding
import kotlinx.android.synthetic.main.shoe_list_fragment.view.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [ShoeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoeListFragment : Fragment() {

    private lateinit var binding: ShoeListFragmentBinding
    private lateinit var viewModel: ShoeListViewModel
    private val shareShoeListViewModel: ShareShoeListViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels {
        SavedStateViewModelFactory(
            BaseApplication.getApplication(),
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView")
        //judge login status
        if (userViewModel.loginSuccess.value != true) {
            findNavController().navigate(ShoeListFragmentDirections.actionShoeListFragmentToLoginFragment())
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.shoe_list_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ShoeListViewModel::class.java)
        binding.shoeListViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        viewModel.eventAddShoe.observe(viewLifecycleOwner, { hasAddShoe ->
            if (hasAddShoe) {
                findNavController().navigate(ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment())
                viewModel.onAddShoeComplete()
            }
        })

        shareShoeListViewModel.shoeList.observe(viewLifecycleOwner, { list ->
            list.forEach { item ->
                val itemShoeBinding = DataBindingUtil.inflate<ItemShoeBinding>(
                    layoutInflater,
                    R.layout.item_shoe,
                    null,
                    false
                )
                itemShoeBinding.shoe = item
                binding.root.ll_list.addView(itemShoeBinding.root)
            }

        })

        setHasOptionsMenu(true)

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.log_out) {
            userViewModel.logOut()
            findNavController().navigate(ShoeListFragmentDirections.actionShoeListFragmentToLoginFragment())
        }
        return super.onOptionsItemSelected(item)
    }

}