package com.example.ekonewka.ui.fertilize

import androidx.lifecycle.ViewModelProvider
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
import com.example.ekonewka.databinding.FragmentFertilizeBinding
import com.example.ekonewka.ui.WaterAdapter

class FertilizeFragment : Fragment() {

    private lateinit var viewModel: FertilizeViewModel
    private var _binding: FragmentFertilizeBinding? = null
    private val binding get() = _binding!!
    private val mainVm by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFertilizeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fertilizeRV.layoutManager = LinearLayoutManager(requireContext())
        mainVm.getAllFertilize().observe(viewLifecycleOwner){ flower ->
            binding.fertilizeRV.adapter = WaterAdapter(flower,3) { flower, position ->
                mainVm.selectFlower(flower)
                (requireActivity() as MainActivity).setNavMainVisibility(false)
                findNavController().navigate(R.id.editFlower)
            }

        }

    }


}