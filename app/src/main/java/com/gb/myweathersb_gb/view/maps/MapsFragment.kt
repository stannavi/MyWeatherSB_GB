package com.gb.myweathersb_gb.view.maps

import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb.myweathersb_gb.R
import com.gb.myweathersb_gb.databinding.FragmentMapsUiBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsFragment : Fragment() {

    lateinit var map: GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        googleMap.setOnMapLongClickListener { latLng ->
            addMarkerToArray(latLng)
            setMarker(latLng, "", R.drawable.ic_map_marker)
            drawLine()
        }

        googleMap.uiSettings.isZoomControlsEnabled = true
        //TOD HW найти подвох

        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true
    }

    private var _binding: FragmentMapsUiBinding? = null
    private val binding: FragmentMapsUiBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsUiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.buttonSearch.setOnClickListener {
            binding.searchAddress.text.toString().let {searchText ->
                val geocoder = Geocoder(requireContext())
                val result = geocoder.getFromLocationName(searchText, 1)
                // TODO HW а не пуст ли result

                val ln = LatLng(result.first().latitude, result.first().latitude)
                setMarker(ln, searchText, R.drawable.ic_map_marker)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(ln, 15f))


            }
        }
    }

    val markers = mutableListOf<Marker>()

    private fun addMarkerToArray(location: LatLng) {
        val marker = setMarker(location, markers.size.toString(),
            R.drawable.ic_map_pin)
        markers.add(marker)
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(previous, current)
                    .color(Color.RED)
                    .width(15f)
            )
        }
    }

    private fun setMarker(
        location: LatLng,
        searchText: String,
        resourceId: Int
    ): Marker {
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
                .icon(BitmapDescriptorFactory.fromResource(resourceId))
        )!!
    }
}