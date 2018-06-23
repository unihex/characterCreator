package com.characterCreator.helper;


import java.io.PrintWriter;

import java.util.List;

import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.io.CsvBeanWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


public class ExportHelper {
	
	public static <T> void writeJson(List<T> objectList, PrintWriter writer) throws Exception {
		ObjectWriter jsonWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
		jsonWriter.writeValue(writer, objectList);	
	}
	
	public static <T> void writeCsv(String schema, List<T> objectList, PrintWriter writer) throws Exception {
		 ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);
		 String[] schemaArray = schema.split(",");
		 csvWriter.writeHeader(schema.replace("characterClass", "class").split(","));
		 
		 for (T element : objectList) {
			 csvWriter.write(element, schemaArray);
		 }
		 
		 csvWriter.close();
		
	}
}
