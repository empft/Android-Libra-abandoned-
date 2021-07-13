package com.example.libraandroid.ui.register.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.libraandroid.R
import com.example.libraandroid.ui.register.RegisterViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [RegFormInvitationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegFormInvitationFragment : Fragment() {
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg_form_invitation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)


    }
}