package com.mayan.sospluginmodlue

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.mayan.sospluginmodlue.model.ContactsData
import kotlinx.android.synthetic.main.sos__contact_list_item.view.proimg

class ContactAdapter(
    val item: ArrayList<ContactsData>, val mContext: Context, val itemClicked: ItemClicked
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.sos__contact_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        holder.tvContact?.text = item[position].contact_name
        holder.tvPhoneNumber?.text =
            item[position].country_code + " " + item[position].contact_number
        holder.imageDelete.tag = holder.imageProgress

        ProfileImageSetupClass.setupProfileImage(
            item[position].contact_name,
            holder.proimg
        )

    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return item.size
        if (itemCount == 0) {
            Toast.makeText(mContext, "No contacts found", Toast.LENGTH_SHORT).show()
        }
        return itemCount
    }

    fun remove(contactID: Int) {
        for (i in item.indices) {
            if (item[i].contact_id == contactID) {
                item.removeAt(i)
                notifyItemRemoved(i)
                return
            }
        }
    }

    fun add(data: ContactsData, position: Int) {
        item.add(position, data)
        notifyItemInserted(position)
    }

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val tvContact = view.findViewById<AppCompatTextView>(R.id.tv_contact)
        val tvPhoneNumber = view.findViewById<AppCompatTextView>(R.id.tv_phno)
        val imageDelete = view.findViewById<AppCompatImageView>(R.id.img_delete)
        val contact_image = view.findViewById<AppCompatImageView>(R.id.img_contact)
        val imageProgress = view.findViewById<ContentLoadingProgressBar>(R.id.img_progress)
        val proimg = view.proimg

        init {
            imageDelete.setOnClickListener {

//                view.visibility = View.GONE
                if (view.tag is View) view.visibility = View.VISIBLE
//                itemClicked.deleteItemClicked(item[adapterPosition].contact_id!!)
                val builder = AlertDialog.Builder(mContext)
                // Set the alert dialog title
                builder.setTitle("")
                // Display a message on alert dialog
                builder.setMessage(mContext.resources.getString(R.string.alert_delete_contact))
                builder.setPositiveButton(mContext.resources.getString(R.string.ok)) { dialog, which ->
                    view.visibility = View.GONE
                    if (view.tag is View)
                        view.visibility = View.VISIBLE
                    itemClicked.deleteItemClicked(item[adapterPosition].contact_id!!)
                    dialog.dismiss()
                }
                // Display a negative button on alert dialog
                builder.setNegativeButton(mContext.resources.getString(R.string.cancel)) { dialog, which ->
                    dialog.dismiss()
                }
                val dialogs: AlertDialog = builder.create()
                dialogs.setOnShowListener {
                    dialogs.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.button_accept))
                    dialogs.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.black))
                }
                dialogs.show()


            }
        }
    }
}





