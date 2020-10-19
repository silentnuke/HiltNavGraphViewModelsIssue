package com.silentnuke.navGraphViewmodels.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.silentnuke.navGraphViewmodels.R

class SecondFragment : Fragment() {

    private val viewModel by navGraphViewModels<MainViewModel>(R.id.nested){
        defaultViewModelProviderFactory
    }
    private val viewModel2 by navGraphViewModels<MainViewModel2>(R.id.nested)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, {
            view.findViewById<TextView>(R.id.message).text = "viewModel: $it"
        })
        viewModel2.state.observe(viewLifecycleOwner, {
            view.findViewById<TextView>(R.id.message2).text = "viewModel2: $it"
        })
    }

}