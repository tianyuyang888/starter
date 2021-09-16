package com.udacity.shoestore.welcome

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
import com.udacity.shoestore.databinding.WelcomeFragmentBinding
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {

    private lateinit var binding: WelcomeFragmentBinding
    private val viewModel: UserViewModel by activityViewModels {
        SavedStateViewModelFactory(
            BaseApplication.getApplication(),
            this
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.welcome_fragment, container, false)
        binding.lifecycleOwner = this
        binding.userViewModel = viewModel

        viewModel.eventNext.observe(viewLifecycleOwner, { hasNext ->
            if (hasNext) {
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToInstructionFragment())
                viewModel.onNextComplete()
            }
        })
        return binding.root
    }

}