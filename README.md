# BR_Weather_App
The weather app is a great way to view both local and international city weather forecasts

## Problems with making app

While making the app I had trouble getting the radar images from the api.

# App Architecture
This app is composed of 1 activity and 4 fragments.

- HomeFragment
- CitySearchFragment
- RadarFragment
- WeatherPageFragment

The Homefragment is the container that houses the Viewpager2 which cycles the various cities.

The WeatherPageFragment displays the information of the various cities.

The CitySearchFragment searches for the various cities.

The RadarFragment shows the radar image of the selected city
