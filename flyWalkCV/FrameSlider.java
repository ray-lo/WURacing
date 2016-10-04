package flyWalkCV;



import java.awt.Dimension;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FrameSlider extends JSlider {
	public FrameSlider(int frameCount){
		super(0, frameCount, 1);
		this.setMinorTickSpacing(1);
		//this.setPreferredSize(new Dimension(this.getWidth()+200, this.getHeight()));
		this.setVisible(true);
	}
}
