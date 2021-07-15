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
import com.example.libraandroid.databinding.FragmentRegFormInvitationBinding
import com.example.libraandroid.extension.formatTime
import com.example.libraandroid.extension.navigateSafe
import com.example.libraandroid.extension.setOnClickListener
import com.example.libraandroid.ui.register.RegisterViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [RegFormInvitationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegFormInvitationFragment : Fragment() {
    private val viewModel: RegisterViewModel by navGraphViewModels(R.navigation.nav_graph_registration_form)

    private var _binding: FragmentRegFormInvitationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegFormInvitationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reqCodeButton = binding.btnRegformRequestCode
        val nextButton = binding.btnRegformNext
        val codeEditText = binding.edittextRegformCode
        val inviteEditText = binding.edittextRegformInvitationEmail
        val countdownText = binding.textRegformCodeCountdown

        viewModel.formState.observe(viewLifecycleOwner, { formState ->
             formState.invitationEmailError?.let {
                inviteEditText.error = getString(it)
             }
             formState.invitationCodeError?.let {
                 codeEditText.error = getString(it)
             }
        })

        viewModel.reqCodeCooldown.observe(viewLifecycleOwner, { countdown ->
            if (countdown != null) {
                countdownText.text = countdown.formatTime()
                countdownText.visibility = View.VISIBLE
                reqCodeButton.isEnabled = false
            } else {
                countdownText.visibility = View.GONE
                reqCodeButton.isEnabled = true
            }
        })

        reqCodeButton.setOnClickListener {
            viewModel.requestInvitationCode(
                inviteEditText.text.toString()
            )
        }

        nextButton.setOnClickListener(Constant.DEBOUNCE_TIME) {
            if (viewModel.updateInvitation(
                inviteEditText.text.toString(),
                codeEditText.text.toString()
            )) {
                val action = RegFormInvitationFragmentDirections.actionRegFormInvitationFragmentToRegFormNameFragment()
                this.findNavController().navigateSafe(R.id.regFormInvitationFragment, action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}