package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Object;



//import com.csvreader.CsvReader;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class WekaPredict {

	public static <CsvWriter> void main(String[] args) throws IOException {
		System.out.println("hi");
		String outputFile = "/home/megha/finalproject.csv";
		
		CSVWriter csvWriter;
		boolean alreadyExists = new File(outputFile).exists();
		csvWriter = new CSVWriter(new FileWriter(outputFile), ',', CSVWriter.NO_QUOTE_CHARACTER);
		
		
		
		try {
			
			if(!alreadyExists) {
				List<String[]> heading = new ArrayList<String[]>();
				heading.add(new String[] {"date","close","classd"});
				csvWriter.writeAll(heading);
			//	csvWriter.close();
			}

			
			
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB("localhost");
			DBCollection collection = db.getCollection("finalproject");
			//db.getCollection("finalproject").find();
			BasicDBObject myDoc = (BasicDBObject) collection.findOne();
			//DBObject myDoc = collection.findOne();
			System.out.println(myDoc);
			
			BasicDBObject query = new BasicDBObject();
			DBCursor curs=  collection.find(query);

			while(curs.hasNext()) {
				BasicDBObject obj = (BasicDBObject) curs.next();
				String date = (String) obj.getString("Date");
				String close = (String) obj.getString("Close");
				String company = obj.getString("Company");
				List<String[]> value = new ArrayList<String[]>();
				value.add(new String[] {date, close, company});
				csvWriter.writeAll(value);
				System.out.println(date+close+company);
			}
			csvWriter.close();
			/*
			DBCursor cursors = collection.find();
			//DBCursor cursors = collection.find();
			while(cursors.hasNext()) {	
				
				DBObject cur = cursors.next();
				String date = cur.get("date");
		
				
				//System.out.println(cur.get(date));
				//System.out.println(cur);	
				
			}
			*/
			/*
			
			GridFS gridfs = new GridFS(db);
			byte[] file = new byte[1000000];
			//byte[] file = null;
			//GridFSInputFile gfsFile = gridfs.createFile(file);
			GridFSInputFile gfsFile = gridfs.createFile(file);
			
			gfsFile.setFilename("books2");
			gfsFile.save();
			System.out.println("file saved");
			
			
			DBCursor cursor = collection.find();
		//	BasicDBObject query = new BasicDBObject("date", "close");
			//cursor = collection.find(query);
			
			
			
			try {
				while(cursor.hasNext()) {
					System.out.println(cursor.next());
					
				} 
			} finally {
				cursor.close();
			}
			
			GridFSDBFile out = gridfs.findOne(new BasicDBObject("_id", gfsFile.getId()));
			try {
				FileOutputStream outputFile = new FileOutputStream("/home/megha/final.csv");
				out.writeTo(outputFile);
				outputFile.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/

					
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	

	}

}
