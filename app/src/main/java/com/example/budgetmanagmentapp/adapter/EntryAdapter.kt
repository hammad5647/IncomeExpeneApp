package com.example.budgetmanagmentapp.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.budgetmanagmentapp.R
import com.example.budgetmanagmentapp.activities.AddEntryActivity
import com.example.budgetmanagmentapp.databinding.EntrySampleBinding
import com.example.budgetmanagmentapp.entity.TraEntity
import com.example.budgetmanagmentapp.helper.DBHelper.Companion.db
import com.example.budgetmanagmentapp.helper.DBHelper.Companion.initDatabase

class EntryAdapter(private var list: MutableList<TraEntity>) :
    RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {
    class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = EntrySampleBinding.bind(itemView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entry_sample, parent, false)
        return EntryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {


        if (list[position].traType == 1) {
            holder.binding.sampleAmount.setTextColor(holder.itemView.context.getColor(android.R.color.holo_green_light))
        } else {
            holder.binding.sampleAmount.setTextColor(holder.itemView.context.getColor(android.R.color.holo_red_dark))
        }
        holder.binding.txtCategory.text = list[position].traCategory
        holder.binding.sampleAmount.text = "₹${list[position].traAmount}"
        holder.binding.txtTitle.text = list[position].traTitle
        holder.binding.txtDate.text = list[position].traDate


        holder.binding.entrySample.setOnLongClickListener {
            val dialog = Dialog(holder.itemView.context)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setContentView(R.layout.dialog_view)
            dialog.setCanceledOnTouchOutside(true)

            val delete: TextView = dialog.findViewById(R.id.deleteBtn)
            val cancel: TextView = dialog.findViewById(R.id.cancelBtn)

            dialog.show()

            val transEntity = TraEntity(
                list[position].traId,
                list[position].traAmount,
                list[position].traDate,
                list[position].traTitle,
                list[position].traCategory,
                list[position].traType
            )
            delete.setOnClickListener {
                initDatabase(holder.itemView.context)
                list.removeAt(position)
                db!!.dao().deleteData(transEntity)
                notifyDataSetChanged()
                dialog.dismiss()
            }
            cancel.setOnClickListener {
                dialog.dismiss()
            }
            true
        }
        holder.binding.entrySample.setOnClickListener {
            val intent = Intent(holder.itemView.context, AddEntryActivity::class.java)
            intent.putExtra("id", list[position].traId)
            intent.putExtra("category", list[position].traCategory)
            intent.putExtra("title", list[position].traTitle)
            intent.putExtra("amount", list[position].traAmount)
            intent.putExtra("date", list[position].traDate)
            intent.putExtra("type", list[position].traType)
            holder.itemView.context.startActivity(intent)

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyDataChanged(l1: MutableList<TraEntity>) {
        list = l1
        notifyDataSetChanged()

    }
}