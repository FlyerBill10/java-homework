package EdiDataParse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * @author Bill Farrell
 * driver program to read input file, parse into record format and call output routine
 * Input file name is hard coded, could be changed ot accept input file name
 * Output files are written under separate timestamped directory to enable an easy re-run
 *
 */
public class ParseDriver {
	public static void main(String[] args) {

        String csvFile = "./input/ediInput1.csv";
        String line = "";
        String cvsSplitBy = ",";
        List <EdiRecord> ediList = new ArrayList<EdiRecord>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] rawFields = line.split(cvsSplitBy);
                ediList.add(parseData(rawFields));
    
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // set sort order
        Comparator byIns = Comparator.comparing(EdiRecord::getInsCompany);
        Comparator byLast= Comparator.comparing(EdiRecord::getLastName);
        Comparator byFirst= Comparator.comparing(EdiRecord::getFirstName);
        Comparator fileHandlerOrder = byIns.thenComparing(byLast).thenComparing(byFirst);
        Collections.sort(ediList, fileHandlerOrder);
        //create FileHandler and process files
        FileHandler fileHandler = new FileHandler();
        fileHandler.processFiles(ediList);
        System.out.println("EdiData file parse complete");

    }

	private static EdiRecord parseData(String[] rawFields) {
		EdiRecord recordOut = new EdiRecord();
		recordOut.setUserId(rawFields[0]);
		recordOut.setFirstName(getFirstName(rawFields[1]));
		recordOut.setLastName(getLastName(rawFields[1]));
		recordOut.setVersionNo(Integer.valueOf(rawFields[2]));
		recordOut.setInsCompany(rawFields[3]);
		return recordOut;
	}
// simplistic approach to parsing name. Assuming that names with multiple spaces
// are formatted like Mary Ann Wells and not Heinrich von Steuben
	private static String getLastName(String valIn) {
		int indx = valIn.lastIndexOf(' ') + 1;
		String valOut = valIn.substring(indx);
		return valOut;
	}

	private static String getFirstName(String valIn) {
		int indx = valIn.lastIndexOf(' ');
		String valOut = valIn.substring(0, indx);
		return valOut;

	}
}
