package pers.peng.ui.recyclerview.baseadapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import pers.peng.ui.recyclerview.R


class SimpleAdapter(private val ctx:Context,private val itemList:MutableList<Item>) :RecyclerView.Adapter<SimpleAdapter.ViewHolder>(),
    ItemTouchStatus {
    inner class ViewHolder(v: View):RecyclerView.ViewHolder(v),View.OnClickListener{
        private val mView:View=v
        private val mSerialTv: TextView =v.findViewById(R.id.serial_tv)
        private val mContentTv:TextView =v.findViewById(R.id.content_tv)
        private val mDeleteTv:TextView=v.findViewById(R.id.delete)
        private val mLinearLayout:LinearLayout=v.findViewById(R.id.linearlayout)
        private var mPullTv:TextView=v.findViewById(R.id.pull_tv)
        private val mDetailTv:TextView=v.findViewById(R.id.detail_tv)
        private lateinit var mItem: Item
        init {
            mView.setOnClickListener(this)
        }
        @SuppressLint("SetTextI18n")
        fun bindView(item: Item){
            mItem=item
            mSerialTv.text=item.id.toString()
            mContentTv.text=item.text
            mDetailTv.text = "第${adapterPosition}的详细信息"
            if(mItem.opened){
                mLinearLayout.visibility=View.VISIBLE
                mPullTv.setBackgroundResource(R.drawable.ic_pullup)
            }else{
                mLinearLayout.visibility=View.GONE
                mPullTv.setBackgroundResource(R.drawable.ic_pulldown)
            }
            if(item.status){
                this.itemView.scrollTo(120,0)
            }else{
                this.itemView.scrollTo(0,0)
            }
            mDeleteTv.setOnClickListener {
                itemList.remove(item)
                this.itemView.scrollTo(0,0)
                notifyItemRemoved(adapterPosition)
            }
        }

        override fun onClick(p0: View?) {
            mItem.opened=!mItem.opened
            notifyItemChanged(adapterPosition)
        }
    }

    override fun onItemChange(position: Int, status: Boolean) {
            itemList[position].status=status
    }

    override fun onSaveItemStatus(viewHolder: RecyclerView.ViewHolder) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(ctx).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(itemList[position])
    }

    override fun getItemCount(): Int {
       return itemList.size
    }
}
fun Context.toast(text:String){
    Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
}