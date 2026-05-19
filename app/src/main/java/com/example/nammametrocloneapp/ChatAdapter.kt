package com.example.nammametrocloneapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ChatMessage(val text: String, val isUser: Boolean)

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutUser: LinearLayout = view.findViewById(R.id.layoutUser)
        val tvUserMessage: TextView = view.findViewById(R.id.tvUserMessage)
        val layoutBot: LinearLayout = view.findViewById(R.id.layoutBot)
        val tvBotMessage: TextView = view.findViewById(R.id.tvBotMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        if (message.isUser) {
            holder.layoutUser.visibility = View.VISIBLE
            holder.layoutBot.visibility = View.GONE
            holder.tvUserMessage.text = message.text
        } else {
            holder.layoutUser.visibility = View.GONE
            holder.layoutBot.visibility = View.VISIBLE
            holder.tvBotMessage.text = message.text
        }
    }

    override fun getItemCount() = messages.size
}
