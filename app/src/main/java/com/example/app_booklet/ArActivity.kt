package com.example.app_booklet

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.ar.core.Anchor
import com.vuforia.*
import io.github.sceneview.ar.ARSceneView
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.*

class ArActivity : AppCompatActivity(), Vuforia.InitListener {

    private lateinit var arSceneView: ARSceneView
    private lateinit var modelLoader: ModelLoader
    private var modelNode: ModelNode? = null
    private var glbModelName: String? = null

    // 🔑 Coloca aquí tu licencia válida de Vuforia
    private val VUFORIA_LICENSE_KEY = "ASofy4f/////AAABmeVZJeE4dUBVtDmgCrj+FOyJfcgchpkb2VEK+OuOSp21dN1bPlFLyvJCZWgomm+t2X1Xv39yKZkK3I8yfbtBKpYep0VTXzzrxeVGEkPGBW2r4eXXUI5jMSkox0i8oSR+B7a8D0P8hbmd2gKFexdBnUXezknK7gNZdyLUi7ubWYRcf/O5lMnRbV1yOVM/oEsMOnNpnuF5JZdTo7qI7+xeOv6aGvX1U7ogYhkoOeU+eYv2Hk0a38alyjIITW7Q0t5f6Pk0YnaLARHKEPO8ajeujZJ6d3Q7z+Qk8tvd6x+DCZ81TbwlIhjaIdWhJac9wIReHIXCJmbPXYe9ihR+bUussL+2Zb6lb/QqvpUVpKUVitJw"

    private var isVuforiaInitialized = false
    private var isTrackingStarted = false
    private var modelAnchorPosition: Position? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) iniciarAR() else finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cargar la librería nativa de Vuforia
        try {
            System.loadLibrary("libVuforiaEngine")
        } catch (e: UnsatisfiedLinkError) {
            Log.e("Vuforia", "Error al cargar librería nativa: ${e.message}")
            Toast.makeText(this, "Error crítico: VuforiaEngine.so no encontrado.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        setContentView(R.layout.activity_ar)

        arSceneView = findViewById(R.id.arSceneView)
        modelLoader = ModelLoader(engine = arSceneView.engine, context = this)
        glbModelName = intent.getStringExtra("modeloGlb")

        // Verificar permisos de cámara
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            iniciarAR()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun iniciarAR() {
        Log.d("Vuforia", "Inicializando Vuforia...")
        Vuforia.init(this, this)
    }

    override fun onInitFinished(result: Int) {
        if (result == Vuforia.INIT_SUCCESS) {
            isVuforiaInitialized = true
            Log.d("Vuforia", "✅ Vuforia inicializado correctamente")

            runOnUiThread {
                if (Vuforia.setLicenseKey(VUFORIA_LICENSE_KEY)) {
                    Vuforia.start()
                    setupDeviceTracking()
                    loadGlbModelAndStartRotation()
                } else {
                    Log.e("Vuforia", "❌ Licencia Vuforia inválida o faltante")
                    Toast.makeText(this, "Clave de licencia inválida.", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        } else {
            val errorMsg = when (result) {
                Vuforia.INIT_LICENSE_ERROR_INVALID_KEY -> "Clave de Licencia Inválida"
                Vuforia.INIT_LICENSE_ERROR_MISSING_KEY -> "Clave de Licencia Faltante"
                Vuforia.INIT_LICENSE_ERROR_NO_CHECK -> "Error de verificación de licencia"
                else -> "Error desconocido ($result)"
            }
            Log.e("Vuforia", errorMsg)
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun setupDeviceTracking() {
        val trackerManager = TrackerManager.getInstance()

        // Inicializar el Ground Plane mediante PositionalDeviceTracker
        val positionalTracker = trackerManager.initTracker(PositionalDeviceTracker.getClassType())

        if (positionalTracker != null) {
            positionalTracker.start()
            isTrackingStarted = true
            Log.d("Vuforia", "✅ Ground Plane activado con PositionalDeviceTracker")

            // Ejecutar detección de plano en cada frame
            arSceneView.onFrame = { detectGroundPlane() }
        } else {
            Log.e("Vuforia", "❌ No se pudo iniciar el PositionalDeviceTracker.")
        }
    }

    private fun detectGroundPlane() {
        if (!isTrackingStarted || modelNode == null) return

        val state = TrackerManager.getInstance().state
        val results = state.trackableResults

        for (result in results) {
            val trackable = result.trackable
            if (trackable is Anchor) {
                val pose = trackable.pose
                val matrix = convertVuforiaPoseToSceneviewMatrix(pose)

                if (modelAnchorPosition == null) {
                    modelAnchorPosition = Position(matrix[12], matrix[13], matrix[14])

                    modelNode?.let { node ->
                        node.position = Position(
                            modelAnchorPosition!!.x,
                            modelAnchorPosition!!.y,
                            modelAnchorPosition!!.z
                        )
                        if (node.parent == null) {
                            arSceneView.addChildNode(node)
                            Toast.makeText(this, "Plano detectado. Modelo colocado sobre el piso.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun loadGlbModelAndStartRotation() {
        val modeloGlb = glbModelName ?: return
        val uriModelo = Uri.parse(modeloGlb)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val modelInstance = modelLoader.loadModelInstance(uriModelo.toString())
                modelInstance?.let { instance ->
                    modelNode = ModelNode(
                        modelInstance = instance,
                        scaleToUnits = 0.3f,
                        centerOrigin = Position(0.0f, 0.0f, 0.0f)
                    ).apply {
                        rotation = Rotation(0.0f, 180.0f, 0.0f)
                    }

                    iniciarRotacionLenta()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@ArActivity, "Error al cargar modelo 3D.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun iniciarRotacionLenta() {
        CoroutineScope(Dispatchers.Default).launch {
            var angulo = 0f
            while (isActive) {
                modelNode?.let {
                    angulo += 0.5f
                    if (angulo >= 360f) angulo = 0f
                    it.rotation = Rotation(it.rotation.x, angulo, it.rotation.z)
                }
                delay(30)
            }
        }
    }

    private fun convertVuforiaPoseToSceneviewMatrix(pose: Matrix44F): FloatArray {
        return pose.data.clone()
    }

    // Ciclo de vida
    override fun onResume() {
        super.onResume()
        if (isVuforiaInitialized) {
            Vuforia.onResume()
            Vuforia.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (isVuforiaInitialized) {
            Vuforia.onPause()
            Vuforia.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isVuforiaInitialized) {
            Vuforia.deinit()
        }
        arSceneView.destroy()
    }
}