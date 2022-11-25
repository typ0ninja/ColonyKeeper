package com.team13.colonykeeper

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.team13.colonykeeper.adapter.YardAdapter
import com.team13.colonykeeper.adapter.prevInspectionAdapter
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.ColonyViewModelFactory
import com.team13.colonykeeper.database.Inspections
import com.team13.colonykeeper.databinding.ActivityPastInspectionBinding
import java.io.File
import java.io.FileOutputStream


class PastInspectionActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPastInspectionBinding
    private var previousInspections: List<Inspections> = emptyList()
    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap

    var PERMISSION_CODE = 101

    private val colonyViewModel: ColonyViewModel by viewModels {
        ColonyViewModelFactory((application as ColonyApplication).colonyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPastInspectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ColonyApplication.instance.curYard.yardName +
                " / " + ColonyApplication.instance.curHive.hiveName

        val prevInspectionAdapter: prevInspectionAdapter = prevInspectionAdapter(applicationContext)
        binding.prevInspectionGridRecyclerView.adapter = prevInspectionAdapter

        colonyViewModel.getInspections()
            .observe(this) {
                Log.d("JSON", "Size: ${it.size}")
                //fill adapter
                prevInspectionAdapter.addInspectionList(it)
                prevInspectionAdapter.notifyDataSetChanged()
                //set test pic
                setInspectionList(it)
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

        binding.exportPdf.setOnClickListener{
            if(!previousInspections.isEmpty()) {
                makePdf()
            } else {
                Toast.makeText(
                    applicationContext, "Please complete an inspection first",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun setInspectionList(inspections: List<Inspections>){
        previousInspections = inspections
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

        canvas.drawBitmap(scaledbmp, 56F, 40F, paint)

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

        title.textSize = 15F

        title.setColor(ContextCompat.getColor(this, R.color.black))

        canvas.drawText("This is a test report", 209F, 100F, title)
        canvas.drawText("Hive info here", 209F, 80F, title)
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.setColor(ContextCompat.getColor(this, R.color.black))
        title.textSize = 15F

        title.textAlign = Paint.Align.CENTER
        canvas.drawText("More test info.", 396F, 560F, title)

        reportPdf.finishPage(myPage)

        Log.d("Storage", "Storage State: ${Environment.getExternalStorageState()}")
        val file: File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "InspectionReport.pdf")
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
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        // on below line we are creating a variable
        // for reading external storage permission
        var readStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
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
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), PERMISSION_CODE
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
}