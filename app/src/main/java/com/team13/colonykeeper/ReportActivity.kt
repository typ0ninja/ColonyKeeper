package com.team13.colonykeeper

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageState
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.ColonyViewModelFactory
import com.team13.colonykeeper.database.Inspections
import com.team13.colonykeeper.databinding.ActivityReportBinding
import java.io.File
import java.io.FileOutputStream


class ReportActivity: AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap
    var finished = false

    var PERMISSION_CODE = 101
    var inspections: List<Inspections> = emptyList()

    private val colonyViewModel: ColonyViewModel by viewModels {
        ColonyViewModelFactory((application as ColonyApplication).colonyRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Generate Report"

        binding.pdfNameInputField.setText(colonyViewModel.reportName)



        colonyViewModel.getInspections()
            .observe(this) {
                inspections = it
                    //var cameraPhotoFilePath = inspections.get(0).photoList.get(0).toUri()
                var cameraPhotoFilePath = ColonyApplication.instance.DEFAULT_URI
                    bmp =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, cameraPhotoFilePath)
                    //bmp = BitmapFactory.decodeResource(resources, inspections.get(0).photoList.get(0).)
                    scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false)
            }

        // on below line we are checking permission
        if (checkPermissions()) {
            // if permission is granted we are displaying a toast message.
            Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show()
        } else {
            // if the permission is not granted
            // we are calling request permission method.
            requestPermission()
        }

        binding.makePDF.setOnClickListener{
            if(!inspections.isEmpty()) {
                makePdf()
            } else if(binding.pdfNameInputField.text.isNullOrBlank()) {
                Toast.makeText(
                    applicationContext, "Please name the report",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                Toast.makeText(
                    applicationContext, "Please complete an inspection first",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun makePdf(){
        var reportPdf: PdfDocument = PdfDocument()

        var paint: Paint = Paint()
        var title: Paint = Paint()

        var pageHeight = 1120
        var pageWidth = 792

        var myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        var myPage: PdfDocument.Page = reportPdf.startPage(myPageInfo)

        var canvas: Canvas = myPage.canvas

        for(inspect in inspections){
            inspect.name

        }

        canvas.drawBitmap(scaledbmp, 56F, 40F, paint)

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

        title.textSize = 15F

        title.setColor(ContextCompat.getColor(this, R.color.black))

        canvas.drawText("Inspection Reports:", 209F, 100F, title)
        canvas.drawText("Hive info here", 209F, 80F, title)
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.setColor(ContextCompat.getColor(this, R.color.black))
        title.textSize = 15F

        title.textAlign = Paint.Align.CENTER
        canvas.drawText("More test info.", 396F, 560F, title)

        reportPdf.finishPage(myPage)

        Log.d("Storage", "Storage State: ${Environment.getExternalStorageState()}")
        var fileName: String = binding.pdfNameInputField.text.toString()
        fileName += ".pdf"
        val file: File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)
        try {
            // after creating a file name we will
            // write our PDF file to that location.
            reportPdf.writeTo(FileOutputStream(file))

            // on below line we are displaying a toast message as PDF file generated..
            Toast.makeText(applicationContext, "PDF file generated..", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // below line is used
            // to handle error
            e.printStackTrace()

            // on below line we are displaying a toast message as fail to generate PDF
            Toast.makeText(applicationContext, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                .show()
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        reportPdf.close()
    }
    fun checkPermissions(): Boolean {
        // on below line we are creating a variable for both of our permissions.

        // on below line we are creating a variable for
        // writing to external storage permission
        var writeStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            WRITE_EXTERNAL_STORAGE
        )

        // on below line we are creating a variable
        // for reading external storage permission
        var readStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            READ_EXTERNAL_STORAGE
        )

        // on below line we are returning true if both the
        // permissions are granted and returning false
        // if permissions are not granted.
        return writeStoragePermission == PackageManager.PERMISSION_GRANTED
                && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    // on below line we are creating a function to request permission.
    fun requestPermission() {

        // on below line we are requesting read and write to
        // storage permission for our application.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), PERMISSION_CODE
        )
    }

    // on below line we are calling
    // on request permission result.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // on below line we are checking if the
        // request code is equal to permission code.
        if (requestCode == PERMISSION_CODE) {

            // on below line we are checking if result size is > 0
            if (grantResults.size > 0) {

                // on below line we are checking
                // if both the permissions are granted.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED) {

                    // if permissions are granted we are displaying a toast message.
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show()

                } else {

                    // if permissions are not granted we are
                    // displaying a toast message as permission denied.
                    Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        colonyViewModel.reportName = binding.pdfNameInputField.text.toString()

    }
}