package com.example.ekonewka.ui.water

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ekonewka.MainActivity
import com.example.ekonewka.MainViewModel
import com.example.ekonewka.R

import com.example.ekonewka.databinding.FragmentWaterBinding

import com.example.ekonewka.ui.WaterAdapter


class WaterFragment : Fragment() {
    private lateinit var viewModel: WaterViewModel
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: FragmentWaterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentWaterBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.waterRV.layoutManager = LinearLayoutManager(requireContext())
        mainVm.getAllWater().observe(viewLifecycleOwner){ flower ->
            binding.waterRV.adapter = WaterAdapter(flower,1) { flower, position ->
                mainVm.selectFlower(flower)
                (requireActivity() as MainActivity).setNavMainVisibility(false)
                findNavController().navigate(R.id.editFlower)
            }

        }

    }




}