package com.example.libraandroid.ui.register.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.unit.Constraints
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.libraandroid.R
import com.example.libraandroid.constant.Constant
import com.example.libraandroid.databinding.FragmentRegFormNameBinding
import com.example.libraandroid.databinding.FragmentRegFormPasswordBinding
import com.example.libraandroid.extension.navigateSafe
import com.example.libraandroid.extension.setOnClickListener
import com.example.libraandroid.ui.register.RegisterViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [RegFormPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegFormPasswordFragment : Fragment() {
    private val viewModel: RegisterViewModel by navGraphViewModels(R.navigation.nav_graph_registration_form)

    private var _binding: FragmentRegFormPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegFormPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nextButton = binding.btnRegformNext
        val passwordEditText = binding.edittextRegformPassword

        viewModel.formState.observe(viewLifecycleOwner, { formState ->
            formState.passwordError?.let {
                passwordEditText.error = getString(it)
            }
        })

        nextButton.setOnClickListener(Constant.DEBOUNCE_TIME) {
            if (viewModel.updatePassword(passwordEditText.text.toString())) {
                val action = RegFormPasswordFragmentDirections.actionRegFormPasswordFragmentToRegFormEmailFragment()
                this.findNavController().navigateSafe(R.id.regFormPasswordFragment, action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}