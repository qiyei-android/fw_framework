package com.qiyei.android.framework.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

abstract class CommonRvAdapter<E,VB : ViewBinding>(private val mContext: Context, var mDataList:MutableList<E>, @LayoutRes val mItemLayoutId:Int):RecyclerView.Adapter<CommonRvAdapter<E,VB>.CommonViewHolder>() {
    private lateinit var mItemLayoutViewBindingClazz:Class<out ViewBinding>
    init {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType && type.actualTypeArguments.size >= 2) {
            mItemLayoutViewBindingClazz = type.actualTypeArguments[1] as Class<out ViewBinding>
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        try {
            val method = mItemLayoutViewBindingClazz.getDeclaredMethod("inflate", LayoutInflater::class.java)
            val binding = method.invoke(null, LayoutInflater.from(mContext),parent,false) as VB
            return CommonViewHolder(binding,binding.root)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        val itemView = LayoutInflater.from(mContext).inflate(mItemLayoutId,parent,false)
        return CommonViewHolder(null,itemView)
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        onBindHolder(holder,mDataList[position],position)
    }

    abstract fun onBindHolder(holder: CommonViewHolder,item:E,position: Int)


    inner class CommonViewHolder(val binding: VB?,itemView: View) : RecyclerView.ViewHolder(itemView)
}