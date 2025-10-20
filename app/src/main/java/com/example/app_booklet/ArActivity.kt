package com.example.app_booklet

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import io.github.sceneview.ar.ARSceneView
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

        // Vincular el ARSceneView
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
        val modeloGlb = intent.getStringExtra("modeloGlb") ?: return
        val uriModelo = Uri.parse(modeloGlb)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val modelInstance = modelLoader.loadModelInstance(uriModelo.toString())
                modelInstance?.let { instance ->
                    // Crear el nodo del modelo
                    modelNode = ModelNode(
                        modelInstance = instance,
                        scaleToUnits = 0.3f,
                        centerOrigin = Position(0.0f, 0.0f, -7.0f)
                    ).apply {
                        rotation = Rotation(0.0f, 180.0f, 0.0f)
                    }

                    arSceneView.addChildNode(modelNode!!)
                    iniciarRotacionLenta()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /** Rotación continua del modelo */
    private fun iniciarRotacionLenta() {
        CoroutineScope(Dispatchers.Default).launch {
            var angulo = 0f
            while (true) {
                modelNode?.let {
                    angulo += 0.5f // velocidad de rotación (en grados aprox.)
                    if (angulo >= 360f) angulo = 0f
                    it.rotation = Rotation(0f, angulo, 0f)
                }
                delay(30) // control de velocidad (ajusta si quieres que gire más rápido/lento)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        arSceneView.destroy()
    }
}