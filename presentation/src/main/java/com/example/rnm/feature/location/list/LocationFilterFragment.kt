package com.example.rnm.feature.location.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.rnm.R
import com.example.rnm.databinding.FragmentLocationFilterBinding

class LocationFilterFragment : DialogFragment(R.layout.fragment_location_filter) {

    private lateinit var binding: FragmentLocationFilterBinding
    private var callback: DialogCallback? = null

    companion object {
        fun newInstance() = LocationFilterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callback = parentFragment as DialogCallback?
        binding = FragmentLocationFilterBinding.inflate(inflater, container, false)
        dialog?.setCanceledOnTouchOutside(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        typeFilter.setSelection(
            requireContext().resources.getStringArray(R.array.location_types)
                .indexOf(LocationListFragment.filterLocation.type)
        )

        buttonOk.setOnClickListener {
            LocationListFragment.filterLocation.apply {
                type = if (typeFilter.selectedItem.toString() != "Any") {
                    typeFilter.selectedItem as String
                } else null
            }
            callback?.onFilter()
            dialog?.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        callback = null
    }

    interface DialogCallback {
        fun onFilter()
    }
}