package com.team13.colonykeeper

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.databinding.ActivityAddInspectionBinding
import com.team13.colonykeeper.model.PlanInspectionViewModel

class AddInspectionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddInspectionBinding
    private val pic_id = 1

    private val viewModel: PlanInspectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddInspectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.viewModel = viewModel
        viewModel.getWeekForcast()

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
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == pic_id){
            //do something with picture
            val photo = data!!.extras!!["data"] as Bitmap?

        }
    }

}