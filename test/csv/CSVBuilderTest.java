package csv;

import java.io.IOException;
import java.io.StringWriter;
import junit.framework.TestCase;

public class CSVBuilderTest extends TestCase {

	public void testEscaping() throws IOException {
		StringWriter stringWriter = new StringWriter(1024);
		CSVBuilder csvBuilder = new CSVBuilder(stringWriter, true);
		StringWriter buff = new StringWriter();

		csvBuilder.escape(buff, null);
		assertEquals("", buff.toString());

		buff = new StringWriter();
		csvBuilder.escape(buff, "");
		assertEquals("", buff.toString());

		buff = new StringWriter();
		csvBuilder.escape(buff, "test\"test");
		assertEquals("test\"\"test", buff.toString());

		buff = new StringWriter();
		csvBuilder.escape(buff, "\"\"");
		assertEquals("\"\"\"\"", buff.toString());
	}

	public void testBuilder1() throws IOException {
		StringWriter stringWriter = new StringWriter(1024);
		CSVBuilder csvBuilder = new CSVBuilder(stringWriter, true);

		csvBuilder.cell(42);
		csvBuilder.cell(36028797018963967L);
		csvBuilder.newRow();
		csvBuilder.cell("test\ntest");
		csvBuilder.cell("test\"test");
		csvBuilder.done();

		String result = stringWriter.getBuffer().toString();
		assertEquals("sep=,\n42,36028797018963967\n\"test\ntest\",=\"test\"\"test\"", result);
	}

	public void testBuilder2() throws IOException {
		StringWriter stringWriter = new StringWriter(1024);
		CSVBuilder csvBuilder = new CSVBuilder(stringWriter, false);

		csvBuilder.cell(42);
		csvBuilder.cell(36028797018963967L);
		csvBuilder.newRow();
		csvBuilder.cell("test\ntest");
		csvBuilder.cell("test\"test");
		csvBuilder.done();

		String result = stringWriter.getBuffer().toString();
		assertEquals("42,36028797018963967\n\"test\ntest\",\"test\"\"test\"", result);
	}
}
