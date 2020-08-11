# Flight Line of the Future
## Project Summary
This project, by NSIN X-Force interns Bernard Chan and Sonali Loomba, is centered around streamlining aircraft maintenance for Travis Air Force Base. Presently, the first steps of the maintenance process begin with identifying the malfunctioning part on the flight line, and subsequently putting in an order for that part. The current method of ordering a part uses a multitude of platforms and requires inquiry requests to different departments before a formal supply order can be submitted. This process can be unwieldy and there is room for improvement to make it more efficient. Our goal is to automate this complicated process to make it faster and easier for maintenance professionals to order new parts. We are using Java to build the desktop application with JavaFX as the GUI framework. We also used the PDFBox and JSoup Java libraries to parse and scrape information about the parts. 

<img src="https://www.stripes.com/polopoly_fs/1.567693.1549556653!/image/image.jpg_gen/derivatives/landscape_900/image.jpg"
     alt="Maintenence Operator"
     style="float: left; margin-right: 10px;" />
     
## Installation
1. Download [Javafx SDK and jmods from Gluon HQ]( https://gluonhq.com/products/javafx/) and put them into one folder on your computer
2. Clone the repository from GitHub
3. In Run Configurations, enter “--module-path *YOUR PATH TO: /javafx-sdk-11.0.2/lib* --add-modules javafx.controls,javafx.fxml”
4. Download the PDFBox, JSoup, JavaFX, and JUnit libraries or add the .jar files from FlightLineOFTheFuture/lib to your Project Structure
5. Enter page number of Technical Order that you would like to parse as a program argument (limited to 3 and 4 based on the declassified Technical Order pages we provided)

## Project Videos
### Problem Statement 
[![Problem Statement](http://img.youtube.com/vi/L2CiMDEHdH8/0.jpg)](https://youtu.be/L2CiMDEHdH8 "Problem Statement")
### Methodology
[![Methodology](http://img.youtube.com/vi/L2CiMDEHdH8/0.jpg)](https://youtu.be/L2CiMDEHdH8 "Methodology")
### Program Demonstration 
[![Problem Demonstration](http://img.youtube.com/vi/L2CiMDEHdH8/0.jpg)](https://youtu.be/L2CiMDEHdH8 "Problem Demonstration")
