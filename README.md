# Flight Line of the Future
## Project Summary
This project, by NSIN X-Force interns Bernard Chan and Sonali Loomba, is centered around streamlining aircraft maintenance for Travis Air Force Base. Presently, the first steps of the maintenance process begin with identifying the malfunctioning part on the flight line, and subsequently putting in an order for that part. The current method of ordering a part uses a multitude of platforms and requires inquiry requests to different departments before a formal supply order can be submitted. This process can be unwieldy and there is room for improvement to make it more efficient. Our goal is to automate this complicated process to make it faster and easier for maintenance professionals to order new parts. We are using Java to build the desktop application with JavaFX as the GUI framework. We also used the PDFBox and JSoup Java libraries to parse and scrape information about the parts. 

<img src="https://www.stripes.com/polopoly_fs/1.567693.1549556653!/image/image.jpg_gen/derivatives/landscape_900/image.jpg"
     alt="Maintenence Operator" width="1000" />
     
## Installation
1. Download [Javafx SDK and jmods from Gluon HQ]( https://gluonhq.com/products/javafx/) and put them into one folder on your computer
2. Clone the repository from GitHub
3. In Run Configurations, enter “--module-path *YOUR PATH TO: /javafx-sdk-11.0.2/lib* --add-modules javafx.controls,javafx.fxml”
4. Download the PDFBox, JSoup, JavaFX, and JUnit libraries or add the .jar files from FlightLineOFTheFuture/lib to the Project Structure
5. Enter a page number of the Technical Order to order from that page (limited to 3 and 4 based on the small selection of declassified Technical Order pages provided)

## Project Videos
### Problem Statement 
[![Problem Statement](http://img.youtube.com/vi/L2CiMDEHdH8/0.jpg)](https://youtu.be/L2CiMDEHdH8 "Problem Statement")
### Methodology
[![Methodology](http://img.youtube.com/vi/TsZh8A7L9Do/0.jpg)](https://youtu.be/TsZh8A7L9Do "Methodology")
### Program Demonstration 
[![Problem Demonstration](http://img.youtube.com/vi/TsZh8A7L9Do/0.jpg)](https://youtu.be/TsZh8A7L9Do "Problem Demonstration")

## Acknowledgements
Thank you to the National Security Innovation Network for providing us this opportunity and Kaitie Penry, our NSIN Point of Contact, for guiding us through the entire process. We'd also like to thank the U.S. Department of Defense for supporting programs like X-Force that allow university students to get involved in National Security. Thank you to Travis Air Force Base and Pheonix Spark Accelerator for providing us with an intriguing problem and the resources to solve it. Finally, thank you to TSgt. Evan Strahan and SSgt. Max Estrada for working with us every step of the way through brainstorming, prototyping, and ultimately developing an effective end product designed specifically for airmen.

<img src="https://www.nsin.us/assets/img/app/social-media/fb-og-tag-image.png"
     alt="NSIN Logo" height = "200"/>
<img src="https://www.nsin.us/assets/img/content/events/logo-dod.jpg"
     alt="US DOD Logo" height="200"/>
<img src="https://scontent-atl3-2.xx.fbcdn.net/v/t1.0-9/49755997_2099479013465396_3913156020624424960_o.jpg?_nc_cat=103&_nc_sid=09cbfe&_nc_ohc=MpcJObXMEf4AX87Ktfa&_nc_oc=AQnzxTeQqEqNtUJecRbrKL47IFMjelOYmknJoMCDAVRef0wsIiVOHAi9djnhBFVxKfg&_nc_ht=scontent-atl3-2.xx&oh=87d77c7672e40aea7a563b799af92638&oe=5F58121F"
     alt="Travis AFB Logo" height="195"/>
     
