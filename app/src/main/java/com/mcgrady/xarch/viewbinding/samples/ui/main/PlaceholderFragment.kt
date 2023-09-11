package com.mcgrady.xarch.viewbinding.samples.ui.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mcgrady.xarch.viewbinding.extensions.viewBinding
import com.mcgrady.xarch.viewbinding.samples.R
import com.mcgrady.xarch.viewbinding.samples.databinding.FragmentTabbedViewsBinding

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment(R.layout.fragment_tabbed_views) {

    private lateinit var pageViewModel: PageViewModel

    private val binding by viewBinding(FragmentTabbedViewsBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView: TextView = binding.sectionLabel
        pageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}