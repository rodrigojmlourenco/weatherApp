# weatherApp
Android application that enables users to check the weather conditions in their location

## About the app
As a user, when I open the app I should see the weather for my current location, so I can immediately see weather that is relevant to me.

 * Screen shows current conditions (sunny, foggy, raining, etc.), temperature, wind speed and direction.
 * The weather information should be cached for future offline use.
 * If I am offline, and there is weather information cached that is less than 24 hours old:
    * The last known conditions and location should be shown along with a prominent display to indicate when the data was last updated.
    * A button should be displayed to allow a user to refresh the data.
 * If I am offline, and there are no previous conditions known, or the previous conditions are more than 24 hours old:
    * A screen should be displayed to indicate there is no previous data available.
    * A button should be displayed to allow a user to refresh the data.
* If I refresh the data manually, and I am offline, a message should be displayed to indicate that I need to connect to the Internet in order to get updated data. The app should display a loading indicator if it is fetching data.

The app uses OpenWeatherMap.org.

## Configure to Test
For security reasons the Marvel Developers API requires a public-private key pair. This pair must be used to create an hash that must be sent in each request, as the platform imposes a limit on the
number of requests per-day. For the same reasons, the project is defined in such a way that it does not contain this keys. To enable the compilation of the project please add an additional string
resources file with the following information:

```xml
<?xml version="1.0" encoding="utf-8"?>
  <resources>
    <string name="weather_api">API KEY</string>
</resources>
```

## Installing to Test

To install the pre-compiled apk, in the project folder please execute the following command:
`adb install release/app-release.apk`

## Next Steps
 - [ ] Ensure toggle between orientation modes does not lead to increased requests
 - [ ] Implement offline-first to improve reactiveness
 - [ ] Improve the layout for landscape
 - [ ] UI testing
 - [ ] Enable minify and adjust the proguard files