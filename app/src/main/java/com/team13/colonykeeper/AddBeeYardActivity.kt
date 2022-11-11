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

class AddBeeYardActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener  {

    private lateinit var binding: ActivityAddBeeYardBinding
    private val pic_id = 1
    var cameraPhotoFilePath: Uri? = null
    private lateinit var imageFilePath: String

//    private var foregroundOnlyLocationServiceBound = false

    // Provides location updates for while-in-use feature.
//    private var foregroundOnlyLocationService: ForegroundOnlyLocationService? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    // Listens for location broadcasts from ForegroundOnlyLocationService.
//    private lateinit var foregroundOnlyBroadcastReceiver: AddBeeYardActivity.ForegroundOnlyBroadcastReceiver

//    private lateinit var sharedPreferences: SharedPreferences

//    private val foregroundOnlyServiceConnection = object : ServiceConnection {
//
//        override fun onServiceConnected(name: ComponentName, service: IBinder) {
//            val binder = service as ForegroundOnlyLocationService.LocalBinder
//            foregroundOnlyLocationService = binder.service
//            foregroundOnlyLocationServiceBound = true
//        }
//
//        override fun onServiceDisconnected(name: ComponentName) {
//            foregroundOnlyLocationService = null
//            foregroundOnlyLocationServiceBound = false
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBeeYardBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        foregroundOnlyBroadcastReceiver = ForegroundOnlyBroadcastReceiver()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

//        sharedPreferences =
//            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)

//        val enabled = sharedPreferences.getBoolean(
//            SharedPreferenceUtil.KEY_FOREGROUND_ENABLED, false)

//        if (enabled) {
//            foregroundOnlyLocationService?.unsubscribeToLocationUpdates()
//        } else {
//            if (foregroundPermissionApproved()) {
//                foregroundOnlyLocationService?.subscribeToLocationUpdates()
//            } else {
//                requestForegroundPermissions()
//            }
//        }


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
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
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
                Log.d("TESTING THIS", "${location.toString()}")
            }

//        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

//        val serviceIntent = Intent(this, ForegroundOnlyLocationService::class.java)
//        bindService(serviceIntent, foregroundOnlyServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
//        LocalBroadcastManager.getInstance(this).registerReceiver(
//            foregroundOnlyBroadcastReceiver,
//            IntentFilter(
//                ForegroundOnlyLocationService.ACTION_FOREGROUND_ONLY_LOCATION_BROADCAST)
//        )
    }

    override fun onPause() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(
//            foregroundOnlyBroadcastReceiver
//        )
        super.onPause()
    }

    override fun onStop() {
//        if (foregroundOnlyLocationServiceBound) {
//            unbindService(foregroundOnlyServiceConnection)
//            foregroundOnlyLocationServiceBound = false
//        }
//        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)

        super.onStop()
    }

    private fun foregroundPermissionApproved(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun requestForegroundPermissions() {
        val provideRationale = foregroundPermissionApproved()

        // If the user denied a previous request, but didn't check "Don't ask again", provide
        // additional rationale.
        if (provideRationale) {
            Snackbar.make(
                findViewById(R.id.app_screen_title),
                R.string.permission_rationale,
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.ok) {
                    // Request permission
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
                    )
                }
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
            )
        }
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

    private inner class ForegroundOnlyBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val location = intent.getParcelableExtra<Location>(
                ForegroundOnlyLocationService.EXTRA_LOCATION
            )

            Log.d("TESTING", "${location}")

            if (location != null) {
                Log.d("TESTING", "${location}")
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        // Updates button states if new while in use location is added to SharedPreferences.
        if (key == SharedPreferenceUtil.KEY_FOREGROUND_ENABLED) {
            Log.d("TESTING", "in location changed"
            )
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