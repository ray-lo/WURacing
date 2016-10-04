package flyWalkCV;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class TimeStampFileReader {
	public long[] readTimeStampFile(String filePath) throws IOException{
		FileInputStream fis = new FileInputStream(filePath);
		ObjectInputStream ois = new ObjectInputStream(fis);

		ArrayList<Long> holder = new ArrayList<Long>();
		File file = new File(filePath);

	
		System.out.println("File Length" + file.length());
		while (ois.available()>0){
			holder.add(ois.readLong());
		}
		long[] result = new long[holder.size()]; 
		for (int i = 0; i < result.length; i++){
			result[i] = holder.get(i);
		}
		System.out.println("HolderSize"+ holder.size());
		return result;
	}
	public TimeStampFileReader(){

	}
	public static void main(String[] args) throws IOException{
		TimeStampFileReader tsReader = new TimeStampFileReader();
		long[] result = tsReader.readTimeStampFile("/home/ray/WebcamTest/sadLife2.ts");
		for(int i = 0; i < result.length; i++){
			System.out.println(result[i]);
		}
	}
}
