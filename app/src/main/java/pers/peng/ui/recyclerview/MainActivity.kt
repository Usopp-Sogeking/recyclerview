package pers.peng.ui.recyclerview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pers.peng.ui.recyclerview.secondaryadapter.DataTree
import pers.peng.ui.recyclerview.baseadapter.Item
import pers.peng.ui.recyclerview.secondaryadapter.SecondaryAdapter

inline fun <reified T:View> Activity.find(id:Int):T{
    return this.findViewById(id)
}

class MainActivity : AppCompatActivity() {
    private lateinit var mRecyclerView:RecyclerView
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView=find(R.id.reyclerview)
        //val itemList=getData()
        //val adapter=SimpleAdapter(this,itemList)
        val dataTreeList=getDataTree()
        val adapter= SecondaryAdapter(this)
        adapter.setData(dataTreeList)
        val layout=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        mRecyclerView.layoutManager=layout
        mRecyclerView.adapter=adapter
        //val itemTouchHelper= ItemTouchHelper(ItemTouchCallback(adapter,dpToPx(this,60f)))
        //itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }
    fun getData():MutableList<Item>{
        val itemList:MutableList<Item> =ArrayList()
        (0..100).forEach {
            itemList.add(Item(it, "该项在列表中的位置是：$it"))
        }
        return itemList
    }
    fun getDataTree():MutableList<DataTree>{
        val dataTreeList:MutableList<DataTree> =ArrayList()
        (0..10).forEach {
            dataTreeList.add(
                DataTree(
                    it.toString(),
                    mutableListOf("sub 0", "sub 1", "sub 2")
                )
            )
        }
        return dataTreeList
    }
    fun dpToPx(context: Context, value:Float):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,context.resources.displayMetrics).toInt()
    }
}

