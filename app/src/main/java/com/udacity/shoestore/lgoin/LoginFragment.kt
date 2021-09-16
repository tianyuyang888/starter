package com.udacity.shoestore.lgoin

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.BaseApplication
import com.udacity.shoestore.R
import com.udacity.shoestore.UserViewModel
import com.udacity.shoestore.databinding.LoginFragmentBinding

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {


    private val userViewModel: UserViewModel by activityViewModels {
        SavedStateViewModelFactory(
            BaseApplication.getApplication(),
            this
        )
    }

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        userViewModel.eventRegister.observe(viewLifecycleOwner, { hasRegister ->
            if (hasRegister) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
                userViewModel.onRegisterComplete()
            }
        })
        userViewModel.eventSignIn.observe(viewLifecycleOwner, { hasSignIn ->
            if (hasSignIn) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
                userViewModel.onSignInComplete()
            }
        })
        return binding.root
    }



}