# ParkN - Your Peer-to-Peer Parking Solution 

![Main Page]https://github.com/kjin11/ParkN/blob/master/iot_demo/app/src/main/res/drawable/1A7390B2-CE17-4AFC-B8CA-BAA26028B01B.png

## MVC Frameworks:

Front-end include a left-sliding Navigation View and several Activity pages associated with main functionality for the application. The home page shows Google map indicating nearby available parking lot and provides the bottom sheet for searching function. After clicking the marker on the map, the user could come to the detail page and view detail information for a corresponding parking lot.Renting page shows the parking lot user is renting currently. The lending page shows the parking lot user is lending currently and provides the button to add newly available parking lot for sharing.

Back-end focus on the main functions associated with the application, including adding a newly available parking lot, renting available parking lot, showing currently available parking lot on Google map and complete payment associated with Google pay.

The application adopts Firebase as authentication and real-time database, including using Firebase Authentication to implement user sign in, sign up and log out and using Firebase real-time database to store user information and parking lot information.


## Transaction Mechanisms:

The CheckOut Activity along with Constants and PaymentsUtil are leveraging the Google Pay API (https://developers.google.com/pay/api/overview) for facilitating fast and easy online purchases for the users and eliminates manual entry of payment and shipping information. 
The Google Pay API can be used to request any credit or debit card stored in customer’s Google account, including their tokenized credentials from the Google Pay app. After clicking the Google pay button in the detail renting page, the holder will be placed through the user’s Google account and completed a notification.

As for the Firebase data entry, the completed transaction will be deleted to avoid the duplicated order, and all the transaction record will be reported through token provider as well as Google service.


## Location Services:

Map Fragment is integrated into the main activity. Since our APP is location-based, we use Google Map API(https://developers.google.com/maps/documentation/android-sdk/intro) and Google Place API(https://developers.google.com/places/?hl=zh-cn). Google Map API provides markers. ParkN shows available parking information, and the target place that the user wants to park on the map. Based on Google Place API, we can locate users’ current locations and facilities around their position. Besides, Google Place API enables ParkN to search and explore user’s target location, and provide user abundant information, and show this information on the Google Map. ParkN also implements the place-autocomplete adapter. After the user enters partial query information, it predicts and provides suggestions based on the target location (similar to searching on Google Maps). 

When first using ParkN, it requests the user for access permission and checks if the device supports Google Service. Only with user’s permission and device supported google service version, can the user uses ParkN. Map Fragment first locates user’s current location, and add a red marker on the map, zoom into that location, and show this marker on the map. ParkN reads data (available parking lots’ information) from Firebase and displays data on the map in real-time. The available parking places are shown as blue markers. By clicking the marker, it jumps to the detail page of this available parking place, and then the user can view in detail about this parking lot and decide whether to reserve it. By searching on the text bar, the user can search his/her target parking location, and the target place is shown in green marker, and the map is also zoomed onto that target place.


## Requirements

In order to build and run this sample app, make sure you:
- Have Android Studio 3.0 or newer installed.
- Have a device running Android 4.4 (KitKat) or newer.
- Have Google Play services version 11.4.0 or newer installed on this device.

