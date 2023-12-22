package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.itistasks.R
import com.itis.itistasks.data.CurrentUser
import com.itis.itistasks.data.db.repositories.UserRepository
import com.itis.itistasks.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewBinding : FragmentProfileBinding
    by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.run {
            lifecycleScope.launch {
                val currentUser = UserRepository.getUserById(CurrentUser.get())
                tvName.text = currentUser.name
                tvPhone.text = currentUser.email
                tvEmail.text = currentUser.phone

                etPhone.setText(currentUser.phone)
                etPassword.setText(currentUser.password)
            }

            btnSubmit.setOnClickListener {
                lifecycleScope.launch {
                    val currentUser = UserRepository.getUserById(CurrentUser.get())
                    if (etPhone.text.isNotEmpty()) {
                        if (UserRepository.updatePhone(currentUser.userId, etPhone.text.toString())) {
                            tvPhone.text = etPhone.text
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.phone_number_changed),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.phone_number_already_in_use), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    if (etPassword.text.isNotEmpty()) {
                        UserRepository.updatePassword(currentUser.userId, etPassword.text.toString())
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.password_changed),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
            btnLogout.setOnClickListener {
                CurrentUser.set(-1)
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        AuthorizationFragment(),
                        AuthorizationFragment.AUTHORIZATION_FRAGMENT_TAG
                    )
                    .commit()
            }

            btnDeleteAccount.setOnClickListener {
                lifecycleScope.launch {
                    UserRepository.setDeletionDate(
                        CurrentUser.get(),
                        System.currentTimeMillis()
                    )
                    CurrentUser.set(-1)
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            AuthorizationFragment(),
                            AuthorizationFragment.AUTHORIZATION_FRAGMENT_TAG
                        )
                        .commit()
                }
            }
        }
    }

    companion object {
        const val PROFILE_FRAGMENT_TAG = "PROFILE_FRAGMENT_TAG"
    }
}