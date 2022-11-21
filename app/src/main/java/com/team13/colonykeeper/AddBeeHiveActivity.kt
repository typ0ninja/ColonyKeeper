package com.team13.colonykeeper

import android.R.attr.data
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.team13.colonykeeper.database.*
import com.team13.colonykeeper.databinding.ActivityAddBeeHiveBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddBeeHiveActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddBeeHiveBinding
    private val pic_id = 1
    var cameraPhotoFilePath: Uri = ColonyApplication.instance.DEFAULT_URI
    private lateinit var imageFilePath: String
    private val colonyViewModel: ColonyViewModel by viewModels {
        ColonyViewModelFactory((application as ColonyApplication).colonyRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBeeHiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ColonyApplication.instance.curYard.yardName +
                " / New Hive"

        binding.addBeeHiveButton.setOnClickListener{
            submitNewHive()
        }

        binding.addPictureButton.setOnClickListener{
            takePhoto()
        }

        binding.beeHivePicture.setOnClickListener{
            takePhoto()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == pic_id){
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, cameraPhotoFilePath)
            binding.beeHivePicture.setImageBitmap(bitmap)
        }
    }

    fun submitNewHive(){
        //startActivity(Intent(this, HiveListActivity::class.java))
        if(binding.beeHiveNameInput.text.isNullOrBlank()){
            //Enter text
            Toast.makeText(
                applicationContext, "Please provide a name",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            //got enough info can submit
            var newHive: Hive = Hive(
                binding.beeHiveNameInput.text.toString(),
                ColonyApplication.instance.curYard.id,
                cameraPhotoFilePath
            )
            Log.d("Hive", "hive id:${newHive.id}")
            colonyViewModel.insertHive(newHive)
            finish()
        }
    }
}