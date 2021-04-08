package com.example.pogodynka

import android.annotation.SuppressLint

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pogodynka.model.WeatherRequest
import com.example.pogodynka.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*
import java.lang.Math.abs
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneOffset
import java.util.*
import java.util.TimeZone
import kotlin.math.round

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    lateinit var viewModel: WeatherViewModel
    lateinit var pictureLink:String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
           viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
            val weatherObserver = Observer<WeatherRequest>{

           newRequest->run{
                if (newRequest.cod==200){
                    var nf = DecimalFormat("##.#")
                    textViewTemperature.text =(round(newRequest.main.temp - 273.15).toInt()).toString() + "\u00B0C"
                    textViewPressure.text = newRequest.main.pressure.toString() + "hPa"
                    textViewCity.text = newRequest.name
                    textViewDescription.text = newRequest.weather[0].description
                    textViewSunrise.text = newRequest.sys.sunrise.toString()
                    textViewSunset.text = newRequest.sys.sunset.toString()

                    val cal = Calendar.getInstance()
                    val timeZoneID = "GMT${if(newRequest.timezone>=0)"+" else "-"}" +
                            "${kotlin.math.abs(newRequest.timezone)/3600}:" +
                            "${kotlin.math.abs(newRequest.timezone)/60}"
                    cal.timeZone = TimeZone.getTimeZone(timeZoneID)
                    ZoneOffset.ofTotalSeconds(newRequest.timezone)
                    val sunrise = Date(newRequest.sys.sunrise *1000 + newRequest.timezone*1000)
                    cal.time = sunrise
                    textViewSunrise.text = cal.get(Calendar.HOUR_OF_DAY).toString().padStart(2,'0') + ":"+cal.get(Calendar.MINUTE).toString().padStart(2,'0')
                    val sunset = Date(newRequest.sys.sunset *1000 + newRequest.timezone*1000)
                    cal.time = sunset
                    textViewSunset.text = cal.get(Calendar.HOUR_OF_DAY).toString().padStart(2,'0') + ":"+cal.get(Calendar.MINUTE).toString().padStart(2,'0')

                    pictureLink = "https://openweathermap.org/img/wn/${newRequest.weather[0].icon}@2x.png"
                    Picasso.get().load(pictureLink)
                            .placeholder(R.drawable.ic_launcher_background).into(imageViewWeatherIcon)

                }
                else{
                    textViewCity.text = "City not found"
                    textViewTemperature.text = ""
                    textViewPressure.text = ""

                    textViewDescription.text =""
                    textViewSunrise.text = ""
                    textViewSunset.text = ""
                    imageViewWeatherIcon.setImageDrawable(null)
                }

           }
        }

        viewModel.weather.observe(viewLifecycleOwner,weatherObserver)
        if (viewModel.weather.value==null ){
            viewModel.cityInfo("Rudy")
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        textview_first.text= viewModel.weather.value.toString()
        view.findViewById<Button>(R.id.buttonToJunior).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}