package pers.peng.ui.recyclerview.baseadapter

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchCallback(itemTouchStatus: ItemTouchStatus, defaultScrollX:Int):ItemTouchHelper.Callback() {
    private val mItemTouchStatus=itemTouchStatus
    private val mDefaultScrollX=defaultScrollX
    private var mCurrentScrollX:Int=0
    private var mCurrentScrollXWhenInactive:Int=0
    private var mInitXWhenInactive:Float=0f
    private var mFirstInactive:Boolean=false
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags:Int=ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags:Int=ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags,swipeFlags)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return Float.MAX_VALUE
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return Float.MAX_VALUE
    }
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
       return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if(dX==0f){
            mCurrentScrollX=viewHolder.itemView.scrollX
            mFirstInactive=true
        }
        if(isCurrentlyActive){
            viewHolder.itemView.scrollTo(mCurrentScrollX+(-dX).toInt(),0)
        }else{
            if(mFirstInactive){
                mFirstInactive=false
                mCurrentScrollXWhenInactive=viewHolder.itemView.scrollX
                mInitXWhenInactive=dX
            }
            if(viewHolder.itemView.scrollX>=mDefaultScrollX){
                viewHolder.itemView.scrollTo(Math.max(mCurrentScrollX+(-dX).toInt(),mDefaultScrollX),0)
            }else{
                viewHolder.itemView.scrollTo((mCurrentScrollXWhenInactive*dX/mInitXWhenInactive).toInt(),0)
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        when (viewHolder.itemView.scrollX) {
            mDefaultScrollX -> mItemTouchStatus.onItemChange(viewHolder.adapterPosition,true)
            0 -> mItemTouchStatus.onItemChange(viewHolder.adapterPosition,false)
            else -> {}
        }
    }
}