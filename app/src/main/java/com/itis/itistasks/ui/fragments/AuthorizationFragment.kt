package com.itis.itistasks.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.itistasks.R
import com.itis.itistasks.data.CurrentUser
import com.itis.itistasks.data.db.entities.UserEntity
import com.itis.itistasks.data.db.repositories.UserRepository
import com.itis.itistasks.databinding.FragmentAuthorizationBinding
import com.itis.itistasks.ui.fragments.FilmsFragment.Companion.FILMS_FRAGMENT_TAG
import com.itis.itistasks.ui.fragments.RegistrationFragment.Companion.REGISTRATION_FRAGMENT_TAG
import kotlinx.coroutines.launch

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    private val viewBinding : FragmentAuthorizationBinding
    by viewBinding(FragmentAuthorizationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.run {
            btnRegister.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .add(R.id.container, RegistrationFragment(), REGISTRATION_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit()
            }

            btnSubmit.setOnClickListener {
                lifecycleScope.launch {
                    val email: String = etEmail.text.toString()
                    val password: String = etPassword.text.toString()
                    if (UserRepository.checkUserData(email, password)) {
                        val user = UserRepository.getUserByData(email, password)
                        if (user.deletedDate != null && user.deletedDate > 0) {
                            val deletedDate = user.deletedDate
                            val currentDate = System.currentTimeMillis()
                            val elapsedDays = (currentDate - deletedDate) / (24 * 60 * 60 * 1000)
                            if (elapsedDays >= 7) {
                                lifecycleScope.launch {
                                    UserRepository.delete(user)
                                    activity?.runOnUiThread {
                                        Toast.makeText(
                                            requireContext(),
                                            getString(R.string.account_deleted),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        etEmail.text?.clear()
                                        etPassword.text?.clear()
                                    }

                                }
                            } else {
                                showRestoreDialog(user)
                            }
                        } else {
                            CurrentUser.set(user.userId)
                            parentFragmentManager.beginTransaction()
                                .add(R.id.container, FilmsFragment(), FILMS_FRAGMENT_TAG)
                                .commit()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.wrong_user_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showRestoreDialog(user: UserEntity) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.this_account_was_deleted))
            .setPositiveButton(getString(R.string.recover)) { _, _ ->
                CurrentUser.set(user.userId)
                lifecycleScope.launch {
                    UserRepository.setDeletionDate(user.userId, 0)
                }
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        FilmsFragment(),
                        FILMS_FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }
            .setNegativeButton(getString(R.string.delete)) { _, _ ->
                lifecycleScope.launch {
                    UserRepository.delete(user)
                }
                Toast.makeText(
                    requireContext(),
                    getString(R.string.account_deleted),
                    Toast.LENGTH_SHORT
                ).show()
                viewBinding.etEmail.text?.clear()
                viewBinding.etPassword.text?.clear()
            }
            .create()
            .show()
    }

    companion object {
        const val AUTHORIZATION_FRAGMENT_TAG = "AUTHORIZATION_FRAGMENT_TAG"
    }
}