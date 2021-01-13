package com.qiyei.android.framework

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qiyei.android.framework.databinding.ActivityMainBinding
import com.qiyei.android.framework.entity.MainMenu
import com.qiyei.android.framework.listener.OnItemClickListener
import com.qiyei.android.framework.ui.adapter.MainMenuAdapter
import com.qiyei.android.framework.ui.view.CategoryItemDecoration
import com.qiyei.android.framework.ui.viewmodel.MainMenuViewModel
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private val mMenuList: MutableList<MainMenu> = ArrayList()

    private val names = emptyList<String>()
    private val clazzs = arrayOf<Class<out Activity>>()

    /**
     * ViewModel
     */
    private lateinit var mMenuViewModel: MainMenuViewModel
    private lateinit var mMenuAdapter: MainMenuAdapter
    private lateinit var mActivityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mActivityMainBinding.root)
        initView()
        initData()
    }

    private fun initView() {
        mActivityMainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mActivityMainBinding.recyclerView.addItemDecoration(CategoryItemDecoration(getDrawable(R.drawable.recyclerview_decoration)))
        //初始化Adapter
        mMenuAdapter = MainMenuAdapter(this,mutableListOf<MainMenu>())
        mMenuAdapter.mClickListener = MyListener()
        //初始化ViewModel 2.2.0 用新方法初始化
        mMenuViewModel = ViewModelProvider(this)[MainMenuViewModel::class.java]

        mMenuViewModel.liveData.observe(
            this,
            Observer { mainMenus ->
                //update UI
                mMenuAdapter.mDataList = mainMenus
            })

        mActivityMainBinding.recyclerView.adapter = mMenuAdapter
    }

    private fun initData() {
        for (i in names.indices) {
            val menu = MainMenu(i + 1, names[i], clazzs[i])
            mMenuList.add(menu)
        }
        //主动更新数据
        mMenuViewModel.liveData.value = mMenuList
    }

    /**
     * 跳转到菜单
     * @param menu
     */
    fun gotoMenuActivity(menu: MainMenu) {
        startActivity(Intent(this,menu.clazz))
    }

    private inner class MyListener : OnItemClickListener<MainMenu> {
        override fun click(v: View, item: MainMenu, position: Int) {
            gotoMenuActivity(item)
        }
    }
}