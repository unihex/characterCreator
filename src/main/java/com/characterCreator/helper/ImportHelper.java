package com.characterCreator.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImportHelper {
	
	public static <T> List<T> getListFromJson(byte[] jsonBytes, Class<T> target) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType listType = mapper.getTypeFactory().constructCollectionType(List.class, target);
		List<T> objectList = new ArrayList<>();
		
		try {
			objectList = mapper.readValue(jsonBytes, listType);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectList;
	
	}
	
	public static <T> List<T> getListFromCsv(InputStream fileInputStream, CellProcessor[] schema, Class<T> target) {
		List<T> objectList = new ArrayList<>();
		ICsvBeanReader csvReader = null;
		
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			csvReader = new CsvBeanReader(bufferedReader, CsvPreference.STANDARD_PREFERENCE);
			
			String[] header = csvReader.getHeader(true);
			
			for (int i = 0; i < header.length; i++) {
				if (header[i].equals("class")) {
					header[i] = "characterClass";
					break;
				}
			}
			
			T object = csvReader.read(target, header, schema);
			
			while (object != null) {
				objectList.add(object);
				object = csvReader.read(target, header, schema);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		} finally {
			IOUtils.closeQuietly(csvReader);
			
		}
		
		
		return objectList;
	}

}
