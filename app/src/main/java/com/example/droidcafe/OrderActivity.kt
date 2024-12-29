package com.example.droidcafe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.droidcafe.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cityList = resources.getStringArray(R.array.city_list)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cityList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.citySpinner.adapter = adapter

        binding.fab.setOnClickListener { view ->
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.sameday.setOnClickListener { onRadioButtonClicked(it) }
        binding.nextday.setOnClickListener { onRadioButtonClicked(it) }
        binding.pickup.setOnClickListener { onRadioButtonClicked(it) }

        // Mengatur tombol kirim pesanan
        binding.submitButton.setOnClickListener {
            submitOrder()
        }
    }

    private fun onRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.id) {
            R.id.sameday -> if (checked) displayToast(getString(R.string.same_day_messenger_service))
            R.id.nextday -> if (checked) displayToast(getString(R.string.next_day_ground_delivery))
            R.id.pickup -> if (checked) displayToast(getString(R.string.pick_up))
        }
    }

    private fun displayToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun submitOrder() {
        val name = binding.nameText.text.toString()
        val address = binding.addressText.text.toString()
        val city = binding.citySpinner.selectedItem.toString()
        val phone = binding.phoneText.text.toString()
        val notes = binding.noteText.text.toString()

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            displayToast("Please fill in all required fields")
            return
        }

        val orderDetails = """
            Name: $name
            Address: $address
            City: $city
            Phone: $phone
            Notes: $notes
        """.trimIndent()

        displayToast("Order submitted: \n$orderDetails")
    }
}
