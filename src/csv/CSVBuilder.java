package csv;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVBuilder {
	private final char SEPARATOR;
	private final char STRING;
	final Writer writer;
	final boolean excelMode;

	public CSVBuilder(Writer writer, boolean excelMode) throws IOException {
		this(writer, ',', '"', excelMode);
	}

	public CSVBuilder(Writer writer, char separator, char string, boolean excelMode) throws IOException {
		SEPARATOR = separator;
		STRING = string;
		this.writer = writer;
		newRow = true;
		this.excelMode = excelMode;
		if (excelMode) {
			writer.write("sep=,\n");// define the CSV separator
		}
	}

	private boolean newRow = true;

	public CSVBuilder newRow() throws IOException {
		newRow = true;
		writer.write('\n');
		return this;
	}

	public CSVBuilder cell(String value) throws IOException {
		if (!newRow) {
			writer.write(SEPARATOR);
		}
		newRow = false;
		if (value.length() > 0) {
			if (excelMode) {
				if (value.indexOf('\n') < 0 && value.indexOf(SEPARATOR) < 0) {
					writer.write('=');
				}
			}
			writer.write(STRING);
			escape(writer, value);
			writer.write(STRING);
		}
		return this;
	}

	public CSVBuilder cell(int value) throws IOException {
		if (!newRow) {
			writer.write(SEPARATOR);
		}
		newRow = false;
		writer.write(String.valueOf(value));
		return this;
	}

	public CSVBuilder cell(long value) throws IOException {
		if (!newRow) {
			writer.write(SEPARATOR);
		}
		newRow = false;
		writer.write(String.valueOf(value));
		return this;
	}

	SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public CSVBuilder cell(Date value) throws IOException {
		if (!newRow) {
			writer.write(SEPARATOR);
		}
		newRow = false;
		writer.write(YYYY_MM_DD_HH_MM_SS.format(value));
		return this;
	}

	public void done() throws IOException {
		writer.flush();
		writer.close();
	}

	public final void escape(Writer writer, String str) throws IOException {
		if (str == null) {
			return;
		}
		for (int i = 0, j = str.length(); i < j; i++) {
			final char c = str.charAt(i);
			if (c == STRING) {
				writer.write(STRING);
				writer.write(STRING);
			} else {
				writer.write(c);
			}
		}
	}
}
