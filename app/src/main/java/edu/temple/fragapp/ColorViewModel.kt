package edu.temple.fragapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ColorViewModel : ViewModel() {
    private val color : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getColor () : LiveData<String> {
        return color
    }

    fun setSelectedColor (selectedColor: String) {
        this.color.value = selectedColor
    }
}