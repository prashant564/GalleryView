package com.example.gallerytest.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.gallerytest.R
import com.example.gallerytest.adapter.GalleryPhotoAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val READ_REQUEST_CODE = 3
    private val TAG = "PermissionDemo"
    var allImagePaths : ArrayList<String> = ArrayList()
    private lateinit var galleryPhotoAdapter: GalleryPhotoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isReadStoragePermissionGranted()
        galleryPhotoAdapter = GalleryPhotoAdapter(allImagePaths)
        rv_images.isNestedScrollingEnabled=false
        rv_images.adapter = galleryPhotoAdapter
    }

    private fun isReadStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                getImagePath()
                true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_REQUEST_CODE)
                false
            }
        } else {
            getImagePath()
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission granted")
                    getImagePath()
                } else {
                    Log.d(TAG, "Permission denied")
                }
            }
        }
    }

    @SuppressLint("InlinedApi")
    fun getImagePath(){
        val cursor: Cursor?
        var columnData: Int
        var absolutePathOfImage: String?
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        cursor = applicationContext.contentResolver.query(uri,
                projection,
                MediaStore.Audio.Media.DATA + " like ? ", arrayOf("%WhatsApp Images%"),
                "$orderBy DESC")
        if(cursor!=null){
            while(cursor.moveToNext()){
                columnData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                absolutePathOfImage = cursor.getString(columnData)
                allImagePaths.add(absolutePathOfImage)
            }
        }
    }
}