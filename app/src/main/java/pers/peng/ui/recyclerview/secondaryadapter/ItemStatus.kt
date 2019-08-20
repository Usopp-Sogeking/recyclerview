package pers.peng.ui.recyclerview.secondaryadapter

class ItemStatus {
    companion object{
        val VIEW_TYPE_GROUPITEM=0
        val VIEW_TYPE_SUBITEM=1
    }
    var viewType:Int?=null
    var groupItemIndex:Int=0
    var subItemIndex:Int=-1
}