package com.example.simplecontactappv3

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.simplecontactappv3.databinding.FragmentFirstBinding
import com.example.simplecontactappv3.room.Contact
import com.example.simplecontactappv3.room.ContactDao
import com.example.simplecontactappv3.room.ContactDatabase

class FirstFragment : Fragment(R.layout.fragment_first) {

    private lateinit var binding: FragmentFirstBinding
    private val adapter = Adapter()
    private lateinit var dao: ContactDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)
        binding.rvContacts.adapter = adapter
        dao = ContactDatabase.getDatabase(requireContext()).contactDao()

        binding.btnAdd.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
        var count = 0
        dao.getMyAllContacts().forEach { contact ->
            if (contact.state == 1) count++
        }
        if (count != 0) {
            binding.ivClose.isInvisible = true
            binding.title.text = count.toString()
        }else {
            binding.ivClose.isVisible = false
            binding.title.text = getString(R.string.contacts)
        }

        binding.ivClose.setOnClickListener {
            dao.getMyAllContacts().forEach { contact ->
                if (contact.state == 1) {
                    dao.updateContact(
                        Contact(
                            contact.id,
                            contactName = contact.contactName,
                            surname = contact.surname,
                            phoneNumber = contact.phoneNumber,
                            state = 0
                        )
                    )
                }
            }
            binding.ivClose.isVisible = false
            binding.title.text = getString(R.string.contacts)
            setDate()
            adapter.notifyDataSetChanged()
        }

        binding.editQuery.doAfterTextChanged {
            adapter.contacts = dao.findContactWithName("$it%").toMutableList()
        }
        binding.ivMore.setOnClickListener {
            dao.deleteAll()
            setDate()
        }
        adapter.editBtnClicked {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(it)
            findNavController().navigate(action)
        }

        adapter.observerAdd {contact ->
            dao.updateContact(
                Contact(
                    contact.id,
                    contactName = contact.contactName,
                    surname = contact.surname,
                    phoneNumber = contact.phoneNumber,
                    state = 1
                )
            )
            var count = 0
            dao.getMyAllContacts().forEach { contact ->
                if (contact.state == 1) {
                    count++
                }
            }
            if (count != 0) {
                binding.ivClose.isVisible = true
                binding.title.text = count.toString()
            }else {
                binding.ivClose.isVisible = false
                binding.title.text = getString(R.string.contacts)
            }
        }

        adapter.observerRemove {contact ->
            dao.updateContact(
                Contact(
                    contact.id,
                    contactName = contact.contactName,
                    surname = contact.surname,
                    phoneNumber = contact.phoneNumber,
                    state = 0
                )
            )
            var count = 0
            dao.getMyAllContacts().forEach{ contact ->
                if (contact.state == 1) {
                    count++
                }
            }
            if(count != 0) {
                binding.ivClose.isVisible = true
                binding.title.text = count.toString()
            } else {
                binding.ivClose.isVisible = false
                binding.title.text = getString(R.string.contacts)
            }
        }

        adapter.deleteBtnClicked {
            dao.deleteContact(it.id)
            setDate()
        }

        setDate()
    }

    private fun setDate() {
        adapter.contacts = dao.getMyAllContacts().toMutableList()
    }
}