package flyWalkCV;

import java.io.File;

import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class VideoSorter {
	
	private ArrayList<VideoCaptureWithName> vidCapWithNameArrayList;
	
	public ArrayList<VideoCaptureWithName> getVidCapWithNameArrayList() {
		return vidCapWithNameArrayList;
	}
	public VideoSorter(){
		vidCapWithNameArrayList = new ArrayList<VideoCaptureWithName>();
	}
	public void addVideoCaptureWithName(VideoCaptureWithName vwn){
		vidCapWithNameArrayList.add(vwn);
	}
	public void populateListFromFolder(String folderPath){
		vidCapWithNameArrayList = new ArrayList<VideoCaptureWithName>();
		File directory = new File(folderPath);
		for(File file: directory.listFiles()){
			String fileExtension = FilenameUtils.getExtension(file.getName());
			if (fileExtension.equals("avi") 
					|| fileExtension.equals("mp4")
					){
				String path = file.getAbsolutePath();
				System.out.println(path);
				VideoCapture vid = new VideoCapture(path);
				
				VideoCaptureWithName vwn = new VideoCaptureWithName(vid, file.getName());
				this.addVideoCaptureWithName(vwn);
				
			}
		}
	}
}
