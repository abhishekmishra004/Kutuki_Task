package com.example.kutuki.Adapter

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kutuki.Model.firstResponse
import com.example.kutuki.R
import com.example.kutuki.VideoActivity
import com.example.kutuki.dataClasses.categoryData

class CatergoryAdapter(var context : Context,val response : firstResponse,val data : List<categoryData>) : RecyclerView.Adapter<CatergoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.each_category,parent,false));
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.textView.text = data[position].name;
        Glide.with(context).load(data[position].url).placeholder(R.drawable.index).into(holder.imageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(context,VideoActivity::class.java)
            intent.putExtra("name",data[position].name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onViewAttachedToWindow(holder: CategoryViewHolder) {
        super.onViewAttachedToWindow(holder)
        setAnimation(holder.itemView)
    }

    private fun setAnimation(viewToAnimate: View) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.rotate_in)
        viewToAnimate.startAnimation(animation)
    }


    class CategoryViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_title)
        val imageView : ImageView = itemView.findViewById(R.id.iv_cover)
    }

}