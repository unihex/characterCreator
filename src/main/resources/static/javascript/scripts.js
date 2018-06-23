function exportButton() {
	$('#btnExport').attr('disabled', true);
	$('#btnExport').blur();
	
	$('#btnImport').attr('disabled', false);
	
	$('#exportDiv').attr('hidden', false);
	
	$('#importDiv').attr('hidden', true);

}


function importButton() {
	$('#btnImport').attr('disabled', true);
	$('#btnImport').blur();
	
	$('#btnExport').attr('disabled', false);
	
	$('#importDiv').attr('hidden', false);
	
	$('#exportDiv').attr('hidden', true);
	
}

function importButtonJson() {
	$('#btnImportJson').attr('disabled', true);
	$('#btnImportJson').blur();
	
	$('#btnImportCsv').attr('disabled', false);
	
	$('#uploadJsonDiv').attr('hidden', false);
	
	$('#uploadCsvDiv').attr('hidden', true);
	
}

function importButtonCsv() {
	$('#btnImportCsv').attr('disabled', true);
	$('#btnImportCsv').blur();
	
	$('#btnImportJson').attr('disabled', false);
	
	$('#uploadCsvDiv').attr('hidden', false);
	
	$('#uploadJsonDiv').attr('hidden', true);
	
}

function checkFileSize(element) {
	if (element.files[0]) {
		var fileSize = element.files[0].size / 1024;
	} else {
		return;
	}
	
	if (fileSize > 1000) {
		alert("This size of this file is: " + fileSize + "KB. It is over the 1000KB limit");
		$('#uploadButton').attr("disabled", "disabled");
	} else {
		$('#uploadButton').removeAttr("disabled");
		
	}
	
}