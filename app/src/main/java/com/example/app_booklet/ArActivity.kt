package com.example.app_booklet

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import io.github.sceneview.ar.ARSceneView
import io.github.sceneview.node.ModelNode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.loaders.ModelLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArActivity : AppCompatActivity() {

    private lateinit var arSceneView: ARSceneView
    private lateinit var modelLoader: ModelLoader
    private var modelNode: ModelNode? = null

    // Permiso de cámara
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) iniciarAR() else finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        arSceneView = findViewById(R.id.arSceneView)
        modelLoader = ModelLoader(engine = arSceneView.engine, context = this)

        // Verificar permiso de cámara
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            iniciarAR()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun iniciarAR() {
        intent.getStringExtra("modeloGlb")?.let { modeloGlb ->
            val uriModelo = Uri.parse(modeloGlb)

            CoroutineScope(Dispatchers.Main).launch {
                runCatching {
                    modelLoader.loadModelInstance(uriModelo.toString())
                }.onSuccess { instance ->
                    instance?.let {
                        val modelNode = ModelNode(
                            modelInstance = it,
                            scaleToUnits = 1.0f,
                            centerOrigin = Position(0.0f, 0.0f, -1.0f)
                        ).apply {
                            rotation = Rotation(0.0f, 180.0f, 0.0f)
                        }
                        arSceneView.addChildNode(modelNode)
                    }
                }.onFailure { e ->
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        arSceneView.destroy()
    }
}