Personal-Health-Monitoring-System
=================================

###CSE 3310 Android project

### How to get a gradle project working for Intellij
#### Setup SDK and environment variables
* [Download the Android sdk](http://developer.android.com/sdk/index.html#download)
* Extract the sdk to some directory. Ex. C:\Program Files
* Then go into the directory you just extracted. Ex. C:\Program Files\adt-bundle-windows-x86_64-20131030
* Run SDK Manager. (Might need to right click and run as administrator)
* Download those packages:
      
![alt text](https://f.cloud.github.com/assets/4734933/2291232/d2bc42a2-a038-11e3-948d-29647267419f.PNG)


* Open up your environment variables

![alt text](https://f.cloud.github.com/assets/4734933/2291233/d2bca774-a038-11e3-8ce8-4f539e89bb2f.PNG)
![alt text](https://f.cloud.github.com/assets/4734933/2291234/d2be44c6-a038-11e3-8b2a-77fe8081bed6.PNG)
![alt text](https://f.cloud.github.com/assets/4734933/2291235/d2be6596-a038-11e3-9e37-443ab92d8aa4.PNG)

* Varable name: ANDROID_HOME

   Varable value: < Path to your sdk directory > Ex. C:\Program Files\adt-bundle-windows-x86_64-20131030\sdk

#### Import project into Intellij 
* Clone this repository with some github program or download the ZIP on the right side and extract it to some directory.
* Open Intellij.
* File > import Project
* Select the project directory

![alt text](https://f.cloud.github.com/assets/4734933/2291236/d2be6bd6-a038-11e3-9e95-c362f74363e0.PNG)

* Select Import project from external model and then select Gradle. Click Next

![alt text](https://f.cloud.github.com/assets/4734933/2291237/d2bf6dba-a038-11e3-8057-e628186d37f2.PNG)

* Click Finish.

![alt text](https://f.cloud.github.com/assets/4734933/2291238/d2c69e50-a038-11e3-9ca5-899eba6a3a9a.PNG)
