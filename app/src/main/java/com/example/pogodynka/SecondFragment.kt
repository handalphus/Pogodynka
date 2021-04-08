package com.example.pogodynka

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pogodynka.model.WeatherRequest
import com.example.pogodynka.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*
import java.text.DecimalFormat
import java.time.ZoneOffset
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    lateinit var viewModel: WeatherViewModel
    lateinit var pictureLink:String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
       val weatherObserver = Observer<WeatherRequest> {
           newRequest->run{
           if (newRequest.cod==200){
               var nf = DecimalFormat("##.#")
               textViewTemperatureJuniorBig.text = (kotlin.math.round(newRequest.main.temp - 273.15).toInt()).toString() + "\u00B0C"
               textViewPressureJunior.text = newRequest.main.pressure.toString() + "hPa"
               textViewCityJunior.text = newRequest.name
               textViewDescriptionJunior.text = newRequest.weather[0].description
               textViewSunriseJunior.text = newRequest.sys.sunrise.toString()
               textViewSunsetJunior.text = newRequest.sys.sunset.toString()

               val cal = Calendar.getInstance()
               val timeZoneID = "GMT${if(newRequest.timezone>=0)"+" else "-"}" +
                       "${kotlin.math.abs(newRequest.timezone)/3600}:" +
                       "${kotlin.math.abs(newRequest.timezone)/60}"
               cal.timeZone = TimeZone.getTimeZone(timeZoneID)
               ZoneOffset.ofTotalSeconds(newRequest.timezone)
               val sunrise = Date(newRequest.sys.sunrise *1000 + newRequest.timezone*1000)
               cal.time = sunrise
               textViewSunriseJunior.text = cal.get(Calendar.HOUR_OF_DAY).toString().padStart(2,'0') + ":"+cal.get(Calendar.MINUTE).toString().padStart(2,'0')
               val sunset = Date(newRequest.sys.sunset *1000 + newRequest.timezone*1000)
               cal.time = sunset
               textViewSunsetJunior.text = cal.get(Calendar.HOUR_OF_DAY).toString().padStart(2,'0') + ":"+cal.get(Calendar.MINUTE).toString().padStart(2,'0')

               pictureLink = "https://openweathermap.org/img/wn/${newRequest.weather[0].icon}@2x.png"
               Picasso.get().load(pictureLink)
                   .placeholder(R.drawable.ic_launcher_background).into(imageViewWeatherIconJunior)
               textViewCloudinessJunior.text = newRequest.clouds.all.toString() + "%"
               textViewWindJunior.text = newRequest.wind.speed.toString() + "m/s"
               textViewHumidityJunior.text = newRequest.main.humidity.toString() + "%"
           }
           else{
               textViewCityJunior.text = "City not found"
               textViewTemperatureJuniorBig.text = ""
               textViewPressureJunior.text = ""

               textViewDescriptionJunior.text =""
               textViewSunriseJunior.text = ""
               textViewSunsetJunior.text = ""
               textViewCloudinessJunior.text = ""
               textViewWindJunior.text = ""
               textViewHumidityJunior.text = ""
               imageViewWeatherIconJunior.setImageDrawable(null)
           }

       }
       }
        viewModel.weather.observe(viewLifecycleOwner,weatherObserver)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonToSenior).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}