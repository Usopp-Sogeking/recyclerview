package pers.peng.ui.recyclerview.secondaryadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import pers.peng.ui.recyclerview.R

class SecondaryAdapter(val ctx:Context):
    SecondaryBaseAdapter<SecondaryAdapter.GroupItemViewHolder, SecondaryAdapter.SubItemViewHolder>(){
    private var dts:MutableList<DataTree> =ArrayList()
    fun setData(datas:MutableList<DataTree>){
        dts=datas
        notifyNewData(dts)
    }

    override fun groupItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val v=LayoutInflater.from(ctx).inflate(R.layout.list_datatree,parent,false)
        return GroupItemViewHolder(v)
    }

    override fun subItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val v=LayoutInflater.from(ctx).inflate(R.layout.list_datatree,parent,false)
        return SubItemViewHolder(v)
    }

    override fun onGroupItemBindViewHolder(holder: RecyclerView.ViewHolder, groupItemIndex: Int) {
        (holder as GroupItemViewHolder).tvGroup.text=dts[groupItemIndex].groupItem
    }

    override fun onSubItemBindViewHolder(holder: RecyclerView.ViewHolder, groupItemIndex: Int, subItemIndex: Int) {
        (holder as SubItemViewHolder).tvSub.text=dts[groupItemIndex].subItems[subItemIndex]
    }

    override fun onGroupItemClick(isExpand: Boolean, holder: GroupItemViewHolder, groupItemIndex: Int) {
        Toast.makeText(ctx,"group list_datatree $groupItemIndex is expand $isExpand",Toast.LENGTH_SHORT).show()
    }

    override fun onSubItemClick(holder: SubItemViewHolder, groupItemIndex: Int, subItemIndex: Int) {
        Toast.makeText(ctx,"sub list_datatree $subItemIndex in group list_datatree $groupItemIndex",Toast.LENGTH_SHORT).show()
    }
    inner class GroupItemViewHolder(v:View):RecyclerView.ViewHolder(v){
        val tvGroup:TextView=v.findViewById(R.id.tv)
    }
    inner class SubItemViewHolder(v:View):RecyclerView.ViewHolder(v){
        val tvSub:TextView=v.findViewById(R.id.tv)
    }
}