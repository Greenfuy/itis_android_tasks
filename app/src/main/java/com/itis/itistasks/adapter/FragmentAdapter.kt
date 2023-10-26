package com.itis.itistasks.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.itis.itistasks.ui.fragments.QuestionFragment

class FragmentAdapter(
    manager: FragmentManager,
    lifecycle: Lifecycle,
    private val itemCount: Int,
    private val answers: Array<Int?>
) : FragmentStateAdapter(manager, lifecycle) {

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment {
        return QuestionFragment(position, answers, position == itemCount - 1)
    }
}