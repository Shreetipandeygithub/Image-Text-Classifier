package com.shreeti.snaptext

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer

class TextRecognition : AppCompatActivity() {
    private lateinit var mCameraSource: CameraSource
    private lateinit var textRecognizer: TextRecognizer
    private val tag:String?="TextRecognition"
    private lateinit var txtViewResult:TextView
    lateinit var surface_camera_preview:SurfaceView
    val keyResult: String = "key_result"
    private val MY_PERMISSIONS_REQUEST_CAMERA: Int = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_recognition)


        requestForPermission()
        //  Create text Recognizer
        textRecognizer = TextRecognizer.Builder(this).build()
        if (!textRecognizer.isOperational) {
            Toast.makeText(this,"Dependencies are not loaded yet...please try after few moment!!",Toast.LENGTH_SHORT).show()
            Log.e(tag,"Dependencies are downloading....try after few moment")
            return
        }

        //  Init camera source to use high resolution and auto focus
        mCameraSource = CameraSource.Builder(applicationContext, textRecognizer)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1280, 1024)
            .setAutoFocusEnabled(true)
            .setRequestedFps(2.0f)
            .build()
        surface_camera_preview.holder.addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (isCameraPermissionGranted()) {
                        mCameraSource.start(surface_camera_preview.holder)
                    } else {
                        requestForPermission()
                    }
                } catch (e: Exception) {
                    toast("Error:  ${e.message}")
                }
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                mCameraSource.stop()
            }
        })
        textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
                val items = detections.detectedItems

                if (items.size() <= 0) {
                    return
                }

                txtViewResult.post {
                    val stringBuilder = StringBuilder()
                    for (i in 0 until items.size()) {
                        val item = items.valueAt(i)
                        stringBuilder.append(item.value)
                        stringBuilder.append("\n")
                    }
                    txtViewResult.text = stringBuilder.toString()
                }
            }
        })
    }
    fun toast(text:String){
        Toast.makeText(this@TextRecognition,text,Toast.LENGTH_SHORT).show()
    }
    private fun requestForPermission() {

        if (ContextCompat.checkSelfPermission(this@TextRecognition,
                android.Manifest.permission.CAMERA)
            !=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@TextRecognition,
                    android.Manifest.permission.CAMERA)){

            }else{
                ActivityCompat.requestPermissions(this@TextRecognition,
                    arrayOf(android.Manifest.permission.CAMERA),
                    MY_PERMISSIONS_REQUEST_CAMERA)
            }
        }else{

        }
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this@TextRecognition,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        when(requestCode){
            MY_PERMISSIONS_REQUEST_CAMERA->{
                if ((grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)){

                }else{
                    requestForPermission()
                }
                return
            }
            else->{

            }
        }
    }
}