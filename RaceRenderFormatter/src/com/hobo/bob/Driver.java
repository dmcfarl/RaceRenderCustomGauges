package com.hobo.bob;

import java.io.File;
import java.io.IOException;

import com.hobo.bob.model.Session;
import com.hobo.bob.reader.DataExtractor;
import com.hobo.bob.writer.DataWriter;

public class Driver {

	public static void main(String[] args) {
		if(args.length < 2 || args.length > 3){
			System.out.println("Requires 2 arguments with one optional argument: sessionFile lapsFile [allLaps]");
			System.exit(1);
		}

		File dataFile = new File(args[0]);
		
		DataExtractor extractor = new DataExtractor(args[0], args[1]);
		if (args.length == 3) {
			extractor.setExtractAllLaps(Boolean.parseBoolean(args[2]));
		}
		try {
			System.out.println("Reading data...");
			Session session = extractor.extract();
			System.out.println("Data extracted...");

			DataWriter writer = new DataWriter(dataFile.getParent() + File.separator, session);
			if(extractor.getExtractAllLaps()) {
				writer.writeAll();
			} else {
				writer.writeBestAndGhost();
			}
			System.out.println("Conversion complete.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
