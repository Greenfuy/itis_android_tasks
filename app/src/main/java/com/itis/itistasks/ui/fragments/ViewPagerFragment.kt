package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
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
        binding?.run {
            vpFragment.adapter = vpAdapter
            vpFragment.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                var currPosition = 0
                var currState = 0

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    if (
                        currState == ViewPager2.SCROLL_STATE_DRAGGING
                        && positionOffsetPixels == 0
                        ) {
                        if (currPosition == 0) {
                            vpFragment.currentItem = itemCount - 1
                        } else if (currPosition == itemCount - 1) {
                            vpFragment.currentItem = 0
                        }
                    }
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    currPosition = position
                    super.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    currState = state
                    super.onPageScrollStateChanged(state)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val VIEW_PAGER_FRAGMENT_TAG: String = "VIEW_PAGER_FRAGMENT_TAG"
    }
}