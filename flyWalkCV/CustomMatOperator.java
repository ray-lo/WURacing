package flyWalkCV;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CustomMatOperator {
	public CustomMatOperator(){
	}
	public Mat drawSquareAroundDetectedPoints(Mat m, Point[] detectedPointsArray, int size){
		for (int i = 0; i< detectedPointsArray.length; i++){
			Point p1 = new Point(detectedPointsArray[i].x- (size/2),detectedPointsArray[i].y- (size/2));
			Point p2 = new Point(detectedPointsArray[i].x+ (size/2), detectedPointsArray[i].y +(size/2));
	        Imgproc.rectangle(m, p1, p2, new Scalar(255, 0, 0, 255), 1); 
		}
		return m;
	}
}
