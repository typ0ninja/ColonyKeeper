package com.team13.colonykeeper

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.team13.colonykeeper.databinding.ActivityAddInspectionBinding
import java.util.*


class AddInspectionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddInspectionBinding
    private val pic_id = 1
    private lateinit var voiceResult: ActivityResultLauncher<Intent>
    private lateinit var speechIntent: Intent

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

        binding.addInspectionVoiceButton.setOnClickListener {
            voiceText()
        }

        speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault())

        voiceResult = registerVoice()

    }

    fun registerVoice(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == RESULT_OK){
                //get the result item
                val voiceResult = it.data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS)
                //get existing text already in box and concat new text in on a new line
                var notesText: String = binding.inspectionTextInput.text.toString() + "\n\n" +
                        voiceResult?.get(0).toString()
                //update textbox
                binding.inspectionTextInput.setText(notesText)
            }
        }
    }

    fun submitInspection(){
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> voiceText()
            KeyEvent.KEYCODE_VOLUME_UP -> takePhoto()
        }
        return true
    }

    fun voiceText(){
        Toast.makeText(applicationContext, "Volume down", Toast.LENGTH_SHORT).show()
        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault())

        voiceResult.launch(speechIntent)
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