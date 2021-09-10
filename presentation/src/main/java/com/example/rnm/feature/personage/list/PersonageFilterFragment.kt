package com.example.rnm.feature.personage.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.rnm.R
import com.example.rnm.databinding.FragmentPersonageFilterBinding

class PersonageFilterFragment : DialogFragment(R.layout.fragment_personage_filter) {
    private lateinit var binding: FragmentPersonageFilterBinding
    private var callback: DialogCallback? = null

    companion object {
        fun newInstance() = PersonageFilterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callback = parentFragment as DialogCallback?
        binding = FragmentPersonageFilterBinding.inflate(inflater, container, false)
        dialog?.setCanceledOnTouchOutside(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        statusFilter.setSelection(
            requireContext().resources.getStringArray(R.array.statuses)
                .indexOf(PersonageListFragment.filterPersonage.status)
        )
        genderFilter.setSelection(
            requireContext().resources.getStringArray(R.array.genders)
                .indexOf(PersonageListFragment.filterPersonage.gender)
        )
        speciesFilter.setSelection(
            requireContext().resources.getStringArray(R.array.species)
                .indexOf(PersonageListFragment.filterPersonage.species)
        )

        buttonOk.setOnClickListener {
            PersonageListFragment.filterPersonage.apply {
                status = if (statusFilter.selectedItem.toString() != "Any") {
                    statusFilter.selectedItem as String
                } else null
                gender = if (genderFilter.selectedItem.toString() != "Any") {
                    genderFilter.selectedItem as String
                } else null
                species = if (speciesFilter.selectedItem.toString() != "Any") {
                    speciesFilter.selectedItem as String
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