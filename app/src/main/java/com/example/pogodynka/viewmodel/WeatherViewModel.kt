package com.example.pogodynka.viewmodel
import android.app.Application
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pogodynka.model.Weather
import androidx.lifecycle.*
import com.example.pogodynka.model.RetrofitInstance.api
import com.example.pogodynka.model.WeatherRepository
import com.example.pogodynka.model.WeatherRequest

class WeatherViewModel(application: Application):AndroidViewModel(application) {
    private  val weatherRepository = WeatherRepository()
    private val _weather: MutableLiveData<WeatherRequest> = MutableLiveData()
    val weather:LiveData<WeatherRequest>
    get() = _weather

    private val _cityName: MutableLiveData<String> = MutableLiveData()
    val cityName:LiveData<String>
        get() = _cityName

    private val _temperature: MutableLiveData<Double> = MutableLiveData()
    val temperature:LiveData<Double>
        get() = _temperature

    private val _pressure: MutableLiveData<Double> = MutableLiveData()
    val pressure:LiveData<Double>
        get() = _pressure

    private val _description: MutableLiveData<String> = MutableLiveData()
    val description: LiveData<String>
        get() = _description

    private val _icon: MutableLiveData<String> = MutableLiveData()
    val icon:LiveData<String>
        get() = _icon

    private val _sunrise: MutableLiveData<String> = MutableLiveData()
    val sunrise:LiveData<String>
        get() = _sunrise

    private val _sunset: MutableLiveData<String> = MutableLiveData()
    val sunset: LiveData<String>
        get() = _sunset
    fun cityInfo(city: String){
        viewModelScope.launch{
            _weather.value = weatherRepository.getWeather(city)

        }
    }

}