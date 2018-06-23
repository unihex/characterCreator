<!DOCTYPE html>
<html>
<head>
    <title>Character Listing</title>
    
    <meta http-equiv="Content-Type" content="no-cache; text/html; charset=UTF-8" />
    
    <link rel="stylesheet" href="/webjars/bootstrap/4.0.0-2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/styles.css"/>
    
    
    <script src="/javascript/scripts.js"></script>
    <script src="/webjars/jquery/3.3.1-1/jquery.min.js"></script>
</head>

<body onload="checkFileSize(document.getElementById('uploadField'))">
	<h1 class="text-center">Character Listing</h1>
	
	<#if errorMessages??>
		<div class="pt-3 pb-3 text-center">
			<#list errorMessages as error>
				<div class="alert-danger mb-2">${error}</div>
			</#list>
		</div>
	</#if>	
	
	<div>
		<div class="float-left">
			<#list pcList as pc>
				<#if pc?is_odd_item>
					<div>
						<a href="/playerCharacters/${pc.playerCharacterId}">${pc.name}<br></a> 
						${pc.race}<br> 
						Level ${pc.level} ${pc.characterClass}
					</div><br>
				</#if>
			</#list>
		</div>
		
		<div class="float-right">
			<#list pcList as pc>
				<#if pc?is_even_item>
					<div>
						<a href="/playerCharacters/${pc.playerCharacterId}">${pc.name}<br></a> 
						${pc.race}<br> 
						Level ${pc.level} ${pc.characterClass}
					</div><br>
				</#if>
			</#list>
		</div>
	</div>
		
	<div class="mt-5 text-center">
		<span>	
			<a href="/playerCharacters/new" class="btn btn-primary">New Character</a>
		</span>
		<span>	
			<button type="button" id="btnImport" class="btn btn-success" onclick="importButton()">Import Characters</button>
		</span>
		<span>	
			<button type="button" id="btnExport" class="btn btn-danger" onclick="exportButton()">Export Characters</button>
		</span>
	</div>
	
	<div class="mt-5 text-center">
		<div hidden="true" id="exportDiv">
			<span>
				<a href="/playerCharacters/export/json" class="btn btn-danger">Export JSON</a>
			</span>
			<span>
				<a href="/playerCharacters/export/csv" class="btn btn-danger">Export CSV</a>
			</span>
		</div>
		
		<div hidden="true" id="importDiv" class="mb-3">
			<span>
				<button type="button" id="btnImportJson" class="btn btn-success" onclick="importButtonJson()">Import JSON</button>
			</span>
			<span>
				<button type="button" id="btnImportCsv" class="btn btn-success" onclick="importButtonCsv()">Import CSV</button>
			</span>
		</div>		
		
		<div hidden="true" id=uploadJsonDiv>
			<form action="/playerCharacters/import/json" enctype="multipart/form-data" method="post">
				File to upload: <input type="file" name="file" onchange="checkFileSize(this)" id="uploadField"><br /> 
				<input type="submit" value="Upload" id="uploadButton">
			</form>
		</div>
		
		<div hidden="true" id=uploadCsvDiv>
			<form action="/playerCharacters/import/csv" enctype="multipart/form-data" method="post">
				File to upload: <input type="file" name="file" onchange="checkFileSize(this)" id="uploadField"><br /> 
				<input type="submit" value="Upload" id="uploadButton">
			</form>
		</div>		
	</div>
</body>
</html>