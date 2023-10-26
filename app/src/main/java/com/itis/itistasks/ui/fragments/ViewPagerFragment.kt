package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.itistasks.R
import com.itis.itistasks.adapter.FragmentAdapter
import com.itis.itistasks.databinding.FragmentViewPagerBinding

class ViewPagerFragment(private val itemCount: Int) :
    Fragment(R.layout.fragment_view_pager) {

    private var binding: FragmentViewPagerBinding? = null
    private var vpAdapter: FragmentAdapter? = null
    private var answers: Array<Int?> = arrayOfNulls(itemCount)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vpAdapter = FragmentAdapter(
            parentFragmentManager,
            lifecycle,
            itemCount,
            answers
        )
        binding = FragmentViewPagerBinding.bind(view)
        binding?.vpFragment?.adapter = vpAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val VIEW_PAGER_FRAGMENT_TAG: String = "VIEW_PAGER_FRAGMENT_TAG"
    }
}