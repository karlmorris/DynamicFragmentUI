package edu.temple.fragapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), SelectionFragment.EventInterface {

    var twoPane = false
    lateinit var colorViewModel: ColorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Flag to determine whether there is one or two fragment containers
        twoPane = findViewById<View>(R.id.container2) != null

        colorViewModel = ViewModelProvider(this).get(ColorViewModel::class.java)

        // Pop DisplayFragment from stack if color was previously selected,
        // but user has since cleared selection
        if (supportFragmentManager.findFragmentById(R.id.container1)
                    is DisplayFragment
            && colorViewModel.getColor().value.isNullOrBlank())
            supportFragmentManager.popBackStack()

        // Remove redundant DisplayFragment if we're moving from single-pane mode
        // (one container) to double pane mode (two containers)
        // and a color has been selected
        if (supportFragmentManager.findFragmentById(R.id.container1) is DisplayFragment
            && twoPane)
                supportFragmentManager.popBackStack();


        val colors = arrayOf("Red", "Blue", "Green", "White", "Purple")

        // If fragment was not previously loaded (first time starting activity)
        // then add SelectionFragment
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1, SelectionFragment.newInstance(colors))
                .commit()

        // If second container is available, place an
        // instance of DisplayFragment
        if (twoPane) {
            if (supportFragmentManager.findFragmentById(R.id.container2) == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.container2, DisplayFragment())
                .commit()
        } else if(!colorViewModel.getColor().value.isNullOrBlank()) { // If moving to single-pane
            supportFragmentManager.beginTransaction()                 // but a color was selected
                .add(R.id.container1, DisplayFragment())              // before the switch
                .addToBackStack(null)
                .commit()
        }
    }

    // A message from the fragment whenever the _event_
    // of selecting a color takes place
    override fun selectionMade() {
        // only respond if there is a single container
        if (!twoPane)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1, DisplayFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        // A single way to "clear" the selected book so that
        // if doesn't remain selected. Remove it when the user
        // hits Back
        colorViewModel.setSelectedColor("")
    }

}