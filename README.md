## Tank.ly

This project demonstrates my competency in Android development. It incorporates modern Android features:
- Jetpack Compose for modern UI development.
- Dagger / Hilt for dependency injection.
- The Coil library for asynchronous image loading.
- Pull-to-refresh and LazyColumn.
- Written in Kotlin and uses all relevant Kotlin-based build tools.

![A screenshot of the main view, showing a list of tanks and their countries of origin.](/readme-img/tankly_tanklist_screenshot.png)

![A screenshot of the detailed view, showing the M1 Abrams from the USA.](/readme-img/tankly_tankdetails_screenshot.png)

![A screenshot of the detailed view, showing the Leopard 2 from Germany.](/readme-img/tankly_tankdetails_screenshot2.png)

The purpose of this app is to display data about various current and historical tanks served from a Flask server, which reads a SQLite database. All data is included in the Tank.ly API repository.

This project is designed to be paired with the Tank.ly API and requires it to run. Please see it here:
https://github.com/hayden-cole/tank.ly-api 

To run:

1. Clone this repository and the companion Tank.ly API.
2. Ensure the Tank.ly API is running and bound to this address: `0.0.0.0:5000`.
3. Ensure the Windows firewall does not block connections to or from the Tank.ly API IP address or the address of your Android emulator.
4. Configure your emulator. I used an otherwise unaltered **Pixel 9** with **Android 15.0** targeting **API 35**.

5. Build and start the Android Studio project.