package pers.peng.ui.recyclerview.baseadapter

import androidx.recyclerview.widget.RecyclerView
//回调接口
interface ItemTouchStatus {
    fun onItemChange(position:Int,status:Boolean)
    fun onSaveItemStatus(viewHolder:RecyclerView.ViewHolder)
}