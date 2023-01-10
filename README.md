# TCP Android Client

We created an Android app that contains a TCP Server for sending out messages to arduino gadgets that gather data. 
It can log the connected users, recieve messages and generally handles many users at once.

Data that is sent out from the server is: How long the data should be gathered for, at what speed should the sensors log data, what to name the log output and whether or not the gadget should have it's LED indicator turned on.

The start button sends out all the necessary messages to start the data-gathering.

Incoming messages are displayed in the app with timestamps without unsightly tags that are systematically removed on recieved message.
The app also tracks the currently connected devices, and displays the amount of them. 
