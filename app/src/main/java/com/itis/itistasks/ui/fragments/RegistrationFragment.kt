package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.itistasks.R
import com.itis.itistasks.data.db.repositories.UserRepository
import com.itis.itistasks.data.model.UserModel
import com.itis.itistasks.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val viewBinding : FragmentRegistrationBinding
    by viewBinding(FragmentRegistrationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.run {
            btnSubmit.setOnClickListener {
                val name : String = etName.text.toString()
                val email : String = etEmail.text.toString()
                val phone : String = etPhone.text.toString()
                val password : String = etPassword.text.toString()
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(),
                        getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val user = UserModel(
                    name = name,
                    email = email,
                    phone = phone,
                    password = password,
                    deletedDate = null
                )

                lifecycleScope.launch {
                    if (!UserRepository.add(user)) {
                        Toast.makeText(requireContext(),
                            getString(R.string.phone_or_email_already_exists),
                            Toast.LENGTH_SHORT).show()
                    }
                    else {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, AuthorizationFragment(),
                                AuthorizationFragment.AUTHORIZATION_FRAGMENT_TAG)
                            .commit()
                    }
                }
            }
        }
    }

    companion object {
        const val REGISTRATION_FRAGMENT_TAG = "REGISTRATION_FRAGMENT_TAG"
    }
}