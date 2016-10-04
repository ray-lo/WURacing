package flyWalkCV;

import org.opencv.videoio.VideoCapture;

public class VideoCaptureWithName {
	private VideoCapture v;
	private String name;
	public VideoCaptureWithName(VideoCapture v, String name) {
		this.v = v;
		this.name = name;
	}
	public VideoCapture getVideoCapture() {
		return v;
	}
	public String getName() {
		return name;
	}	
}
