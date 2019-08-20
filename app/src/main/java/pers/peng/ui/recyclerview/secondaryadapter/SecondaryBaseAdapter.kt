package pers.peng.ui.recyclerview.secondaryadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class SecondaryBaseAdapter<GVH,SVH:RecyclerView.ViewHolder>:RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    //列表中项的展开状态
    private val groupItemStatus:MutableList<Boolean> =ArrayList()
    private var dataTrees:MutableList<DataTree> =ArrayList()
        set(value) {
            field=value
            initGroupItemStatus(groupItemStatus)
            notifyDataSetChanged()
        }
    fun notifyNewData(data:MutableList<DataTree>){
        dataTrees=data
    }
    //未展开
    private fun initGroupItemStatus(l:MutableList<Boolean>){
        for(i in 0 until dataTrees.size){
            l.add(false)
        }
    }
    abstract fun groupItemViewHolder(parent:ViewGroup):RecyclerView.ViewHolder
    abstract fun subItemViewHolder(parent:ViewGroup):RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder:RecyclerView.ViewHolder?=when(viewType){
            ItemStatus.VIEW_TYPE_GROUPITEM->groupItemViewHolder(parent)
            ItemStatus.VIEW_TYPE_SUBITEM->subItemViewHolder(parent)
            else->null
        }
        return viewHolder!!
    }
    abstract fun onGroupItemBindViewHolder(holder:RecyclerView.ViewHolder,groupItemIndex:Int)
    abstract fun onSubItemBindViewHolder(holder:RecyclerView.ViewHolder,groupItemIndex:Int,subItemIndex:Int)
    abstract fun onGroupItemClick(isExpand:Boolean,holder:GVH,groupItemIndex:Int)
    abstract fun onSubItemClick(holder: SVH,groupItemIndex: Int,subItemIndex: Int)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemStatus: ItemStatus =getItemStatusByPosition(position)
        val dt: DataTree =dataTrees[itemStatus.groupItemIndex]
        if(itemStatus.viewType== ItemStatus.VIEW_TYPE_GROUPITEM){
            onGroupItemBindViewHolder(holder,itemStatus.groupItemIndex)
            holder.itemView.setOnClickListener{
                val groupItemIndex=itemStatus.groupItemIndex
                if(!groupItemStatus[groupItemIndex]){
                    onGroupItemClick(false,holder as GVH,groupItemIndex)
                    groupItemStatus[groupItemIndex] = true
                    notifyItemRangeInserted(holder.adapterPosition+1,dt.subItems.size)
                }else{
                    onGroupItemClick(true,holder as GVH,groupItemIndex)
                    groupItemStatus[groupItemIndex]=false
                    notifyItemRangeRemoved(holder.adapterPosition+1,dt.subItems.size)
                }
            }
        }else if(itemStatus.viewType== ItemStatus.VIEW_TYPE_SUBITEM){
            onSubItemBindViewHolder(holder,itemStatus.groupItemIndex,itemStatus.subItemIndex)
            holder.itemView.setOnClickListener{
                onSubItemClick(holder as SVH,itemStatus.groupItemIndex,itemStatus.subItemIndex)
            }
        }
    }

    override fun getItemCount(): Int {
        var itemCount=0
        if(groupItemStatus.size==0){
            return 0
        }
        for(i in 0 until dataTrees.size){
            if(groupItemStatus[i]){
                itemCount+=dataTrees[i].subItems.size+1
            }else{
                itemCount++
            }
        }
        return itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return getItemStatusByPosition(position).viewType!!
    }
    private fun getItemStatusByPosition(position:Int): ItemStatus {
        val itemStatus= ItemStatus()
        var count=0
        for(i in 0..groupItemStatus.size){
            if(count==position){
                itemStatus.viewType= ItemStatus.VIEW_TYPE_GROUPITEM
                itemStatus.groupItemIndex=i
                break
            }else if(count>position){
                itemStatus.viewType= ItemStatus.VIEW_TYPE_SUBITEM
                itemStatus.groupItemIndex=i-1
                itemStatus.subItemIndex=position-(count-dataTrees[i-1].subItems.size)
                break
            }
            count++
            if(groupItemStatus[i]){
                count+=dataTrees[i].subItems.size
            }
        }
        return itemStatus
    }
}