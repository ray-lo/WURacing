package flyWalkCV;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class MatTest {
	public static void main(String[] args){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat m = new Mat();
		System.out.println(m.dump());
	}
}
