package com.team13.colonykeeper

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.util.Log
import android.view.KeyEvent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.team13.colonykeeper.adapter.InspectionPicAdapter
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.ColonyViewModelFactory
import com.team13.colonykeeper.database.Inspections
import com.team13.colonykeeper.databinding.ActivityAddInspectionBinding
import java.io.File
import java.io.IOException
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class AddInspectionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddInspectionBinding
    private val pic_id = 1

    var cameraPhotoFilePath: Uri? = Uri.EMPTY
    private lateinit var imageFilePath: String
    private lateinit var voiceResult: ActivityResultLauncher<Intent>
    private lateinit var speechIntent: Intent
    var picList: MutableList<String> = mutableListOf<String>()
    lateinit var inspectionPicAdapter: InspectionPicAdapter
    private var finished = false


    private val colonyViewModel: ColonyViewModel by viewModels {
        ColonyViewModelFactory((application as ColonyApplication).colonyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInspectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ColonyApplication.instance.curYard.yardName +
                " / " + ColonyApplication.instance.curHive.hiveName

        inspectionPicAdapter = InspectionPicAdapter(applicationContext, 3)

        binding.inspectPicRecyclerView.adapter = inspectionPicAdapter
        //observe the actual database info and assign it to gridview list via lambda
//        colonyViewModel.getInspections()
//            .observe(this) {
//                inspectionPicAdapter.addInspectionList(it)
//                inspectionPicAdapter.notifyDataSetChanged()
//            }

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

    override fun onStart() {
        super.onStart()
        if(colonyViewModel.getInspectionNotes().length > 0){
            binding.inspectionTextInput.setText(colonyViewModel.getInspectionNotes())
        }
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> voiceText()
            KeyEvent.KEYCODE_VOLUME_UP -> takePhoto()
        }
        return true
    }

    fun voiceText(){
        //Toast.makeText(applicationContext, "Volume down", Toast.LENGTH_SHORT).show()
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

//        if(cameraPhotoFilePath != Uri.EMPTY){
//            Log.d("camera_test", "path: ${cameraPhotoFilePath.toString()}")
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

            cameraPhotoFilePath = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                BuildConfig.APPLICATION_ID + ".provider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                cameraPhotoFilePath)
            startActivityForResult(takePictureIntent,
                REQUEST_IMAGE_CAPTURE)
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
            //do something with picture
            //val photo = data!!.extras!!["data"] as Bitmap?
            //add Uri to list of pics
            picList.add(cameraPhotoFilePath!!.toString())
            inspectionPicAdapter.inspectionPics = picList.toList()
            inspectionPicAdapter.notifyDataSetChanged()
            Log.d("inspect", "Size of dataset: ${inspectionPicAdapter.inspectionPics.size}")
        }
    }

    fun submitInspection(){

        val today = SimpleDateFormat("MM/dd/YYYY")
        val todaysDate: String = today.format(Date())

        var newInspection: Inspections = Inspections( ColonyApplication.instance.curHive.hiveName, todaysDate,
            binding.inspectionTextInput.text.toString(), ColonyApplication.instance.curYard.id)
        newInspection.photoList = picList.toTypedArray()
        Log.d("Inspection", "Size of photoset: ${newInspection.photoList.size}")
        Log.d("Inspection", "hive id:${newInspection.id}")
        colonyViewModel.resetInspectionNotes()
        colonyViewModel.addInspection(newInspection)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!finished){
            colonyViewModel.setInspectionNotes(binding.inspectionTextInput.toString())
        }
    }
}