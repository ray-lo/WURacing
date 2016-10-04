package flyWalkCV;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
//import org.opencv.
/**
 * this is inb4 GUI. Will delete soon
 * @author ray
 *
 */

public class Tracker {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat m  = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("m = ");
        System.out.println(m.dump());
        String path = "/Users/ray/PCTestVid.mp4";
        VideoCapture cap = new VideoCapture(path);
        int width = (int)cap.get(Videoio.CV_CAP_PROP_FRAME_WIDTH);
        int height = (int)cap.get(Videoio.CV_CAP_PROP_FRAME_HEIGHT);
        int frameCount = (int)cap.get(Videoio.CAP_PROP_FRAME_COUNT);
      //  int frameRate = (int)cap.get(Videoio.CV_CAP_PROP_FRAME_);

    //    int colorDepth = (int)cap.get(Videoio.);
       Mat mat = new Mat();
      
       cap.read(mat);
       FrameAccessHelper imgDis = new FrameAccessHelper();
      //  System.out.println("mat = " + mat.dump());
     //  System.out.println("Frame Rate" + frameRate);
       System.out.println("Frame Count" + frameCount);
       System.out.println(width);
       System.out.println(height);
       imgDis.displayImage(imgDis.returnNthImage(2000, cap));
       System.out.println(mat.size());
        
      //  OpenCVFrameGrabber grabber = new OpenCVFrameGrabber("demo.avi");
         
    }  
}