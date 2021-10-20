package edu.temple.fragapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider

private const val ARG_PARAM1 = "param1"


class SelectionFragment : Fragment() {

    private lateinit var items: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            items = it.getStringArray(ARG_PARAM1)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_selection, container, false)

        val spinner = layout.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = CustomAdapter(requireContext(), items)

        val colorViewModel = ViewModelProvider(requireActivity())
            .get(ColorViewModel::class.java)

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {

                    // Update viewmodel with selected color
                    colorViewModel.setSelectedColor(parent?.getItemAtPosition(position).toString())

                    // A simple way to deal with spinner auto-selecting
                    // reset after selection (if your solution allows for that)
                    spinner.setSelection(0, false)
                    (activity as EventInterface).selectionMade()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        return layout
    }

    companion object {
        fun newInstance(param1: Array<String>) =
            SelectionFragment().apply {
                arguments = Bundle().apply {
                    putStringArray(ARG_PARAM1, param1)
                }
            }
    }

    interface EventInterface {
        fun selectionMade()
    }
}