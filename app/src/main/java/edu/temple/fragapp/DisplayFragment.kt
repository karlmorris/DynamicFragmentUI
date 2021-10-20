package edu.temple.fragapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class DisplayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_display, container, false)

        return layout;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewModelProvider(requireActivity())
            .get(ColorViewModel::class.java)
            .getColor()
            .observe(requireActivity(), {
                changeColor(it)
            })
    }

    private fun changeColor(color : String) {
        view?.setBackgroundColor(Color.parseColor(if (!color.isBlank()) color else "White"))
    }
}