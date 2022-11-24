package com.team13.colonykeeper

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import java.net.URI
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.ColonyViewModelFactory
import com.team13.colonykeeper.databinding.ActivityEditItemBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EditHiveActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEditItemBinding
    private val pic_id = 1
    private lateinit var imageFilePath: String
    var cameraPhotoFilePath: Uri = Uri.EMPTY
    private var finished = false

    private val colonyViewModel: ColonyViewModel by viewModels {
        ColonyViewModelFactory((application as ColonyApplication).colonyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ColonyApplication.instance.curYard.yardName +
                " / " + ColonyApplication.instance.curHive.hiveName

        binding.submitButton.setOnClickListener{
            submitChanges()
        }

        binding.photoButton.setOnClickListener{
            takePhoto()
        }

        binding.editHivePicture.setOnClickListener{
            takePhoto()
        }
    }

    override fun onStart() {
        super.onStart()
        if(colonyViewModel.getEditHiveNameInProgress().length > 0){
            binding.beeHiveNameInput.setText(colonyViewModel.getEditHiveNameInProgress())
        }
        if(colonyViewModel.getEditHivePictureInProgress() != Uri.EMPTY){
            binding.editHivePicture.setImageURI(colonyViewModel.getEditHivePictureInProgress())
            cameraPhotoFilePath = colonyViewModel.getEditHivePictureInProgress()!!
        }
    }

    fun takePhoto() {
        val REQUEST_IMAGE_CAPTURE = 1
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if(cameraPhotoFilePath != Uri.EMPTY){
            val file = File(URI(cameraPhotoFilePath.toString()))
            file.delete()
        }

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
            binding.editHivePicture.setImageBitmap(bitmap)
        }
    }

    fun submitChanges(){

        if(binding.beeHiveNameInput.text.toString() != ColonyApplication.instance.curHive.hiveName &&
            !binding.beeHiveNameInput.text.isNullOrBlank()) {
            colonyViewModel.updateHiveName(
                ColonyApplication.instance.curHive.id,
                binding.beeHiveNameInput.text.toString()
            )
            //update current hive name in UI
            ColonyApplication.instance.curHive.hiveName = binding.beeHiveNameInput.text.toString()
        }
        if(cameraPhotoFilePath != Uri.EMPTY){
            colonyViewModel.updateHivePhoto(
                ColonyApplication.instance.curHive.id,
                cameraPhotoFilePath
            )
            //TODO: delete old photo
            //update current hive photo in UI
            ColonyApplication.instance.curHive.photoURI = cameraPhotoFilePath
        }
        colonyViewModel.resetEditHiveNameInProgress()
        colonyViewModel.resetEditHivePictureInProgress()
        finished = true
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!finished){
            colonyViewModel.setEditHiveNameInProgress(binding.beeHiveNameInput.text.toString())
            if(cameraPhotoFilePath != Uri.EMPTY){
                colonyViewModel.setEditHivePictureInProgress(cameraPhotoFilePath!!)
            }
        }
    }
}