package com.example.kutuki

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.example.kutuki.Adapter.CatergoryAdapter
import com.example.kutuki.dataClasses.categoryData
import com.example.kutuki.viewModel.MainActivityModel
import com.example.kutuki.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityModel::class.java)
        binding.rvCategory.layoutManager = GridLayoutManager(this,2,HORIZONTAL,false);
        binding.rvCategory.setHasFixedSize(true)
        viewModel.error.observe(this, {
            if(it != "")
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })
        viewModel.successCode.observe(this, {
            if(null != it && it != 0 && it != -200){
                viewModel.categoryResponse.value?.let {
                    val data = getData()
                    val adapter = CatergoryAdapter(this,it,data)
                    binding.rvCategory.adapter = adapter
                }
            }
            if(it == -200){
                Toast.makeText(this,""+it,Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getResponse()
        viewModel.getVideos()
    }

    private fun getData(): List<categoryData> {
        viewModel.categoryResponse.value?.let {
            val data : ArrayList<categoryData> = ArrayList()
            data.add(categoryData(it.response.videoCategories.data1.name,it.response.videoCategories.data1.image))
            data.add(categoryData(it.response.videoCategories.data2.name,it.response.videoCategories.data2.image))
            data.add(categoryData(it.response.videoCategories.data3.name,it.response.videoCategories.data3.image))
            data.add(categoryData(it.response.videoCategories.data4.name,it.response.videoCategories.data4.image))
            data.add(categoryData(it.response.videoCategories.data5.name,it.response.videoCategories.data5.image))
            data.add(categoryData(it.response.videoCategories.data6.name,it.response.videoCategories.data6.image))
            data.add(categoryData(it.response.videoCategories.data7.name,it.response.videoCategories.data7.image))
            data.add(categoryData(it.response.videoCategories.data8.name,it.response.videoCategories.data8.image))
            data.add(categoryData(it.response.videoCategories.data9.name,it.response.videoCategories.data9.image))
            data.add(categoryData(it.response.videoCategories.data10.name,it.response.videoCategories.data10.image))
            data.add(categoryData(it.response.videoCategories.data11.name,it.response.videoCategories.data11.image))
            data.add(categoryData(it.response.videoCategories.data12.name,it.response.videoCategories.data12.image))
            data.add(categoryData(it.response.videoCategories.data13.name,it.response.videoCategories.data13.image))
            data.add(categoryData(it.response.videoCategories.data14.name,it.response.videoCategories.data14.image))
            data.add(categoryData(it.response.videoCategories.data15.name,it.response.videoCategories.data15.image))
            return data;
        }?: run {
            return ArrayList()
        }
    }
}