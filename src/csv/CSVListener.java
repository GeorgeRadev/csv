package csv;

/**
 * SAX listener for CSV parsing
 */
public interface CSVListener {

	/**
	 * called when a cell value was found
	 */
	void cell(String value);

	/**
	 * current row has ended.
	 */
	void row();
}
