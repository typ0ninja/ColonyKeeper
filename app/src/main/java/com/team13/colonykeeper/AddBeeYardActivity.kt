package com.team13.colonykeeper

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.databinding.ActivityAddBeeYardBinding
import com.team13.colonykeeper.databinding.ActivityMainBinding


class AddBeeYardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBeeYardBinding
    private val pic_id = 1

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
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == pic_id){
            val photo = data!!.extras!!["data"] as Bitmap?
            binding.beeYardPicture.setImageBitmap(photo)

        }

    }


    fun submitNewYard(){
        //startActivity(Intent(this, YardListActivity::class.java))
        finish()
    }

}