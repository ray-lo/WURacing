package flyWalkCV;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


public class SimpleImageDisplay extends JFrame {
	private JLabel l;
	private FrameAccessHelper helper;
	public SimpleImageDisplay(){
		l = new JLabel();
		helper = new FrameAccessHelper();
		this.add(l);
		this.setVisible(true);

		
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void updateImage(Mat m){
		if(this.getHeight() != m.height()){
			m= resizeMat(m);
		}
		l.setIcon(new ImageIcon(helper.Mat2BufferedImage(m)));
	}
	private Mat resizeMat(Mat m){
		Mat resizedImage = new Mat();
		double scale = (double)m.height()/(double)m.width();
		double heightOfFrame = this.getSize().getHeight();
		Size sz = new Size(heightOfFrame/scale, heightOfFrame);
		Imgproc.resize(m, resizedImage, sz);
		return resizedImage;
	}
	public void setFrameSizeToMatchMat(Mat m){
		this.setBounds(100, 100, m.width(), m.height());
	}
}
