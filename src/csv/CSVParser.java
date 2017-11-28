package csv;

import java.io.IOException;
import java.io.Reader;

/**
 * CSV SAX like parser. Emulates behavior of Microsoft EXCEL when reading CSV
 * file
 */
public class CSVParser {

	private final char SEPARATOR;
	private final char STRING;
	private final char LF = '\n';
	private final char CR = '\r';

	public CSVParser() {
		this(',', '"');
	}

	public CSVParser(char separator, char string) {
		SEPARATOR = separator;
		STRING = string;
	}

	public void parse(Reader in, CSVListener listener) throws IOException {
		StringBuffer cellValue = new StringBuffer(1024);

		int c = in.read();

		while (true) {
			if (c == -1) {
				if (cellValue.length() > 0) {
					listener.cell(cellValue.toString());
					cellValue.setLength(0);
					listener.row();
				}
				return;
			}
			if (c == SEPARATOR || c == LF) {
				// remove CR if any at the end
				int len = cellValue.length();
				if (len > 0 && cellValue.charAt(len - 1) == CR) {
					cellValue.setLength(len - 1);
				}
				listener.cell(cellValue.toString());
				cellValue.setLength(0);
				if (c == LF) {
					listener.row();
				}
				c = in.read();
				continue;
			}
			if (c == STRING) {
				// read until " and escape "" to "
				while (true) {
					while (true) {
						c = in.read();
						if (c == STRING || c == -1) break;
						cellValue.append((char) c);
					}
					c = in.read();
					if (c != STRING) {
						break;
					}
					cellValue.append((char) c);
				}
			}
			// read until SEPARATOR or \n
			while (true) {
				if (c == SEPARATOR || c == LF || c == -1) break;
				cellValue.append((char) c);
				c = in.read();
			}
		}
	}
}
