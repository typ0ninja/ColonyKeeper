package com.team13.colonykeeper

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.team13.colonykeeper.databinding.ActivityAddBeeYardBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddBeeYardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBeeYardBinding
    private val pic_id = 1
    lateinit var mUri: Uri
    private lateinit var imageFilePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBeeYardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBeeYardButton.setOnClickListener{
            submitNewYard()
        }
        binding.addPictureButton.setOnClickListener{
            takePhoto()
        }
    }

    fun takePhoto() {
        val REQUEST_IMAGE_CAPTURE = 1
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            var photoFile: File? = null;
            try {
                photoFile = createImageFile();
                Log.d("erroring", "${photoFile}")
            } catch (e: IOException) {

            }
            if (photoFile != null) {
                Log.d("erroring", "didn't make the file")
                var photoURI = FileProvider.getUriForFile(this,                                                                                                "com.example.android.provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    photoURI);
                startActivityForResult(takePictureIntent,
                    REQUEST_IMAGE_CAPTURE);
            }
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
            val uri = data?.getData()
            binding.beeYardPicture.setImageURI(null)
            binding.beeYardPicture.setImageURI(uri)
//            val photo = data!!.extras!!["data"] as Bitmap?
//            binding.beeYardPicture.setImageBitmap(photo)

        }

    }


    fun submitNewYard(){
        //startActivity(Intent(this, YardListActivity::class.java))
        finish()
    }

}