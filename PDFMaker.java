import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public class PDFMaker {

	public static void main (String[] args) throws IOException {
		File fileDir = new File(args[0]);
		String DEFAULT_SEPARATOR = "_";
		String fileNameSeparator;

		if (!fileDir.exists()) {
			System.out.println("Could not find the directory");
			System.exit(1);
		}

		if (!fileDir.isDirectory()) {
			System.out.println("Path is not a directory");
			System.exit(1);
		}

		if (args.length == 2) {
			fileNameSeparator = args[1];
		} else {
			fileNameSeparator = DEFAULT_SEPARATOR;
		}


		System.out.println("File name: " + fileDir.getName());
		System.out.println("Separator: " + fileNameSeparator);
		
		String testString = "piotr_myfile.pdf";

		Set<String> fileList = Stream.of(fileDir.listFiles())
			.filter(file -> !file.isDirectory())
			.map(File::getName)
			.collect(Collectors.toSet());

		System.out.println("Found " + fileList.size() + " files.");
		System.out.println("Dir contents:");

		Map<String,List<String>> parsedFileList = new TreeMap<>();
		Set<String> skippedFiles = new TreeSet<>();
		Set<String> suffixes = new TreeSet<>();
		int sepIdx;		

		for (String entry : fileList) {
			System.out.println(entry);
			sepIdx = entry.indexOf(fileNameSeparator);
						
			if (sepIdx < 0) {
				skippedFiles.add(entry);
			} else {
				String prefix = entry.substring(0, sepIdx);
				String suffix = entry.substring(sepIdx + 1, entry.length());

				System.out.println("Split file into: " + prefix + " --> " + suffix); 
				suffixes.add(suffix);

				if (parsedFileList.containsKey(prefix)) {
					parsedFileList.get(prefix).add(suffix);
				} else {
					ArrayList<String> l = new ArrayList<>();
					l.add(suffix);
					parsedFileList.put(prefix, l);
				}
			}

		}

		System.out.println("Skipped files:");
		System.out.println(skippedFiles);
		System.out.println("Suffixes:");
		System.out.println(suffixes);


	}
}
