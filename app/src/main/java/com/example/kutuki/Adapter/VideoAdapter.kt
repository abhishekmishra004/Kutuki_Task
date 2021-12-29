package com.example.kutuki.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kutuki.R
import com.example.kutuki.VideoActivity
import com.example.kutuki.dataClasses.videoDataModel

class VideoAdapter(private val context : Context, val data : ArrayList<videoDataModel>) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    var clicked = 0;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.each_video_card,parent,false))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        Glide.with(context).load(data[position].thumbnailURL).placeholder(R.drawable.index).into(holder.imageView)
        if(position == 0 && clicked == 0){
            holder.imageView.foreground = ResourcesCompat.getDrawable(context.resources,R.drawable.selected_background,null);
        }else{
            holder.imageView.foreground = ResourcesCompat.getDrawable(context.resources,R.drawable.transparent_bg,null);
        }
        holder.itemView.setOnClickListener {
            notifyItemChanged(clicked)
            clicked = position
            holder.imageView.foreground = ResourcesCompat.getDrawable(context.resources,R.drawable.selected_background,null)
            (context as VideoActivity).playVideo(data[position].videoURL)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onViewAttachedToWindow(holder: VideoViewHolder) {
        super.onViewAttachedToWindow(holder)
        setAnimation(holder.itemView)
    }

    private fun setAnimation(viewToAnimate: View) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.rotate_in)
        viewToAnimate.startAnimation(animation)
    }

    class VideoViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView : ImageView = itemView.findViewById(R.id.iv_cover)
    }
}