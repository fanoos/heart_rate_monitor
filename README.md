<img src="logo.png" >


# What is the Problem?
Low blood sugar levels, known as hypoglycemia, in people with diabetes may cause potentially dangerous changes in heart rate. Someone with type 1 diabetes is found dead in the morning in an undisturbed bed after having been observed in apparently good health the day before.

# Doc Hero Solution
<h3><b>Part 1: Heart Rate Monitoring by Polar Sensor - BLE </b></h3>
A wearable Heart Rate Device measuring the beat-to-beat variation in heart rate is a promising device for the early detection of hypoglycemia, or low blood sugar, in type 1 diabetes. System sends immediate alerts to your smart device or receiver when your heartrate is trending too high or too low.

<h3><b>Part 2: Heart Rate Monitoring by STM32F401 board and pulse Sensor  </b></h3>
The STM32F401 Nucleo board detects the heartbeats through the pulse sensor.
Do real-time analytics on the continuous streams of data coming from sensor by Apache Edgent. Such as Aggregation, Categorize data  in order to Reduce the amount of data transmitted to  analytics servers and Reduce the amount of data to be stored .
Data Pre-processed with Apache Edgent and then submitted to the Machine Learning algorithm.
ML algorithm does Classification of Heart Disease Using K- Nearest Neighbor and detect Heart Disease  such as Atrial fibrillation(Atrial fibrillation (AF or A-fib) is an abnormal heart rhythm characterized by rapid and irregular beating )
Finally all Data sent to Firebase Realtime Database by Firebase Admin in Java Application.


# Main functionalities
<table style="width:100%">
  <tr>
    <th>Part 1 (Polar Sensor)</th>
    <th>Part 2 (STM32F401 board and pulse Sensor) </th> 
   
  </tr>
  <tr>
    <td>
<b>Heart Rate Device</b>:
<ul>
<li>Scan Bluetooth Low Energy Devices</li>
<li>Connect to Device</li>
</ul>

<b>Monitoring Heart Rate</b>:
<ul>
<li>Continuously monitoring heart rate</li>
<li>Check heart rate value and sends immediate alerts (Alarm,Call,SMS)</li>
</ul>

<b>Collect Data</b>:
<ul>
<li>Store Data in the Cloud (Firebase Realtime Database)</li>
<li>Make Heart Rate Report Base on Last Hour|Night|Week</li>
</ul>

<b>Settings</b>:
<ul>
<li>Child Profile</li>
<li>Patient Care Attendant Profile</li>
</ul></td>
    <td>
<b>Programming STM32 and Pulse Sensor</b>:
<ul>
<li>Use  Arduino software (IDE)</li>
<li>Add STM32 boards support to Arduino</li>
<li>Add PulseSensor.com Playground to Arduino</li>
</ul>
  <b>Collect data from Sensor</b>
  <ul>
    <li>Capture raw data in COM port (Serial port )- Use RXTX Library</li>
    <li>Create Apache Edgent Pulse Sensor class</li>
  </ul>
  <b>Machine learning</b>
  <ul>
    <li>Pre-Processing data through Apache Edgent</li>
    <li>Classification of Heart Disease Using K- Nearest Neighbor and detect Heart Disease</li>
  </ul>
  <b>Apache Edgent Application</b>
  <ul>
    <li>Categorize data based on BPM number</li>
    <li>Aggregation the Heart Rate data</li>
  </ul>
  <b>Send data to cloud</b>
  <ul>
    <li>Use Firebase Admin to send data to Firebase Realtime database</li>
  </ul>
</td> 
   
  </tr>
 
</table>



# Architecture and Technology
<table>
<tr>
<td><h5>Android</h5>
<ul>
<li>Minimum API 18</li>
<li>Target API 25</li>
<li>Compiled with API 25</li>
</ul></td>
<td><img src="https://4.bp.blogspot.com/-brgnjo5GUa0/WLhXuAwnQII/AAAAAAAAD88/oxL3WK0wiU8zRVDAKyt1sUo37VZLo3BrQCLcB/s1600/Android%2BLogo.png" width="200"/>
</td>
<td>
<h5>Firebase</h5>
<ul>
<li>Firebase Realtime Database (Data is stored as JSON and synchronized in realtime to every connected client) </li>
<li>Firebase Authentication (Knowing a user's identity allows an app to securely save user data in the cloud and provide the same personalized experience across all of the user's devices)</li>
<li>Cloud Storage</li>
</ul>
<td><img src="https://www.joshmorony.com/wp-content/uploads/2016/11/firebase.png" width="300"/></td>
</tr>
<tr>
<td><h5>Scichart</h5>
<ul>
<li>
Realtime Graphing Solution</li>
<li>LineChart</li>
</ul></td>
<td><img src="https://raw.github.com/PhilJay/MPChart/master/design/other/bottom.png" width="400" height="50"/>
</td>
<td>
<h5>BLE</h5>
<ul>
<li>Bluetooth LE Technology</li>
<li>(Bluetooth LE, BLE, marketed as Bluetooth Smart)</li>
</ul>
<td><img src="https://cdn.mist.com/wp-content/uploads/ble.png" width="300"/></td>
</tr>
</table>
<table>
<tr>
<td><h5>Apache Edgent</h5>
<ul>
<li>Version 1.2.0-incubating</li>
<li>supported on Java 8 SE</li>
</ul></td>
<td><img src="https://edgent.apache.org/img/apache_logo.png" width="400"/>
</td>
<td>
<h5>Arduino IDE</h5>
<ul>
<li>The open-source Arduino Software (IDE) makes it easy to write code and upload it to the board</li>
 
</ul>
<td><img src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/Arduino2-IDE-LOGO.jpg" width="400"/></td>
</tr>
<tr>
<td><h5>RXTX</h5>
<ul>
<li>x64 Binaries</li>
<li>Version RXTX-2-2-20081207</li>
</ul></td>
<td><img src="http://rxtx.qbang.org/wiki/skins/common/images/wikii.png" width="400" height="50"/>
</td>
<td>
<h5>Eclipse IDE</h5>
<ul>
<li>Version Neon</li>
</ul>
<td><img src="http://www.eclipse.org/artwork/images/v2/logo-800x188.png" width="400"/></td>
</tr>
</table>
<img src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/Architecture.png">
<table>
  <tr>
    <td>
      <img src="https://firebase.google.com/docs/auth/images/auth-providers.png" width="600">
    </td>
    <td>
      <img src="https://cloud.google.com/solutions/mobile/images/overview-firebase-appengine-standard.png">
    </td>
  </tr>
</table>


# Hardware

<img src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/polar_h7.png">
<img src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/hardware.PNG">
<img src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/real-environment.jpg">

# Screenshots
<h3>Part 1 (Polar Sensor)</h3>

<div style="width:100vw">

<img width="45%" src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/scr-1.png" alt="Signin" border="1">
<img width="45%" src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/scr-2.png" alt="ParentDashboard" border="1">

<img width="45%" src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/scr-3.png" alt="MonitoringDashboard" border="1">
<img width="45%" src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/scr-4.png" alt="ScanDevice" border="1">

<img width="45%" src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/scr-5.png" alt="MonitoringHeartRate" border="1">
<img width="45%" src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/scr-6.png" alt="Alarm" border="1">

<img width="45%" src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/scr-7.png" alt="SMS" border="1">
<img width="45%" src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/scr-8.png" alt="ChartLine" border="1">

<h3>Part 2 (STM32F401 board and pulse Sensor)</h3>

<img src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/Run_Fibrillation_1.png">
<img src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/Run_Fibrillation_2.png">
<img src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/Consol_State.PNG">

# Video Demo
<h3>Part 1 (Polar Sensor)</h3>
<ul>
  <li>
    Initial App and Use Heart Rate Monitoring [https://youtu.be/F6RQ29Ilp54]
  </li>
  <li>
    Run Alarm of High Heart Rate [https://youtu.be/EvwKW1WH4sQ]
  </li>
  </ul>


<h3>Part 2 (STM32F401 board and pulse Sensor)</h3>
  <ul>
  <li>
    Sample of Use project [https://youtu.be/z4-mK-KDD-k]
  </li>
  <li>
    Machine Learning [https://youtu.be/g2mwLiRT55o]
  </li>
  <li>
    Categorize the Heart Rate State [https://youtu.be/bIEF7td8qyM]
  </li>
  <li>
    Aggregation the Heart Rate data [https://youtu.be/WkpGs2RWD8I]
  </li>
  <li>
    Programming STM32 and Pulse Sensor [https://youtu.be/l_7tFTZ9QKg]
  </li> 
  </ul>

# Roadmap

<h3>Part 1 (Polar Sensor)</h3>
<img src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/RoadMap.JPG">
<h3>Part 2 (STM32F401 board and pulse Sensor)</h3>
<img src="https://github.com/fanoos/heart_rate_monitor/blob/master/Demo/Roadmap_part2.png">

# Team Members
The team members are: Mostafa Ramezani, Soma Shekarchi.

Links to our LinkedIn accounts:
- Mostafa [LinkedIn: https://www.linkedin.com/in/mostafaramezani/]
- Soma [LinkedIn: https://www.linkedin.com/in/somashekarchi/]

# Slideshow links
- First overview [https://github.com/fanoos/heart_rate_monitor/blob/master/Diabetes-heart-rate.pdf]
- Feedback overview [https://github.com/fanoos/heart_rate_monitor/blob/master/Feedback-Heartrate%20Monitoring%20in%20Diabetic%20Children.pdf]
- Final Presentation [https://github.com/fanoos/heart_rate_monitor/blob/master/DocHero-HeartRate-Monitor.pdf]

- Heart Rate Monitoring by STM32F401 board and pulse Sensor [https://www.slideshare.net/MostafaRamezani1/machine-learning-and-apache-edgent-with-stm32f401-to-firebase]

# Source code
- Andriod App [https://github.com/fanoos/heart_rate_monitoring]
