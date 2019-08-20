package pers.peng.ui.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import pers.peng.ui.recyclerview.expandadapter.ChildrenData
import pers.peng.ui.recyclerview.expandadapter.ExpandableListViewAdapter
import pers.peng.ui.recyclerview.expandadapter.FatherData

class ExpandableActivity : AppCompatActivity() {
    private lateinit var myExpandableListView:ExpandableListView
    private var adapter:ExpandableListViewAdapter?=null
    private lateinit var datas:MutableList<FatherData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandable)

        initView()
        setData()
        setAdapter()
    }
    fun initView(){
        myExpandableListView=findViewById(R.id.expandablelist)
        myExpandableListView.setOnGroupClickListener { p0, p1, p2, p3 ->
            Toast.makeText(this@ExpandableActivity,datas[p2].title,Toast.LENGTH_SHORT).show()
            false
        }
        myExpandableListView.setOnChildClickListener { p0, p1, p2, p3, p4 ->
            Toast.makeText(this@ExpandableActivity,datas[p2].list[p3].title,Toast.LENGTH_SHORT).show()
            false
        }
    }
    fun setAdapter(){
        if(adapter==null){
            adapter=ExpandableListViewAdapter(this,datas)
            myExpandableListView.setAdapter(adapter)
        }else{
            adapter!!.flashData(datas)
        }
    }
    fun setData(){
        datas =ArrayList()
        for(i in 0 until 5){
            val itemList:MutableList<ChildrenData> =ArrayList()
            for(j in 0 until 3){
                val childrenData=ChildrenData("闹钟主题$j","$j:30")
                itemList.add(childrenData)
            }
            val fatherData=FatherData("闹钟列表$i",itemList)
            datas.add(fatherData)
        }
    }
}
