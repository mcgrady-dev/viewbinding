package com.mcgrady.xlabs.viewbinding.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.mcgrady.xlabs.viewbinding.extensions.viewBinding
import com.mcgrady.xlabs.viewbinding.sample.databinding.ActivityFragmentTransactionBinding
import com.mcgrady.xlabs.viewbinding.sample.ui.home.HomeFragment
import com.mcgrady.xlabs.viewbinding.sample.ui.main.PlaceholderFragment

class FragmentTransactionActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by viewBinding(ActivityFragmentTransactionBinding::inflate)

    private lateinit var homeFragment: Fragment
    private lateinit var placeholderFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeFragment = HomeFragment()
        placeholderFragment = PlaceholderFragment.newInstance(0)

        binding.btnAdd.setOnClickListener(this)
        binding.btnShow.setOnClickListener(this)
        binding.btnHide.setOnClickListener(this)
        binding.btnReplace.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_add -> {
                supportFragmentManager.commit {
                    add(R.id.container, homeFragment)
                }
            }
            R.id.btn_show -> {
                supportFragmentManager.commit {
                    show(homeFragment)
                }
            }
            R.id.btn_hide -> {
                supportFragmentManager.commit {
                    hide(homeFragment)
                }
            }
            R.id.btn_replace -> {
                supportFragmentManager.commit {
                    replace(R.id.container, placeholderFragment)
                }
            }
        }
    }
}