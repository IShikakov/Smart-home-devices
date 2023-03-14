## Application description

A small smart home application that can be used to control devices such as lights,
roller shutters or heaters.

## General information

All information about available devices comes from the server
by http://storage42.com/modulotest/data.json.

The main screen consists from two tabs:

* A list of currently available devices, which can be filtered by product type;
* A user profile page where the user can update all information about themselves.

Currently, the device list can contain three types of products,
for each of which the user can open the control screen:

* Lights: The mode can be ON/OFF and the intensity can be changed from 0 to 100;
* Roller shutters: The position can be changed with the vertical slider from 0 to 100;
* Heaters: The mode can be ON/OFF and the temperature can be changed in 0.5 degree increments from
  7° to 28°.

The user can remove any device from the list of devices in the application tab by swiping to the
left.

The configuration of each device (current mode, intensity, etc.) is stored locally and survives the
killing of the application.