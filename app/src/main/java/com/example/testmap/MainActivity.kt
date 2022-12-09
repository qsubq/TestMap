package com.example.testmap

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.testmap.databinding.ActivityMainBinding
import com.vmadalin.easypermissions.EasyPermissions
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationTask()
    }

    private fun locationTask() {
        if (EasyPermissions.hasPermissions(this, ACCESS_FINE_LOCATION)) {
            initMap()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app permission", 100, ACCESS_FINE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun initMap() {
        Configuration.getInstance().userAgentValue = packageName

        binding.map.setMultiTouchControls(true)
        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        val controller = binding.map.controller
        controller.setZoom(9.0)
        val startPoint = GeoPoint(48.13, -1.63)


        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.toFloat()) {
            controller.setCenter(GeoPoint(it))
        }

        val startMarker = Marker(binding.map)
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        binding.map.overlays.add(startMarker)
        startMarker.icon = AppCompatResources.getDrawable(this,R.drawable.ic_baseline_airport_shuttle_24)
        startMarker.title = "Start point"


    }

}