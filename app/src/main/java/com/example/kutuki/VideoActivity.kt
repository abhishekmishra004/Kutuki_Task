package com.example.kutuki

import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kutuki.Adapter.VideoAdapter
import com.example.kutuki.databinding.ActivityVideoBinding
import com.example.kutuki.viewModel.MainActivityModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.kutuki.viewModel.MainActivityModel.video as video1


@AndroidEntryPoint
class VideoActivity : AppCompatActivity() {

    val viewModel: MainActivityModel by viewModels()
    lateinit var binding: ActivityVideoBinding

    lateinit var ivRewind: ImageView
    lateinit var ivForward: ImageView
    lateinit var ivPlayPause: ImageView
    lateinit var ivFullScreen : ImageView
    lateinit var progressBar: ProgressBar
    lateinit var seekBar: SeekBar
    lateinit var tvStart: TextView
    lateinit var tvEnd: TextView
    lateinit var videoView: VideoView
    lateinit var mediacontroller: MediaController
    var mediaPlayer = MediaPlayer()

    private var mCurrentPosition = 0
    var duration = 0
    var isLoading = true
    var isFullScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val type = intent.getStringExtra("name")

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.rvList.layoutManager = linearLayoutManager;
        binding.rvList.setHasFixedSize(true)
        binding.tvBack.setOnClickListener { onBackPressed() }
        tvStart = binding.tvTiming
        tvEnd = binding.tvEndTotal
        ivPlayPause = binding.ivPlayPause
        seekBar = binding.seekBar
        ivRewind = binding.ivRewind
        ivForward = binding.ivForward
        ivFullScreen = binding.ivFullscreen
        progressBar = binding.progressCircular
        videoView = binding.videoView
        mediacontroller = MediaController(this)
        mediacontroller.setAnchorView(videoView)
        mediacontroller.visibility = View.GONE
        videoView.setMediaController(mediacontroller)

        ivPlayPause.setTag(R.drawable.ic_play_circle)

        video1.videoData.observe(this, {
            it?.let {
                it.data.let {
                    if (it.containsKey(type) && it[type] != null) {
                        binding.rvList.adapter = VideoAdapter(this, it[type]!!)
                        playVideo(it[type]!![0].videoURL)
                    } else {
                        Toast.makeText(this, "if condition false", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: run {
                Toast.makeText(this, "first it null", Toast.LENGTH_SHORT).show()
            }
        })

        clickListener()
    }

    fun playVideo(url : String){
        if(mediaPlayer.isPlaying)
            mediaPlayer.stop()
        if(videoView.isPlaying)
            videoView.stopPlayback()
        val videoUri = Uri.parse(url)
        videoView.setVideoURI(videoUri)
        bufferPlay()
    }


    private fun bufferPlay() {
        progressBar.visibility = View.VISIBLE
        isLoading = true
        videoView.requestFocus()
        ivPlayPause.setImageResource(R.drawable.ic_pause_circle)
        videoView.setOnPreparedListener(OnPreparedListener { mp: MediaPlayer ->
            mediaPlayer = mp
            mediaPlayer.setVolume(1.0f,1.0f)
            try {
                Log.d("videoView", "Bufferplay: prepare async called")
                mediaPlayer.prepareAsync()
            } catch (e: Exception) {
                Log.d("videoView", "Bufferplay: " + e.message)
            }
            duration = mp.duration / 1000
            Log.d("videoView", "Bufferplay: $duration")
            progressBar.visibility = View.INVISIBLE
            isLoading = false
            seekBar.max = duration
            handleProgressBar()
            calculateTotalTime()
            if (mCurrentPosition > 0 && mCurrentPosition < videoView.getDuration()) {
                videoView.seekTo(mCurrentPosition)
                mp.seekTo(mCurrentPosition)
            } else {
                videoView.seekTo(1)
                mp.seekTo(1)
            }
            videoView.start()
        })
        videoView.setOnCompletionListener { mp: MediaPlayer ->
            videoView.seekTo(0)
            mediaPlayer = mp
            mediaPlayer.setVolume(1.0f,1.0f)
            try {
                Log.d("videoView", "Bufferplay: prepare async called")
                mediaPlayer.prepareAsync()
            } catch (e: Exception) {
                Log.d("videoView", "Bufferplay: " + e.message)
            }
            progressBar.visibility = View.INVISIBLE
            isLoading = false
        }
        videoView.setOnInfoListener { mp: MediaPlayer?, what: Int, extra: Int ->
            if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
                progressBar.visibility = View.INVISIBLE
                isLoading = false
            }
            if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
                progressBar.visibility = View.VISIBLE
                isLoading = true
            }
            if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
                progressBar.visibility = View.INVISIBLE
                isLoading = false
            }
            false
        }
    }


    private fun handleProgressBar() {
        val mHandler = Handler(Looper.getMainLooper())
        runOnUiThread(object : Runnable {
            override fun run() {
                try {
                    val mCurrentPosition = mediaPlayer.currentPosition / 1000
                    Log.d("videoView", "Seekbar previous: inside handler " + seekBar.progress)
                    seekBar.progress = mCurrentPosition
                    Log.d("videoView", "Seekbar after: inside handler " + seekBar.progress)
                    val min = (mCurrentPosition / 60)
                    val sec = (mCurrentPosition % 60)
                    var minute = ""
                    var second = ""
                    minute = if (min.toString().length == 1) "0$min" else min.toString()
                    second = if (sec.toString().length == 1) "0$sec" else sec.toString()
                    tvStart.text = "$minute:$second"
                } catch (e: IllegalArgumentException) {
                    Log.d("videoView", "AudioTrackUrl seems to be incorrectly formatted" + e.message, e)
                } catch (e: IllegalStateException) {
                    Log.d("videoView", "MediaPlayer is in an illegal state" + e.message, e)
                    try {
                        mediaPlayer.prepareAsync()
                    } catch (e1: Exception) {
                        e1.printStackTrace()
                    }
                } catch (e: Exception) {
                    Log.d("videoView", "MediaPlayer failed due to exception" + e.message, e)
                }
                mHandler.postDelayed(this, 1000)
            }
        })
    }

    private fun calculateTotalTime() {
        val min = (duration / 60)
        val sec = (duration % 60)
        var minute = ""
        var second = ""
        minute = if (min.toString().length == 1) "0$min" else min.toString()
        second = if (sec.toString().length == 1) "0$sec" else sec.toString()
        tvEnd.text = "$minute:$second"
    }


    fun clickListener() {
        ivRewind.setOnClickListener { v: View? ->
            if(isLoading) return@setOnClickListener
            try {
                Log.d("mediaPlayer", "clickListener: -"+mediaPlayer.currentPosition)
                if (mediaPlayer.currentPosition - 10000 > 10000) {
                    val mCurrentPosition = mediaPlayer.currentPosition / 1000 - 10
                    mediaPlayer.seekTo(mediaPlayer.currentPosition - 10000)
                    seekBar.progress = seekBar.progress - 10
                    val min = (mCurrentPosition / 60)
                    val sec = (mCurrentPosition % 60)
                    var minute = ""
                    var second = ""
                    minute = if (min.toString().length == 1) "0$min" else min.toString()
                    second = if (sec.toString().length == 1) "0$sec" else sec.toString()
                    tvStart.text = "$minute:$second"
                } else {
                    mediaPlayer.seekTo(0)
                    videoView.seekTo(0)
                    seekBar.progress = 0
                    tvStart.text = "00:00"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        ivForward.setOnClickListener { v: View? ->
            if(isLoading) return@setOnClickListener
            try {
                if ((mediaPlayer.currentPosition + 10000) / 1000 < duration) {
                    val mCurrentPosition = mediaPlayer.currentPosition / 1000 + 10
                    mediaPlayer.seekTo(mediaPlayer.currentPosition + 10000)
                    val min = (mCurrentPosition / 60)
                    val sec = (mCurrentPosition % 60)
                    var minute = ""
                    var second = ""
                    minute = if (min.toString().length == 1) "0$min" else min.toString()
                    second = if (sec.toString().length == 1) "0$sec" else sec.toString()
                    tvStart.text = "$minute:$second"
                } else {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.pause()
                    }
                    seekBar.progress = seekBar.max
                    val end = tvEnd.text.toString()
                    tvStart.text = end
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        seekBar.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(seekBar.progress == 0 && !mediaPlayer.isPlaying){
                    ivPlayPause.setImageResource(R.drawable.ic_play_circle)
                    ivPlayPause.tag = R.drawable.ic_play_circle
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                if(isLoading) return
                if (seekBar.progress < (duration - 10) && mediaPlayer.isPlaying && videoView.isPlaying) {
                    mediaPlayer.seekTo(seekBar.getProgress() * 1000);
                    videoView.seekTo(seekBar.getProgress() * 1000);
                }
            }
        })

        ivFullScreen.setOnClickListener {
            if(isLoading) return@setOnClickListener
            if(!isFullScreen){
                val metrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(metrics)
                val params = binding.rvPlay.layoutParams as ConstraintLayout.LayoutParams
                params.width = metrics.widthPixels
                params.height = metrics.heightPixels
                binding.rvPlay.layoutParams = params
                binding.tvBack.visibility = View.GONE
                binding.rvList.visibility = View.GONE
                isFullScreen = true
            }
            else{
                val params = binding.rvPlay.layoutParams as ConstraintLayout.LayoutParams
                params.width = 0
                params.height = 0
                params.startToEnd = binding.tvBack.id
                params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                params.bottomToTop = binding.rvList.id
                binding.rvPlay.layoutParams = params
                binding.tvBack.visibility = View.VISIBLE
                binding.rvList.visibility = View.VISIBLE
                isFullScreen = false
            }
        }

        ivPlayPause.setOnClickListener { v: View? ->
            if(isLoading) return@setOnClickListener
            if (ivPlayPause.tag as Int == R.drawable.ic_play_circle) {
                if((mediaPlayer.currentPosition / 1000) >= duration-1) {
                    mediaPlayer.seekTo(0)
                    videoView.seekTo(0)
                    seekBar.progress = 0
                    tvStart.text = "00:00"
                }
                if (!mediaPlayer.isPlaying) mediaPlayer.start()
                ivPlayPause.setImageResource(R.drawable.ic_pause_circle)
                ivPlayPause.tag = R.drawable.ic_pause_circle
                mCurrentPosition = videoView.getCurrentPosition()
            } else {
                if (mediaPlayer.isPlaying) mediaPlayer.stop()
                ivPlayPause.setImageResource(R.drawable.ic_play_circle)
                ivPlayPause.tag = R.drawable.ic_play_circle
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if(mediaPlayer.isPlaying)
                mediaPlayer.stop()
            if(videoView.isPlaying)
                videoView.stopPlayback()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        if(isFullScreen){
            val params = binding.rvPlay.layoutParams as ConstraintLayout.LayoutParams
            params.startToEnd = binding.tvBack.id
            params.width = 0
            params.height = 0
            params.setMargins(20)
            params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params.bottomToTop = binding.rvList.id
            binding.rvPlay.layoutParams = params
            binding.tvBack.visibility = View.VISIBLE
            binding.rvList.visibility = View.VISIBLE
        }else{
            super.onBackPressed()
            if(mediaPlayer.isPlaying)
                mediaPlayer.stop()
            if(videoView.isPlaying)
                videoView.stopPlayback()
        }
    }

}