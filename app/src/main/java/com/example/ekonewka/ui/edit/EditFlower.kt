package com.example.ekonewka.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.ekonewka.MainActivity
import com.example.ekonewka.MainViewModel
import com.example.ekonewka.data.models.Flower
import com.example.ekonewka.databinding.FragmentAddFlowerBinding
import com.example.ekonewka.databinding.FragmentEditFlowerBinding

import com.example.ekonewka.ui.date_picker.FlowerDatePicker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class EditFlower : Fragment() {
    private val viewModel by viewModels<EditFlowerViewModel>()
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: FragmentEditFlowerBinding? = null
    private val binding get() = _binding!!
    private val sdf = SimpleDateFormat("dd-MM-yyyy", Locale("pl_"))
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (requireActivity() as MainActivity).setNavMainVisibility(true)
            isEnabled = false
            requireActivity().onBackPressed()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditFlowerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPressed()
        setFlowerData(mainVm.getSelectedFlower()!!)
        binding.saveBT.setOnClickListener{
            val flower = createFlower()
            mainVm.updateFlower(flower)
            requireActivity().onBackPressed()
        }
        binding.waterFB.isVisible = binding.waterCB.isChecked
        binding.sprinkleFB.isVisible = binding.sprinkleCB.isChecked
        binding.FertilizeFB.isVisible = binding.fertilizeCB.isChecked
        binding.waterFB.setOnClickListener{
            viewModel.waterLPD = Calendar.getInstance().timeInMillis
            val flower = createFlower()
            mainVm.updateFlower(flower)
            requireActivity().onBackPressed()
        }
        binding.sprinkleFB.setOnClickListener{
            viewModel.sprinkleLPD = Calendar.getInstance().timeInMillis
            val flower = createFlower()
            mainVm.updateFlower(flower)
            requireActivity().onBackPressed()
        }
        binding.FertilizeFB.setOnClickListener{
            viewModel.fertilizeLPD = Calendar.getInstance().timeInMillis
            val flower = createFlower()
            mainVm.updateFlower(flower)
            requireActivity().onBackPressed()
        }
        binding.deleteBT.setOnClickListener{
            mainVm.getSelectedFlower()?.let { flower ->
                mainVm.deleteFlower(listOf(flower))
                mainVm.unselectFlower()
            }
            requireActivity().onBackPressed()
        }
        binding.backIV.setOnClickListener{
            requireActivity().onBackPressed()
        }

        binding.lastWaterDatePH.setOnClickListener{
            showDatePickerDialogWater()
        }
        binding.lastSprinkledatePH.setOnClickListener {
            showDatePickerDialogSprinkle()

        }
        binding.lastFertilizeDatePH.setOnClickListener{
            showDatePickerDialogFertilize()
        }
    }

    private fun setFlowerData(flower: Flower) {
        setCurrentName(flower.name)
        if(flower.water){
            binding.waterCB.isChecked = true
            binding.waterET.setText(getDays(flower.water_fp,flower.water_schedule).toInt().toString())
            binding.lastWaterDatePH.text = sdf.format(Date(flower.water_fp))
        }
        if(flower.sprinkle){
            binding.sprinkleCB.isChecked = flower.sprinkle
            binding.sprinkleET.setText(getDays(flower.sprinkle_fp,flower.sprinkle_schedule).toInt().toString())
            binding.lastSprinkledatePH.text = sdf.format(Date(flower.sprinkle_fp))
        }
        if(flower.fertilize){
            binding.fertilizeCB.isChecked = flower.fertilize
            binding.fertilizeET.setText(getDays(flower.fertilize_fp,flower.fertilize_schedule).toInt().toString())
            binding.lastFertilizeDatePH.text = sdf.format(Date(flower.fertilize_fp))
        }
        viewModel.fertilizeLPD = flower.fertilize_fp
        viewModel.fertilizeNPD = flower.fertilize_schedule
        viewModel.waterLPD = flower.water_fp
        viewModel.waterNPD = flower.water_schedule
        viewModel.sprinkleLPD = flower.sprinkle_fp
        viewModel.sprinkleNPD = flower.sprinkle_schedule
    }

    private fun setCurrentName(name : String) {
        binding.editText.setText(name)
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,onBackPressedCallback)
    }

    private fun createFlower(): Flower {

        val name = binding.editText.text.toString()
        var water = false
        var fertilize = false
        var sprinkle = false
        val cw = Calendar.getInstance()
        val cs = Calendar.getInstance()
        val cf = Calendar.getInstance()


        if(binding.waterCB.isChecked){
            water = true
            val water_schedule = binding.waterET.text.toString().toInt()
            cw.time = Date(viewModel.waterLPD)
            cw.add(Calendar.DATE,water_schedule)
            viewModel.waterNPD = cw.timeInMillis
            (activity as MainActivity?)!!.scheduleNotification(viewModel.waterNPD)
        }
        if(binding.fertilizeCB.isChecked){
            fertilize = true
            val fertilize_schedule = binding.fertilizeET.text.toString().toInt()
            cf.time = Date(viewModel.fertilizeLPD)
            cf.add(Calendar.DATE,fertilize_schedule)
            viewModel.fertilizeNPD = cf.timeInMillis
            (activity as MainActivity?)!!.scheduleNotification(viewModel.fertilizeNPD)
        }
        if(binding.sprinkleCB.isChecked){
            sprinkle = true
            val sprinkle_schedule = binding.sprinkleET.text.toString().toInt()
            cs.time = Date(viewModel.sprinkleLPD)
            cs.add(Calendar.DATE,sprinkle_schedule)
            viewModel.sprinkleNPD = cs.timeInMillis
            (activity as MainActivity?)!!.scheduleNotification(viewModel.sprinkleNPD)
        }


        return Flower(mainVm.getSelectedFlower()!!.uid,name,water,sprinkle,fertilize,viewModel.waterLPD,viewModel.sprinkleLPD,viewModel.fertilizeLPD,viewModel.waterNPD,viewModel.sprinkleNPD,viewModel.fertilizeNPD)
    }

    private fun showDatePickerDialogWater() {
        val newDatePicker = FlowerDatePicker { day, month, year ->

            val date = Calendar.getInstance()
            date.set(year,month,day)
            viewModel.waterLPD = date.timeInMillis
            binding.lastWaterDatePH.text = sdf.format(Date(viewModel.waterLPD))

        }
        newDatePicker.show(parentFragmentManager, "DatePicker")

    }
    private fun showDatePickerDialogSprinkle() {
        val newDatePicker = FlowerDatePicker { day, month, year ->

            val date = Calendar.getInstance()
            date.set(year,month,day)
            viewModel.sprinkleLPD = date.timeInMillis
            binding.lastSprinkledatePH.text = sdf.format(Date(viewModel.sprinkleLPD))
        }

        newDatePicker.show(parentFragmentManager, "DatePicker")
    }
    private fun showDatePickerDialogFertilize() {
        val newDatePicker = FlowerDatePicker { day, month, year ->

            val date = Calendar.getInstance()
            date.set(year,month,day)
            viewModel.fertilizeLPD = date.timeInMillis
            binding.lastFertilizeDateTV.text = sdf.format(Date(viewModel.fertilizeLPD))
        }

        newDatePicker.show(parentFragmentManager, "DatePicker")
    }

    private fun getDays(fp : Long,np : Long ): Long {
        val diff = np - fp
        val days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        return days
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}