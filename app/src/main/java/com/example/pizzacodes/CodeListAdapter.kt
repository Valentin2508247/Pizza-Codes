package com.example.pizzacodes

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzacodes.database.Code

class CodeListAdapter(private var mData: List<Code>, private val listener: IListItemClickListener)
        : RecyclerView.Adapter<CodeListAdapter.ViewHolder>() {
    private val TAG = "CodeListAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_code, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val code = mData[position]
        holder.tvCode.text = code.code
        holder.tvDescription.text = code.description
        if (code.cost % 100 == 0)
            holder.tvCost.text = "От ${code.cost / 100}.00"
        else
            holder.tvCost.text = "От ${code.cost / 100}.${code.cost % 100}"

        val itemListener= View.OnClickListener { _ ->
            listener.onListItemClick(code)
            Log.d(TAG, "Item ${code.code} was clicked")
        }

        holder.itemView.setOnClickListener(itemListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private  val TAG = "CodeViewHolder"
        val tvCode: TextView = view.findViewById(R.id.tv_code_code)
        val tvDescription: TextView = view.findViewById(R.id.tv_code_description)
        val tvCost: TextView = view.findViewById(R.id.tv_code_cost)
    }

    fun setData(data: List<Code>){
        mData = data
        notifyDataSetChanged()
    }
}