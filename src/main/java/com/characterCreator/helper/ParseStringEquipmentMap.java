package com.characterCreator.helper;

import java.util.HashMap;
import java.util.Map;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import com.characterCreator.domain.Equipment;

public class ParseStringEquipmentMap extends CellProcessorAdaptor {

	
	public ParseStringEquipmentMap() {
		super();
	}
	
	public <T> T execute(Object mapAsString, CsvContext context) {
		Map<String, Object> map = new HashMap<>();
		
		String[] mapAsStringArray = mapAsString.toString().split(",");
		int lastIndex = mapAsStringArray.length - 1;
		
		mapAsStringArray[0] = mapAsStringArray[0].substring(1);
		mapAsStringArray[lastIndex] = mapAsStringArray[lastIndex].replace("}", "");
		
		for (String keyWithValue : mapAsStringArray) {
			String[] keyWithValueArray = keyWithValue.split("=");
			
			String key = keyWithValueArray[0].trim();
			String value = keyWithValueArray[1].trim();
			boolean found = false;
			
			for (Equipment equipment : Equipment.getAllEquipmentByType(key)) {
				if (equipment.toString().equals(value)) {
					map.put(key, equipment);
					found = true;
					break;
				}
				
				
			}
			
			if (!found) {
				throw new SuperCsvCellProcessorException(String.format("%s is not a valid equipment!", value), context, this);
			}
		}
		
		
		return next.execute(map, context);
	}

}
