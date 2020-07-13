TCP Android QNSZT

We created an Android app that contains a TCP Server for sending out messages to arduino gadgets that gather data. 
It can log the connected users, recieve messages and generally handle many users at once.
Data that is sent out from the server is: frequency, how long the data should be gathered for, whether or not the gadget have it's LED indicator turned on.
The start button sends out all the necessary data for data-gathering.

Incoming messages are displayed in the app with timestamps without unsightly tags that are systematically removed on recieved message.
The app also tracks the currently connected devices, and displays the amount of them. 