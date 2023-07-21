package com.example.simplecontactappv3

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.simplecontactappv3.databinding.FragmentSecondBinding
import com.example.simplecontactappv3.room.Contact
import com.example.simplecontactappv3.room.ContactDao
import com.example.simplecontactappv3.room.ContactDatabase

class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var dao: ContactDao
    private val navArgs by navArgs<SecondFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)
        dao = ContactDatabase.getDatabase(requireContext()).contactDao()
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
                //findNavController().popBackStack()
            }

            if (navArgs.contact != null) {
                tvTitle.text = "Edit"
                etName.setText(navArgs.contact?.contactName)
                etSurname.setText(navArgs.contact?.surname)
                etPhone.setText(navArgs.contact?.phoneNumber)
            } else {
                tvTitle.text = "Add"
            }

            btnDone.setOnClickListener {
                val name = etName.text.toString()
                val surname = etSurname.text.toString()
                val phone = etPhone.text.toString()

                if (navArgs.contact != null) {
                    dao.updateContact(
                        Contact(
                            id = navArgs.contact!!.id,
                            contactName = name,
                            surname = surname,
                            phoneNumber = phone
                        )
                    )
                } else {
                    dao.addContact(
                        Contact(
                            id = 0,
                            contactName = name,
                            surname = surname,
                            phoneNumber = phone,
                            state = 0
                        )
                    )
                }

                findNavController().popBackStack()
            }
        }
    }
}