import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PoliticalDonors {
	
	public static void clearFiles(String file) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();
		return;
	}
	public static void writeToFile(String fileName, String line) {
		try
		{
		    FileWriter fw = new FileWriter(fileName,true); 
		    fw.write(line);
		    fw.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		clearFiles(args[1]);
		clearFiles(args[2]);
		
		Map<String, DonorVal> zipIdMap = new HashMap<>();
		Map<String, DonorVal> dateIdMap = new TreeMap<>();
		
		String fName = args[0];
		
		File fin = new File(fName);
		FileInputStream fis = new FileInputStream(fin);
		 
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		String line = null;
		while ((line = br.readLine()) != null) {
		
			String[] tupleArr = line.split("\\|");
			if (tupleArr.length < 16) {
				continue;
			}
			boolean isZipValid = true;
			boolean isDateValid = true;
			if (tupleArr[0].length() < 1 || tupleArr[14].length() < 1 || tupleArr[15].length() > 0) {
				continue;
			}
			if (tupleArr[10].length() > 5)
				tupleArr[10] = tupleArr[10].substring(0, 5);
			
			if (tupleArr[10].length() < 5 || !tupleArr[10].matches("[0-9]+")) {
				isZipValid = false;
			}
			
			if (tupleArr[13].trim().length() != 8 || !tupleArr[13].matches("[0-9]+")) {
				isDateValid = false;
			}
			
			
			
			String key1 = tupleArr[0]+"|"+tupleArr[10];
			String key2 = tupleArr[0]+"|"+tupleArr[13];
			double amount = Double.parseDouble(tupleArr[14]);
			
			if (isZipValid) {
				if (zipIdMap.containsKey(key1)) {
					DonorVal prev = zipIdMap.get(key1);
					prev.setCount();
					prev.addNumberToStream(amount);
					prev.setSum(amount);
					zipIdMap.put(key1, prev);
				}
				else {
					DonorVal newPair = new DonorVal(1, amount, amount);
					newPair.addNumberToStream(amount);
					zipIdMap.put(key1, newPair);
				}
				
				sb1.append(key1+"|"+zipIdMap.get(key1).getMedian()+"|"+zipIdMap.get(key1).getCount()+"|"+zipIdMap.get(key1).getSum()+"\n");
				
			}
			
			if (isDateValid) {
				if (dateIdMap.containsKey(key2)) {
					DonorVal prev = dateIdMap.get(key2);
					prev.setCount();
					prev.setSum(amount);
					prev.addNumberToStream(amount);
					dateIdMap.put(key2, prev);
				}
				else {
					DonorVal newPair = new DonorVal(1, amount, amount);
					newPair.addNumberToStream(amount);
					dateIdMap.put(key2, newPair);
				}

			}
			
		}
		br.close();
		
		for (String key : dateIdMap.keySet()) {
			sb2.append(key+"|"+dateIdMap.get(key).getMedian()+"|"+dateIdMap.get(key).getCount()+"|"+dateIdMap.get(key).getSum()+"\n");
		}
		writeToFile(args[1], sb1.toString());
		writeToFile(args[2], sb2.toString());
	}

}
