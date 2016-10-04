package flyWalkCV;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.imgcodecs.*;

public class FrameAccessHelper {

	public FrameAccessHelper(){
	}

	/**this method is taken from StackOVerflow users 'howdoidothis' and 
	 * 	'rafaroc' @http://stackoverflow.com/questions/26515981/display-image-using-mat-in-opencv-java
	 * @param m
	 * @return the same image represented by the matrix but as a Buffered Image
	 */

	public BufferedImage Mat2BufferedImage(Mat m) {
		// Fastest code
		// output can be assigned either to a BufferedImage or to an Image

		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( m.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels()*m.cols()*m.rows();
		byte [] b = new byte[bufferSize];
		m.get(0,0,b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);  
		return image;
	}
	/**
	 * delete this guy soon. This was inb4 GUI
	 * @param img2
	 */
	public void displayImage(Image img2) {

		//BufferedImage img=ImageIO.read(new File("/HelloOpenCV/lena.png"));
		ImageIcon icon=new ImageIcon(img2);
		JFrame frame=new JFrame();
		frame.setLayout(new FlowLayout());        
		frame.setSize(img2.getWidth(null)+50, img2.getHeight(null)+50);     
		JLabel lbl=new JLabel();
		lbl.setIcon(icon);
		frame.add(lbl);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public BufferedImage returnNthImage(int frameIndex, VideoCapture vid){
		if (frameIndex < vid.get(Videoio.CAP_PROP_FRAME_COUNT)){
			vid.set(Videoio.CAP_PROP_POS_FRAMES, (double) frameIndex);
			Mat mat = new Mat(); 
			vid.read(mat);
			return Mat2BufferedImage(mat);
		}
		else {
			System.out.println("Frame Does Not Exist");
			return null;
		}
	}
	public Mat returnNthMat(int frameIndex, VideoCapture vid){
		if (frameIndex < vid.get(Videoio.CAP_PROP_FRAME_COUNT)){
			vid.set(Videoio.CAP_PROP_POS_FRAMES, (double) frameIndex);
			Mat mat = new Mat(); 
			vid.read(mat);
			return mat;
		}
		else {
			System.out.println("Frame Does Not Exist");
			return null;
		}
	}
}
