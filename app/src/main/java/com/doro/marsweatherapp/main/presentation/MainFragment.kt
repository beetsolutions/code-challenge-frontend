package com.doro.marsweatherapp.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.doro.marsweatherapp.R
import com.doro.marsweatherapp.BR
import com.doro.marsweatherapp.databinding.FragmentMainBinding
import com.doro.marsweatherapp.main.data.repository.RetryAction
import com.doro.marsweatherapp.main.data.repository.model.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainFragment : Fragment() {

    private val mainAdapter: MainAdapter by inject()
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doDataBinding()

        binding.retryAction = object : RetryAction {
            override fun retry() {
                mainViewModel.fetchWeatherInformation()
            }
        }

        binding.list.adapter = mainAdapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launchWhenCreated {
            mainViewModel.weather.collect {
                binding.resource = it
                if (it.status == Status.SUCCESS) {
                    mainAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun doDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel, mainViewModel)
        binding.executePendingBindings()
    }
}