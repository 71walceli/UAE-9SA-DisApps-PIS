package com.example.a20231211_taller

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.random.Random


class InflorescenciasActivity : AppCompatActivity() {
    val ID_REQUEST_MULTIPLE_PERMISSIONS = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inflorescencias)

        // TODO Si no se da el permiso, salir de la actividad
        checkAndRequestPermissions(this)
    }

    fun checkAndRequestPermissions(context: Activity?): Boolean {
        val WExtstorePermission = ContextCompat.checkSelfPermission(
            context!!,
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )
        val cameraPermission = ContextCompat.checkSelfPermission(
            context,
            "android.permission.CAMERA"
        )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add("android.permission.CAMERA")
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add("android.permission.WRITE_EXTERNAL_STORAGE")
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                context, listPermissionsNeeded
                    .toTypedArray<String>(),
                ID_REQUEST_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults!!)
        when (requestCode) {
            ID_REQUEST_MULTIPLE_PERMISSIONS ->
                if (
                    ContextCompat.checkSelfPermission(
                        this,
                        "android.permission.CAMERA"
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        applicationContext,
                        "El permiso es necesario para facilitar hacer predicciones a partir de una foto tomada.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else if (
                    ContextCompat.checkSelfPermission(
                        this,
                        "android.permission.WRITE_EXTERNAL_STORAGE"
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        applicationContext,
                        "El permiso es requerido para poder guardar su imagen anotada..",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
        }
    }

    private fun chooseImage(context: Context) {
        val optionsMenu = arrayOf<CharSequence>(
            "Take Photo",
            "Choose from Gallery",
            "Exit"
        ) // create a menuOption Array
        // create a dialog for showing the optionsMenu
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        // set the items in builder
        builder.setItems(optionsMenu,
            DialogInterface.OnClickListener { dialogInterface, i ->
                if (optionsMenu[i] == "Take Photo") {
                    // Open the camera and get the photo
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, 0)
                } else if (optionsMenu[i] == "Choose from Gallery") {
                    // choose from  external storage
                    val pickPhoto =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, 1)
                } else if (optionsMenu[i] == "Exit") {
                    dialogInterface.dismiss()
                }
            })
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CANCELED) {
            var imageChosen = false
            when (requestCode) {
                0 -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = data.extras!!["data"] as Bitmap?
                    findViewById<ImageView>(R.id.foto).setImageBitmap(selectedImage)
                    imageChosen = true
                }

                1 -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (selectedImage != null) {
                        val cursor =
                            contentResolver.query(selectedImage, filePathColumn, null, null, null)
                        if (cursor != null) {
                            cursor.moveToFirst()
                            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                            val picturePath = cursor.getString(columnIndex)
                            findViewById<ImageView>(R.id.foto).setImageBitmap(BitmapFactory.decodeFile(picturePath))
                            imageChosen = true
                            cursor.close()
                        }
                    }
                }
            }
            if (imageChosen) {
                Toast.makeText(this,
                    "Se detectaron ${Random.nextInt(100)} inflorescencias.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun tomarFoto_onClick(view: View) {
        chooseImage(this)
    }

}