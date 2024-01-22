# OpenWeather
Open weather API simulatoin

APP ID: 12aa83daf9dd5483fcc567739a456299
Location: Constants.kt

Limitations:
Data conversion (*temp and date/time), not entirely sure if server data issue or internal conversion.

Back log:
1. Error dialog not working as expected -> Done (fixed error trapping and replace with toast for the mean time)
2. Data fetch from server, recursive when entering current weather page -> Partially fixed (when launching page as a new instance, data is being fetched)
3. Unit test not started -> Started (Room db is done)

Done:
Error trappings are not complete, due to lack in time and preparation.
