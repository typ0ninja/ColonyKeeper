package com.team13.colonykeeper

import android.Manifest
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.team13.colonykeeper.databinding.ActivityAddBeeYardBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34

class AddBeeYardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBeeYardBinding
    private val pic_id = 1
    var cameraPhotoFilePath: Uri? = null
    private lateinit var imageFilePath: String
    private var latitude = -1.0
    private var longitude = -181.0


    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
            }
            }
        }

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
        binding = ActivityAddBeeYardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)



        binding.addBeeYardButton.setOnClickListener{
            submitNewYard()
        }
        binding.addPictureButton.setOnClickListener{
            takePhoto()
        }


    }

    override fun onStart() {
        super.onStart()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    latitude = location.latitude!!
                    longitude = location.longitude!!
                }
                else{
                    latitude = -1.0
                    longitude = -181.0
                }
                Log.d("TESTING THIS", "latitude: ${latitude}")
                Log.d("TESTING THIS", "longitude: ${longitude}")
            }


    }

    private fun foregroundPermissionApproved(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE -> when {
                grantResults.isEmpty() ->
                    Log.d("AddBeeYard", "User interaction was cancelled.")
                    // If user interaction was interrupted, the permission request
                    // is cancelled and you receive empty arrays.
                grantResults[0] == PackageManager.PERMISSION_GRANTED ->
                    // Permission was granted.
//                    foregroundOnlyLocationService?.subscribeToLocationUpdates()
                Log.d("Testing", "Permission granted")
                else -> {
                    // Permission denied.

                    Snackbar.make(
                        findViewById(R.id.app_screen_title),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(R.string.settings) {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                BuildConfig.APPLICATION_ID,
                                null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        .show()
                }
            }
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
                cameraPhotoFilePath = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
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

           binding.beeYardPicture.setImageBitmap(bitmap)
//            val photo = data!!.extras!!["data"] as Bitmap?
//            binding.beeYardPicture.setImageBitmap(photo)

        }

    }


    fun submitNewYard(){
        //startActivity(Intent(this, YardListActivity::class.java))
        finish()
    }

}