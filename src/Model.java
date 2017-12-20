import java.lang.*;
import java.io.*;

public class Model {
	
	private boolean isOnline;

	public Model(){
		isOnline = true;
	}

	public void stop() throws IOException{
		Process p = Runtime.getRuntime().exec("ipconfig /release");
	}

	public void start() throws IOException{
		Process p = Runtime.getRuntime().exec("ipconfig /renew");
	}

	public void setIsOnline(boolean status){
		isOnline = status;
	}
	public boolean getIsOnline(){
		return isOnline;
	}

}
