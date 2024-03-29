package com.team13.colonykeeper

import android.R
import android.content.DialogInterface
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
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.ColonyViewModelFactory
import com.team13.colonykeeper.databinding.ActivityEditItemBinding
import java.io.File
import java.io.IOException
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*


class EditYardActivity: AppCompatActivity() {
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

        supportActionBar?.title = "Current Yard: " + ColonyApplication.instance.curYard.yardName

        binding.submitButton.setOnClickListener{
            submitChanges()
        }

        binding.photoButton.setOnClickListener{
            takePhoto()
        }

        binding.editHivePicture.setOnClickListener{
            takePhoto()
        }

        binding.deleteButton.setOnClickListener{
            deleteYard()
        }

    }

    override fun onStart() {
        super.onStart()
        if(colonyViewModel.getEditYardNameInProgress().length > 0){
            binding.beeHiveNameInput.setText(colonyViewModel.getEditYardNameInProgress())
        }
        if(colonyViewModel.getEditYardPictureInProgress() != Uri.EMPTY){
            binding.editHivePicture.setImageURI(colonyViewModel.getEditYardPictureInProgress())
            cameraPhotoFilePath = colonyViewModel.getEditYardPictureInProgress()!!
        }
    }

    fun takePhoto() {
        val REQUEST_IMAGE_CAPTURE = 1
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

//        if(cameraPhotoFilePath != Uri.EMPTY){
//            val file = File(URI(cameraPhotoFilePath.toString()))
//            file.delete()
//        }

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
        if(binding.beeHiveNameInput.text.toString() != ColonyApplication.instance.curYard.yardName &&
            !binding.beeHiveNameInput.text.isNullOrBlank()) {
            colonyViewModel.updateYardName(
                ColonyApplication.instance.curYard.id,
                binding.beeHiveNameInput.text.toString()
            )
            //update current hive name in UI
            ColonyApplication.instance.curYard.yardName = binding.beeHiveNameInput.text.toString()
        }
        if(cameraPhotoFilePath != Uri.EMPTY){
            colonyViewModel.updateYardPhoto(
                ColonyApplication.instance.curYard.id,
                cameraPhotoFilePath
            )
            //TODO: delete old photo
            //update current hive photo in UI
            ColonyApplication.instance.curYard.photoURI = cameraPhotoFilePath
        }
        colonyViewModel.resetEditYardNameInProgress()
        colonyViewModel.resetEditYardPictureInProgress()
        finished = true
        finish()
    }

    fun deleteYard(){
        var yardID = ColonyApplication.instance.curYard.id
        //var test = colonyViewModel.getYardHives(yardID).value
//        var hiveList = colonyViewModel.getYardHives(yardID).value?.toList()
//
//        for(hive in hiveList!!){
//            File(URI(hive.photoURI.toString())).delete()
//        }
        colonyViewModel.deleteYardHives(yardID)
        //File(URI(ColonyApplication.instance.curYard.photoURI.toString())).delete()
        colonyViewModel.deleteYard(yardID)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!finished){
            colonyViewModel.setEditYardNameInProgress(binding.beeHiveNameInput.text.toString())
            if(cameraPhotoFilePath != Uri.EMPTY){
                colonyViewModel.setEditYardPictureInProgress(cameraPhotoFilePath!!)
            }
        }
    }
}