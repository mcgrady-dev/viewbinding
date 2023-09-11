package com.mcgrady.xarch.viewbinding.sample

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.mcgrady.xarch.viewbinding.extensions.viewBinding
import com.mcgrady.xarch.viewbinding.sample.ui.main.SectionsPagerAdapter
import com.mcgrady.xarch.viewbinding.sample.databinding.ActivityTabbedViewsBinding

class TabbedViewsActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityTabbedViewsBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}