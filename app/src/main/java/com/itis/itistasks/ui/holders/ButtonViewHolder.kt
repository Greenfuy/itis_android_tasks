package com.itis.itistasks.ui.holders

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.itis.itistasks.adapter.NewsAdapter
import com.itis.itistasks.databinding.ItemButtonBinding
import com.itis.itistasks.ui.fragments.AddNewsDialogFragment

class ButtonViewHolder (
    private val viewBinding: ItemButtonBinding,
    private val fragmentManager: FragmentManager,
    private val adapter: NewsAdapter
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem() {
        viewBinding.btnBottomSheet.setOnClickListener {
            val bottomSheet = AddNewsDialogFragment(adapter)
            bottomSheet.show(fragmentManager, AddNewsDialogFragment.ADD_NEWS_DIALOG_FRAGMENT_TAG)
        }
    }
}