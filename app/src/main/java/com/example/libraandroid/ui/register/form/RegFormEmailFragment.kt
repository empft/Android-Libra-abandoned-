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
import com.example.libraandroid.databinding.FragmentRegFormEmailBinding
import com.example.libraandroid.databinding.FragmentRegFormPasswordBinding
import com.example.libraandroid.extension.navigateSafe
import com.example.libraandroid.extension.setOnClickListener
import com.example.libraandroid.ui.register.RegisterViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [RegFormEmailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegFormEmailFragment : Fragment() {
    private val viewModel: RegisterViewModel by navGraphViewModels(R.navigation.nav_graph_registration_form)

    private var _binding: FragmentRegFormEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegFormEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val doneButton = binding.btnRegformDone
        val emailEditText = binding.edittextRegformEmail

        viewModel.formState.observe(viewLifecycleOwner, { formState ->
            formState.emailError?.let {
                emailEditText.error = getString(it)
            }
        })

        doneButton.setOnClickListener(Constant.DEBOUNCE_TIME) {
            if (viewModel.updateEmail(emailEditText.text.toString()) && viewModel.submitForm()) {
                val action = RegFormEmailFragmentDirections.actionRegFormEmailFragmentToRegFormLoadingFragment()
                findNavController().navigateSafe(R.id.regFormEmailFragment, action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}