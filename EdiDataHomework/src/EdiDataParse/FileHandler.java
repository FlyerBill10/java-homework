package EdiDataParse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Bill Farrell
 * Java class to process list of EdiRecords
 * File is output using EdiRecord.toString(). Other output formats like 
 * JSON could be incorporated
 *
 */
public class FileHandler {

	public void processFiles(List<EdiRecord> recordList) {
		   
	    Map<String, List<EdiRecord>> insMap =
	        recordList.stream().collect(Collectors.groupingBy(EdiRecord::getInsCompany));
	    Path outPath = getOutputPath();
	    insMap.forEach((k,v) -> processInsList(v, outPath));
	    
	  }

	  private Path getOutputPath() {
		Instant instant = Instant.now();
		String pathName = "./output/" + instant.toString().replaceAll("[:.]", "") + "/";
	//	String pathName = "./output/fred";
		Path dirPath = Paths.get(pathName);
		if (Files.notExists(dirPath)) {
	    	try {
				Files.createDirectory(dirPath);
				System.out.println("Directory created for " + dirPath.getFileName().toAbsolutePath().toString());
			} catch (IOException e) {
	  		   e.printStackTrace();
			}
	    }
		return dirPath;
	}
// look for duplicate UserId and remove dup with lower version
	private void processInsList(List<EdiRecord> listIn, Path pathIn) {
	    String outputFileName = listIn.get(0).getInsCompany() + ".txt";
	    // get map of all userIds to their records by grouping by UserId
	    Map<String, List<EdiRecord>> dupMap = getDuplicateMap(listIn);
	    // elimiante maps where there is only record per userId
	    dupMap.values().stream().filter(duplicates -> duplicates.size() > 1);
	    //If I have duplicates eliminate lowest version
	    if (dupMap.size() > 0) {
	      dupMap.forEach((k,v) -> {
	        int highestVer = findHighestVer(v);        
	        Predicate<EdiRecord> dupPred = ediRecord -> ediRecord.userId.equals(k) && ediRecord.versionNo < highestVer;
	        listIn.removeIf(dupPred);
	      });
	    }
	    try {
			outputFile(listIn,outputFileName, pathIn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

// procedure to take list of records and output
	  private void outputFile(List<EdiRecord> listIn, String outputFileName, Path p2) throws IOException {
	// create path for file name inside directory
	  Path resolvedPath = p2.resolve(outputFileName);		
	    if (Files.notExists(resolvedPath)) {
	    	Files.createFile(resolvedPath);
			System.out.println("File created for " + resolvedPath.getFileName().toAbsolutePath().toString());
			
	    }
	 //write out list to file
	    try(PrintWriter out = new PrintWriter(Files.newBufferedWriter(resolvedPath))) {
	      listIn.stream().forEach(
	          l -> out.println(l.toString()));
	      out.close();
	    } catch (Exception e) {     
	      e.printStackTrace();
	    }
	    
	  }

// Takes in List of duplicate records and returns highest version
	  private int findHighestVer(List<EdiRecord> v) {
	    int highestVer = 0;
	    for (int i=0; i < v.size(); i++) {
	      if (v.get(i).versionNo > highestVer) {
	        highestVer = v.get(i).versionNo;
	      }
	    }
	    return highestVer;
	  }
	  
	  
// Create map with list of records per userId
// Could have performed this inline but separated for troubleshooting
	  private Map<String, List<EdiRecord>> getDuplicateMap(List<EdiRecord> listIn) {
	    return listIn.stream().collect(Collectors.groupingBy(EdiRecord::getUserId));
	  }
		
	

}
