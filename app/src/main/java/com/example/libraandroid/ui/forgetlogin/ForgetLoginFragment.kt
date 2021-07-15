package com.example.libraandroid.ui.forgetlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.libraandroid.R
import com.example.libraandroid.databinding.FragmentForgetLoginBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ForgetLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgetLoginFragment : Fragment() {
    private val viewModel: ForgetLoginViewModel by viewModels()
    private var _binding: FragmentForgetLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForgetLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailEditText = binding.edittextForgetLoginEmail
        val usernameEditText = binding.edittextForgetLoginUsername
        val remindNameButton = binding.btnForgetLoginRemindUsername
        val resetPassButton = binding.btnForgetLoginResetPassword


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}