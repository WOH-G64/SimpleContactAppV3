package com.example.simplecontactappv3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.simplecontactappv3.databinding.ItemContactBinding
import com.example.simplecontactappv3.room.Contact

class Adapter: RecyclerView.Adapter<Adapter.ContactViewHolder>() {

    var contacts = mutableListOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ContactViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Contact) {
            binding.apply {
                tvContactName.text = item.contactName
                tvContactPhone.text = item.phoneNumber
                imgCheck.isVisible = item.state == 1

                btnDelete.setOnClickListener { deleteBtnClicked.invoke(item) }

                root.setOnClickListener {
                    imgCheck.isVisible = true
                    observerAdd.invoke(item)
                    true
                }

                root.setOnClickListener {
                    imgCheck.isVisible = false
                    observerRemove.invoke(item)
                }

                btnEdit.setOnClickListener { editBtnClicked.invoke(item) }
            }
        }
    }

    var observerAdd: (Contact) -> Unit = {}
    fun observerAdd(block: (Contact) -> Unit) {observerAdd = block}

    var observerRemove: (Contact) -> Unit = {}
    fun observerRemove(block: (Contact) -> Unit) {observerRemove = block}

    var deleteBtnClicked: (contact: Contact) -> Unit = {}
    fun deleteBtnClicked(block: (Contact) -> Unit) {
        deleteBtnClicked = block
    }

    var editBtnClicked: (contact: Contact) -> Unit = {}
    fun editBtnClicked(block: (Contact) -> Unit) {
        editBtnClicked = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }
}