package pers.peng.ui.recyclerview.expandadapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import pers.peng.ui.recyclerview.R

class ExpandableListViewAdapter(ctx:Context,datas:MutableList<FatherData>):BaseExpandableListAdapter(){
    private var mInflater:LayoutInflater = LayoutInflater.from(ctx)
    private var context=ctx
    private var data_list:MutableList<FatherData> =datas
    var v:View?=null
    fun flashData(datas:MutableList<FatherData>){
        data_list=datas
        notifyDataSetChanged()
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return data_list[p0].list[p1]
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        v=p3
        var childrenView:HolderView?=null
        if(v==null) {
            v=mInflater.inflate(R.layout.list_children, p4, false)
            childrenView= HolderView(v!!.findViewById(R.id.alarm_clock_tv1),v!!.findViewById(R.id.alarm_clock_tv2))
            v?.tag=childrenView
        }else{
            childrenView=v?.tag as HolderView
        }
        childrenView.titleView.text=data_list[p0].list[p1].title
        childrenView.descView.text=data_list[p0].list[p1].Desc
        return v!!
    }
    class HolderView(val titleView:TextView,val descView: TextView)

    override fun getChildrenCount(p0: Int): Int {
        return data_list[p0].list.size
    }

    override fun getGroup(p0: Int): Any {
        return data_list[p0]
    }

    override fun getGroupCount(): Int {
        return data_list.size
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        v=p2
        var holderViewFather:HolderViewFather?=null
        if(v==null) {
            v=mInflater.inflate(R.layout.list_parent, p3, false)
            holderViewFather=HolderViewFather(v!!.findViewById(R.id.father_tv),v!!.findViewById(R.id.group_state))
            v?.tag=holderViewFather
        }else{
            holderViewFather=v?.tag as HolderViewFather
        }

        if(p1){
            holderViewFather.group_state.setImageResource(R.drawable.ic_pulldown)
        }else{
            holderViewFather.group_state.setImageResource(R.drawable.ic_pullup)
        }
        holderViewFather.titlev.text=data_list[p0].title
        return v!!
    }
    class HolderViewFather(val titlev:TextView,val group_state:ImageView)

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}