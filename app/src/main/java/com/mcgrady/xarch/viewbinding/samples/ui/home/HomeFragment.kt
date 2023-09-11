package com.mcgrady.xarch.viewbinding.samples.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mcgrady.xarch.viewbinding.extensions.viewBinding
import com.mcgrady.xarch.viewbinding.samples.R
import com.mcgrady.xarch.viewbinding.samples.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }
}