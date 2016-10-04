package flyWalkCV;
import java.awt.Color;
import java.util.Random;

//import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.features2d.FeatureDetector;
import org.opencv.core.KeyPoint;

/**
 * A demo for blob extraction using only JavaCV / OpenCV
 * @see http://stackoverflow.com/questions/4641817/blob-extraction-in-opencv
 * @see http://voices.yahoo.com/connected-components-using-opencv-5462975.html?cat=15
 * @see http://opencv.willowgarage.com/documentation/cpp/structural_analysis_and_shape_descriptors.html#cv-findcontours
 * @author happyburnout
 */

public class FlyDetector {
	private FeatureDetector fd;

	
	
	public FlyDetector() {
		this.fd =FeatureDetector.create(FeatureDetector.SIMPLEBLOB);
	}
	public MatOfKeyPoint returnCordinates(Mat m){
		MatOfKeyPoint matOfKeyPoint = new MatOfKeyPoint();
		fd.detect(m, matOfKeyPoint);
		return matOfKeyPoint;
	}
	public Point[] returnPointArray(Mat m){
		MatOfKeyPoint matOfKeyPoint = new MatOfKeyPoint();
		fd.detect(m, matOfKeyPoint);
		KeyPoint[] kpArray = matOfKeyPoint.toArray();
		Point[] pArray = new Point[kpArray.length];
		for(int i = 0; i < kpArray.length; i++){
			pArray[i] = kpArray[i].pt;
		}
		return pArray;
	}
}