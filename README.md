# CSV
CSV format parser and builder in Java 1.4.
Requires JDK 1.4 or higher and junit.jar for tests

### Examples

#### CSV parsing
```java
	CSVListener listener = new CSVListener(){
	public void cell(String value) {
		// new cell value was parsed
	}

	public void row() {
		// end of row was encountered
	}
};
CSVParser parser = new CSVParser();
parser.parse(new StringReader(source), listener);
```

#### CSV creation:
```java
	StringWriter stringWriter = new StringWriter(1024);
	CSVBuilder csvBuilder = new CSVBuilder(stringWriter, false);

	csvBuilder.cell(42);
	csvBuilder.cell(36028797018963967L);
	csvBuilder.newRow();
	csvBuilder.cell("test\ntest");
	csvBuilder.cell("test\"test");
	csvBuilder.done();

	System.out.print(stringWriter.getBuffer().toString());
```

Outputs: 
```42,36028797018963967
"test
test","test""test"
```