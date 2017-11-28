package csv;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

public class CSVParserTest extends TestCase implements CSVListener {

	List values = new ArrayList();
	List rowsId = new ArrayList();

	public void testSAX1() throws IOException {
		String source = "aaa,bbbb,cccccc\r\n\"aaa\",bb\"bb,cc\"\"cc\r\n\"aa\"\"aa\" ,bbb\"bb\"\"bb\" , ccc \r\n\"aa\"\"aa\" \"aa\"\"aa\",bbb\"bb\"\"bb\" , ccc \r\n\"aa\"\"aa\" \"aa\"\"aa,\"bbb\"bb\"\"bb\"\" , \"ccc\" ";
		String[] expected = new String[] { "aaa", "bbbb", "cccccc", "aaa", "bb\"bb", "cc\"\"cc", "aa\"aa ",
				"bbb\"bb\"\"bb\" ", " ccc ", "aa\"aa \"aa\"\"aa\"", "bbb\"bb\"\"bb\" ", " ccc ", "aa\"aa \"aa\"\"aa",
				"bbbbb\"\"bb\"\" ", " \"ccc\" " };
		int[] expectedRows = new int[] { 3, 6, 9, 12, 15 };

		CSVParser parser = new CSVParser();
		values.clear();
		rowsId.clear();
		parser.parse(new StringReader(source), this);

		for (int i = 0; i < expected.length; i++) {
			assertEquals("index: " + i, expected[i], values.get(i));
		}
		assertEquals(expected.length, values.size());

		for (int i = 0; i < expectedRows.length; i++) {
			assertEquals("row index: " + i, expectedRows[i], ((Integer) rowsId.get(i)).intValue());
		}
		assertEquals(expectedRows.length, rowsId.size());
	}

	public void testSAX2() throws IOException {
		String source = "0,1,2,3,4\n5,6,7,8,9\n";
		String[] expected = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		int[] expectedRows = new int[] { 5, 10 };

		CSVParser parser = new CSVParser();
		values.clear();
		rowsId.clear();
		parser.parse(new StringReader(source), this);

		for (int i = 0; i < expected.length; i++) {
			assertEquals("index: " + i, expected[i], values.get(i));
		}
		assertEquals(expected.length, values.size());

		for (int i = 0; i < expectedRows.length; i++) {
			assertEquals("row index: " + i, expectedRows[i], ((Integer) rowsId.get(i)).intValue());
		}
		assertEquals(expectedRows.length, rowsId.size());
	}

	public void cell(String value) {
		values.add(value);
	}

	public void row() {
		rowsId.add(new Integer(values.size()));
	}
}
