package com.team13.colonykeeper

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.team13.colonykeeper.databinding.ActivityAddInspectionBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddInspectionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddInspectionBinding
    private val pic_id = 1
    var cameraPhotoFilePath: Uri? = null
    private lateinit var imageFilePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInspectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addPictureButton.setOnClickListener {
            takePhoto()
        }
        binding.submitInspectionButton.setOnClickListener {
            submitInspection()
        }
    }

    fun submitInspection(){
        finish()
    }

    fun takePhoto() {
        val REQUEST_IMAGE_CAPTURE = 1
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        //Create a file to store the image
        var photoFile: File? = null;
        try {
            photoFile = createImageFile();
            Log.d("erroring", "${photoFile}")
        } catch (e: IOException) {
            Log.d("erroring", "didn't make the file")
        }
        if (photoFile != null) {
            Log.d("erroring", "didn't make the file")
            cameraPhotoFilePath = FileProvider.getUriForFile(
                Objects.requireNonNull(getApplicationContext()),
                BuildConfig.APPLICATION_ID + ".provider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                cameraPhotoFilePath);
            startActivityForResult(takePictureIntent,
                REQUEST_IMAGE_CAPTURE);
        }


//        try {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//        } catch (e: ActivityNotFoundException) {
//            // display error state to the user
//        }
    }

    fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        imageFilePath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pic_id) {
            val bitmap =
                MediaStore.Images.Media.getBitmap(this.contentResolver, cameraPhotoFilePath)

//            binding.beeYardPicture.setImageBitmap(bitmap)
//            val photo = data!!.extras!!["data"] as Bitmap?
//            binding.beeYardPicture.setImageBitmap(photo)
        }
    }
}