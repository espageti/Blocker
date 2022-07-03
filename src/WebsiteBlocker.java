import java.io.BufferedReader;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;



public class WebsiteBlocker {
	static String hostsPath = "C:\\Windows\\System32\\drivers\\etc\\hosts";
	static String startString = "#Blocker";
	static String endString = "#End Blocker";
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		turn(true);
		
	}
	
	
	public static void turn (boolean on)
	{
		
		File hostsFile = new File(hostsPath);
		if(!hostsFile.exists()) 
		{
			System.out.println("can't find the file");
			return;
		}
		hostsFile.setReadable(true);
		hostsFile.setWritable(true);
		BufferedReader br;
		
		String writeoverContent = "";
		try 
		{
			DataMethods.Data data = DataMethods.getData();
	        	
	        data.on = on;


			DataMethods.writeData(data);
			br = new BufferedReader(new FileReader(hostsPath));	
			String nextLine = br.readLine();
			while (nextLine != null)
			{
				writeoverContent += nextLine + "\n";
				
				
				nextLine = br.readLine();

				if (nextLine == null || nextLine.contentEquals(startString))
				{
					
					System.out.println("whateer " + nextLine);
					while(nextLine != null && nextLine != endString)
					{
						//do nothing, skip it

						System.out.println(nextLine);
						nextLine = br.readLine();
					}
					if(on)
					{
						
						
				        String blocksText = startString + "\n";
				        for (String block: data.blocklist)
				        {
				        	blocksText += DataMethods.blockText("127.0.0.1", block);
				        }
				        blocksText += endString;
						writeoverContent += blocksText;
					}
					
					
				}
				
			}
			//System.out.println(writeoverContent);
			
			
			
			FileWriter fw = new FileWriter(hostsPath);
			fw.write(writeoverContent);
			fw.close();
			

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	


}
