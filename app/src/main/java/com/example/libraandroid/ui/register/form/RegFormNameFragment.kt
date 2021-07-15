package com.example.libraandroid.ui.register.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.libraandroid.R
import com.example.libraandroid.constant.Constant
import com.example.libraandroid.databinding.FragmentRegFormNameBinding
import com.example.libraandroid.extension.navigateSafe
import com.example.libraandroid.extension.setOnClickListener
import com.example.libraandroid.ui.register.RegisterViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [RegFormNameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegFormNameFragment : Fragment() {

    private val viewModel: RegisterViewModel by navGraphViewModels(R.navigation.nav_graph_registration_form)

    private var _binding: FragmentRegFormNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegFormNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nextButton = binding.btnRegformNext
        val usernameEditText = binding.edittextRegformUsername
        val displayNameEditText = binding.edittextRegformDisplayName

        viewModel.formState.observe(viewLifecycleOwner, { formState ->
            formState.usernameError?.let {
                usernameEditText.error = getString(it)
            }
            formState.displayNameError?.let {
                displayNameEditText.error = getString(it)
            }
        })

        nextButton.setOnClickListener(Constant.DEBOUNCE_TIME) {
            if (viewModel.updateName(
                usernameEditText.text.toString(),
                displayNameEditText.text.toString()
            )) {
                val action = RegFormNameFragmentDirections.actionRegFormNameFragmentToRegFormPasswordFragment()
                this.findNavController().navigateSafe(R.id.regFormNameFragment, action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}