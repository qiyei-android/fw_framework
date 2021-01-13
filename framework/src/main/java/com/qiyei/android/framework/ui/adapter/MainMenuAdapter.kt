package com.qiyei.android.framework.ui.adapter

import android.content.Context

import com.qiyei.android.framework.R
import com.qiyei.android.framework.databinding.RvMainMenuItemBinding
import com.qiyei.android.framework.entity.MainMenu
import com.qiyei.android.framework.listener.OnItemClickListener
import com.qiyei.android.framework.ui.adapter.CommonRvAdapter


class MainMenuAdapter(context: Context, datas: MutableList<MainMenu>) :
    CommonRvAdapter<MainMenu,RvMainMenuItemBinding>(context,datas, R.layout.rv_main_menu_item) {

    /**
     * item点击事件
     */
    var mClickListener: OnItemClickListener<MainMenu>? = null

    override fun onBindHolder(holder: CommonViewHolder, item: MainMenu, position: Int) {
        holder.binding?.tv?.text = item.name
        holder.binding?.tv?.setOnClickListener {
            mClickListener?.let {
                it.click(holder.binding.tv,item,position)
            }
        }
    }
}