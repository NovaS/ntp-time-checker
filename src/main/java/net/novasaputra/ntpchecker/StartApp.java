package net.novasaputra.ntpchecker;

import java.io.IOException;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.net.time.TimeTCPClient;

/**
 * @author NovaS
 */
public class StartApp extends TimerTask {
	private String hostName;
	private Timer timer;
	
	public StartApp(String host) {
		if(host==null || host.trim().isEmpty()) throw new NullPointerException("Invalid parameter for host address!");
		hostName = host;
		timer = new Timer();
	}
	
	public void start() {
		timer.scheduleAtFixedRate(this, 0, 1000);
	}
	
	@Override
	public void run() {
		try {
			TimeTCPClient ntpClient = new TimeTCPClient();
			ntpClient.connect(hostName);
			ntpClient.setDefaultTimeout(60000);
			System.out.println("Sync Time from "+hostName+" is "+ntpClient.getDate().toString());
			ntpClient.disconnect();
			ntpClient = null;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		if(args.length>0){
			StartApp app = new StartApp(args[0]);
			app.start();
		}else{
			throw new NullPointerException("Invalid parameter! Place NTP server address on the next argument!");
		}
	}
}
