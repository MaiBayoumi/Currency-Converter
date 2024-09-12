package com.example.currencyconverter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.adapter.SpinnerAdapter
import com.example.currencyconverter.databinding.FragmentCalculatorBinding
import com.example.currencyconverter.models.Currency
import com.example.currencyconverter.utils.MyViewModels
import java.lang.Exception

class CalculatorFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentCalculatorBinding
    lateinit var myViewModel: MyViewModels
    lateinit var list: ArrayList<Currency>
    lateinit var spinnerAdapter: SpinnerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using ViewBinding
        binding = FragmentCalculatorBinding.inflate(layoutInflater)
        list = ArrayList()

        // Initialize ViewModel
        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModels::class.java)

        // Observe data from ViewModel
        myViewModel.getUsers().observe(viewLifecycleOwner) { currencyList ->
            list.addAll(currencyList)

            // Adjust the list values for missing prices
            list.forEach {
                if (it.nbu_cell_price.isEmpty()) {
                    it.nbu_cell_price = it.cb_price
                }
                if (it.nbu_buy_price.isEmpty()) {
                    it.nbu_buy_price = it.cb_price
                }
            }

            // Add UZS currency to the list
            list.add(Currency(list.last().date, "1", "UZS", "1", "1", "O'zbek so'mi"))

            // Initialize and set SpinnerAdapter
            spinnerAdapter = SpinnerAdapter(list)
            binding.spinner1.adapter = spinnerAdapter
            binding.spinner2.adapter = spinnerAdapter

            // Set default selection to UZS
            binding.spinner1.setSelection(list.size - 1)
            binding.spinner2.setSelection(list.size - 1)
        }

        // Handle swap button click
        binding.imageChangeV.setOnClickListener {
            val selectedPos = binding.spinner1.selectedItemPosition
            binding.spinner1.setSelection(binding.spinner2.selectedItemPosition)
            binding.spinner2.setSelection(selectedPos)
            calculation()
        }

        // Add text change listener for the input field
        binding.edt1.addTextChangedListener {
            calculation()
        }

        return binding.root
    }

    // Perform currency conversion calculation
    private fun calculation() {
        if (binding.edt1.text.isNotEmpty()) {
            val inputAmount = binding.edt1.text.toString().toDouble()
            val buyPrice = list[binding.spinner1.selectedItemPosition].nbu_buy_price.toDouble()
            val sellPrice = list[binding.spinner1.selectedItemPosition].nbu_cell_price.toDouble()

            val totalSellAmount = sellPrice * inputAmount
            val totalBuyAmount = buyPrice * inputAmount

            val convertedSellAmount = totalSellAmount / list[binding.spinner2.selectedItemPosition].nbu_cell_price.toDouble()
            val convertedBuyAmount = totalBuyAmount / list[binding.spinner2.selectedItemPosition].nbu_buy_price.toDouble()

            // Format and display the result
            if (convertedSellAmount.toString().contains('E') || convertedBuyAmount.toString().contains('E')) {
                binding.tvGet.text = convertedBuyAmount.toInt().toString()
                binding.tvSale.text = convertedSellAmount.toInt().toString()
            } else {
                try {
                    binding.tvSale.text = convertedSellAmount.toString().substring(0, convertedSellAmount.toString().indexOf(".") + 3)
                    binding.tvGet.text = convertedBuyAmount.toString().substring(0, convertedBuyAmount.toString().indexOf(".") + 3)
                } catch (e: Exception) {
                    binding.tvSale.text = convertedSellAmount.toString()
                    binding.tvGet.text = convertedBuyAmount.toString()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Set item selection listeners for spinners
        binding.spinner1.onItemSelectedListener = this
        binding.spinner2.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // Update text when spinner item is selected
        binding.tvSale.text = list[position].nbu_cell_price
        binding.tvGet.text = list[position].nbu_buy_price
        calculation()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing when no item is selected
    }
}
