package com.example.libraandroid.ui.register.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.example.libraandroid.R
import com.example.libraandroid.databinding.FragmentRegFormInvitationBinding
import com.example.libraandroid.databinding.FragmentRegFormLoadingBinding
import com.example.libraandroid.ui.register.RegisterViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [RegFormLoadingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegFormLoadingFragment : Fragment() {
    private val viewModel: RegisterViewModel by navGraphViewModels(R.navigation.nav_graph_registration_form)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reg_form_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}