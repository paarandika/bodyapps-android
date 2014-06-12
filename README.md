bodyapps-android
================

Android app for the #bodyapps project

Building the project:
---------------------
1. Get Android SDK(ADT) from here :http://developer.android.com/sdk/index.html
2. Extract the files.
3. Open Eclipse in ADT and open SDK Manger from tool bar. Add Google play services and Android Support Library to SDK.  
4.	i.	Restart Eclipse and do : File> Import> Existing Android code to workspace> select Browse.  
	ii.	Go to you ADT location (ADT)>sdk>extras>google>google_play_services>libproject and select it.  
	iii.	Tick "add project to the workspace"(due to a bug in ADT this is a must)
	iv.	Click Finish.  
5. 	i.	Do 4.i.  
	ii.	Go to you ADT location (ADT)>sdk>extras>android>support>v7>appcompat and select it.  
	iii	 Do 4.iii and 4.iv.  
6.	i.	Do 4.i.  
	ii.	Select project clone.  
	iii. 	Do 4.iii and 4.iv  
7.	i. 	Right click on BodyApp project and go to properties.  
	ii.	Go to Android tab and remove the existing references in Library section.   
	iii. 	Click Add and add Google play services and appcompat libraries.  
	iv. 	Now there shouldn't be any error on BodyApp project. If any clean the project and restart Eclipse.  
8. Now you are good to run the app on a connected device.   
	But if you want to go with a emulator you cant use AVD because it doesn't support Google play services.  
		i. Download GennyMotion emulator from here : http://www.genymotion.com/ (it's free for personal use)  
		ii.Download a device to GennyMotion and install Google play services(You can follow this thread: http://stackoverflow.com/questions/20121883/how-to-install-google-play-service-in-the-genymotion-ubuntu-13-04-currently-i)  
		iii. Log in to device using a Google account.  
		iv. Run the app while virtual device is on. It will automatically choose the device.  

**Creating Google API project**  
To get information from Google API it is required to create project in Google developer console
1. Go to Google developer console(https://console.developers.google.com/project?authuser=0) and go to projects tab and click on create project.  
2. Give a project name and create a project.  
3. Then go in to the project and go to APIs under APIs & auth tab.  
4. Turn on Google+ API.  
5. Go to Credentials  under APIs & auth tab and click on Create new client ID.  
6. Click on Installed Application and select Android.  
5. Give package name as fossasia.valentina.bodyapp.main and give your SHA1 key.  
(You can get SHA1 from Eclipse by Windows> Preferences > Android > build )  
6 . Enable deep linking and click create client ID.
		
**NOTE: Web application Running in the localhost and listning to port 8020 is essential if you try sync options. Otherwise App is fully functional offline**  
And you can change the IP and URL according to your system by changing:  
URL in SyncUser class(line:24)  
URL in SyncMeasurement class(line: 19)  
parameter in SettingsActivity class (line:280)  
parameter in MeasurementActivity class (line: 166)