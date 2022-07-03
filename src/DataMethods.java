import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class DataMethods {

	public static void main(String[] args) {
		addBlock(args[0]);
	}
	static String dataFile = "data.dat";
	public static class Data implements Serializable
	{
		ArrayList<String> blocklist;
		boolean on;
		public Data(ArrayList<String> blocklist, boolean on)
		{
			this.blocklist = blocklist;
			this.on = on;
		}

		public ArrayList<String> getBlocklist()
		{
			return blocklist;
		}

		public void setBlocklist(ArrayList<String> bl)
		{
			blocklist = bl;
		}



	}
	
	public static void writeData(Data data)
	{
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFile));
	        out.writeObject(data);
	        out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static Data getData()
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile));
	        Data data = (Data) in.readObject();
	        in.close();
	        return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("problem accessing data file");
			
			Data newData = new Data(new ArrayList<String>(), false);
			writeData(newData);
			return getData();
		}
	}
	public static String blockText (String redirectIP, String website)
	{
		return redirectIP + "\t" + website + "\n";
	}

	public static void addBlock ( String website)
	{
		Data data = DataMethods.getData();
		data.getBlocklist().add(website);
		writeData(data);
	}


}

