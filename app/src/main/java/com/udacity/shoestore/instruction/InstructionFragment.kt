package com.udacity.shoestore.instruction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.InstructionFragmentBinding
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [InstructionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InstructionFragment : Fragment() {

    private lateinit var binding: InstructionFragmentBinding
    private lateinit var viewModel: InstructionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.instruction_fragment, container, false)
        viewModel = ViewModelProvider(this).get(InstructionViewModel::class.java)
        binding.instructionViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.eventNext.observe(viewLifecycleOwner, { hasNext ->
            if (hasNext) {
                findNavController().navigate(InstructionFragmentDirections.actionInstructionFragmentToShoeListFragment())
                viewModel.onNextComplete()
            }
        })
        return binding.root
    }

}