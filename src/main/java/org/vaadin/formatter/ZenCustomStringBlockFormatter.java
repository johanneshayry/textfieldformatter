package org.vaadin.formatter;

import java.util.ArrayList;

import org.vaadin.formatter.conf.CleaveZenExtension;
import org.vaadin.formatter.conf.FormatGeneralOptions;

import com.vaadin.flow.component.textfield.TextField;

public class ZenCustomStringBlockFormatter extends CleaveZenExtension<FormatGeneralOptions> {

	/**
	 * Creates an instance of CustomStringBlockFormatter. Call extend to attach this
	 * formatter to a TextField.
	 * 
	 * @param blocks      an array configuring the character blocks. The length of
	 *                    the array is the number of character blocks. The integers
	 *                    in the array are the length of the blocks.
	 * @param delimiters  the delimiter characters to be used between the character
	 *                    blocks
	 * @param forceCase   enumeration to control letter case of characters. One of
	 *                    ForceCase.NONE don't touch letter case. ForceCase.LOWER
	 *                    transform characters to lower case. ForceCase.UPPER
	 *                    transform characters to upper case.
	 * @param prefix      String prefix for the input
	 * @param numericOnly true for allowing numeric characters only. Defaults to
	 *                    false.
	 */
	public ZenCustomStringBlockFormatter(int[] blocks, String[] delimiters, ForceCase forceCase, String prefix,
										 boolean numericOnly) {
		this(blocks, delimiters, false, forceCase, prefix, true, numericOnly);
	}

	/**
	 * Creates an instance of CustomStringBlockFormatter. Call extend to attach this
	 * formatter to a TextField.
	 * 
	 * @param blocks                an array configuring the character blocks. The
	 *                              length of the array is the number of character
	 *                              blocks. The integers in the array are the length
	 *                              of the blocks.
	 * @param delimiters            the delimiter characters to be used between the
	 *                              character blocks
	 * @param forceCase             enumeration to control letter case of
	 *                              characters. One of ForceCase.NONE don't touch
	 *                              letter case. ForceCase.LOWER transform
	 *                              characters to lower case. ForceCase.UPPER
	 *                              transform characters to upper case.
	 * @param prefix                String prefix for the input
	 * @param showPrefixImmediately true if you want to show prefix before value is
	 *                              entered i.e. always. Default true.
	 * @param numericOnly           true for allowing numeric characters only.
	 *                              Defaults to false.
	 */
	public ZenCustomStringBlockFormatter(int[] blocks, String[] delimiters, ForceCase forceCase, String prefix,
										 boolean showPrefixImmediately, boolean numericOnly) {
		this(blocks, delimiters, false, forceCase, prefix, showPrefixImmediately, numericOnly);
	}

	private ZenCustomStringBlockFormatter(int[] blocks, String[] delimiters, boolean lazyDelimiter, ForceCase forceCase,
										  String prefix, boolean showPrefixImmediately, boolean numericOnly) {
		getConfiguration().blocks = blocks;
		getConfiguration().delimiters = delimiters;
		getConfiguration().delimiterLazyShow = lazyDelimiter;
		if (forceCase == ForceCase.UPPER) {
			getConfiguration().lowercase = false;
			getConfiguration().uppercase = true;
		} else if (forceCase == ForceCase.LOWER) {
			getConfiguration().lowercase = true;
			getConfiguration().uppercase = false;
		}
		getConfiguration().prefix = prefix;
		// todo jcg getConfiguration().showPrefixImmediately = showPrefixImmediately;
		getConfiguration().numericOnly = numericOnly;
	}

	public ZenCustomStringBlockFormatter(Options options) {
		this(options.blocks, options.delimiters, options.lazyDelimiter, options.forceCase, options.prefix,
				options.showPrefixImmediately, options.numericOnly);
	}

	/**
	 * Adds this extension to a TextField. Extension cannot be moved to another
	 * TextField again.
	 * 
	 * @param textField TextField to attach this extension to
	 */
	public void extend(TextField textField) {
		super.extend(textField);
	}

	@Override
	protected FormatGeneralOptions createDefaultConfiguration() {
		return new FormatGeneralOptions();
	}

	public enum ForceCase {
		NONE, UPPER, LOWER
	}

	public static class Builder {
		private String prefix;
		private boolean showPrefixImmediately = true;
		private ForceCase _case;
		private boolean numeric;
		private final ArrayList<String> delimiters = new ArrayList<>();
		private boolean lazyDelimiter = false;
		private final ArrayList<Integer> blocks = new ArrayList<>();

		/**
		 * Creates prefix as the first block and delimiter. This will replaced
		 * previously set prefix block. This will prepend any previously set normal
		 * blocks.
		 * 
		 * @param prefix    String prefix to use. Cannot be null or empty.
		 * @param delimiter String delimiter after the prefix block. Cannot be null or
		 *                  empty.
		 * @return this Builder
		 */
		public Builder prefix(String prefix, String delimiter) {
			return prefix(prefix, true, delimiter);
		}

		/**
		 * Creates prefix as the first block and delimiter. This will replaced
		 * previously set prefix block. This will prepend any previously set normal
		 * blocks.
		 * 
		 * @param prefix                String prefix to use. Cannot be null or empty.
		 * @param showPrefixImmediately true if the prefix should be shown even before
		 *                              user has input anything to the field. Default
		 *                              behavior is true.
		 * @param delimiter             String delimiter after the prefix block. Cannot
		 *                              be null or empty.
		 * @return this Builder
		 */
		public Builder prefix(String prefix, boolean showPrefixImmediately, String delimiter) {
			if (prefix == null || prefix.isEmpty())
				throw new IllegalArgumentException("Prefix cannot be null or empty.");
			if (delimiter == null || delimiter.isEmpty())
				throw new IllegalArgumentException("Delimiter cannot be null or empty.");
			this.prefix = prefix;
			this.showPrefixImmediately = showPrefixImmediately;
			blocks.add(0, prefix.length());
			delimiters.add(0, delimiter);
			return this;
		}

		/**
		 * Appends a block with a given block length and delimiter character. If this is
		 * the last block, the delimiter will be ignored.
		 * 
		 * @param blockLength int block length
		 * @param delimiter   String delimiter after block. Cannot be null or empty.
		 * @return this Builder
		 */
		public Builder block(int blockLength, String delimiter) {
			if (delimiter == null || delimiter.isEmpty())
				throw new IllegalArgumentException("Delimiter cannot be null or empty.");
			blocks.add(blockLength);
			delimiters.add(delimiter);
			return this;
		}

		/**
		 * Appends the blocks with a given block lengths.
		 * 
		 * @param blockLengths int[] block lengths
		 * @return this Builder
		 */
		public Builder blocks(int... blockLengths) {
			if (blockLengths == null)
				throw new IllegalArgumentException("Block lengths cannot be null.");
			for (int block : blockLengths) {
				blocks.add(block);
			}
			return this;
		}

		/**
		 * Appends the blocks with a given block lengths.
		 * 
		 * @param delimiters String[] delimiters to be used. Cannot be null.
		 * @return this Builder
		 */
		public Builder delimiters(String... delimiters) {
			if (delimiters == null)
				throw new IllegalArgumentException("Delimiters cannot be null.");
			for (String delimiter : delimiters) {
				this.delimiters.add(delimiter);
			}
			return this;
		}

		/**
		 * Show the delimiter only when the user starts typing the next group section.
		 * Default is false.
		 * 
		 * @return
		 */
		public Builder delimiterLazyShow() {
			lazyDelimiter = true;
			return this;
		}

		/**
		 * Allows only numeric characters. Default is allowing any character.
		 * 
		 * @return this Builder
		 */
		public Builder numeric() {
			numeric = true;
			return this;
		}

		/**
		 * Sets the forced letter casing of characters.
		 * 
		 * @param _case enumeration to control letter case of characters. ForceCase.NONE
		 *              don't touch letter case. ForceCase.LOWER transform characters to
		 *              lower case. ForceCase.UPPER transform characters to upper case.
		 * @return this Builder
		 */
		public Builder forceCase(ForceCase _case) {
			this._case = _case;
			return this;
		}

		public ZenCustomStringBlockFormatter build() {
			return new ZenCustomStringBlockFormatter(buildOptions());
		}

		public Options buildOptions() {
			Options options = new Options();
			options.setForceCase(_case);
			options.setNumericOnly(numeric);
			options.setPrefix(prefix, showPrefixImmediately);
			if (!blocks.isEmpty() && !delimiters.isEmpty()) {
				options.setBlocks(blocks.stream().mapToInt(Integer::valueOf).toArray());
				options.setDelimiters(delimiters.toArray(new String[delimiters.size()]));
			}
			options.setDelimiterLazyShow(lazyDelimiter);
			return options;
		}
	}

	public static class Options {
		private int[] blocks;
		private String[] delimiters = null;
		private boolean lazyDelimiter = false;
		private ForceCase forceCase = ForceCase.NONE;
		private boolean numericOnly = false;
		private String prefix;
		private boolean showPrefixImmediately = true;

		public Options(int[] blocks, String[] delimiters, ForceCase forceCase, String prefix, boolean numericOnly) {
			this(blocks, delimiters, forceCase, prefix, true, numericOnly);
		}

		public Options(int[] blocks, String[] delimiters, ForceCase forceCase, String prefix,
				boolean showPrefixImmediately, boolean numericOnly) {
			this(blocks, delimiters, false, forceCase, prefix, showPrefixImmediately, numericOnly);
		}

		public Options(int[] blocks, String[] delimiters, boolean delimiterLazyShow, ForceCase forceCase, String prefix,
				boolean showPrefixImmediately, boolean numericOnly) {
			this.blocks = blocks;
			this.delimiters = delimiters;
			this.lazyDelimiter = delimiterLazyShow;
			this.forceCase = forceCase;
			this.prefix = prefix;
			this.showPrefixImmediately = showPrefixImmediately;
			this.numericOnly = numericOnly;
		}

		public Options(Options options) {
			this(options.blocks, options.delimiters, options.lazyDelimiter, options.forceCase, options.prefix,
					options.showPrefixImmediately, options.numericOnly);
		}

		public Options() {

		}

		public int[] getBlocks() {
			return blocks;
		}

		public void setBlocks(int... blocks) {
			this.blocks = blocks;
		}

		public String[] getDelimiters() {
			return delimiters;
		}

		public void setDelimiters(String... delimiters) {
			this.delimiters = delimiters;
		}

		/**
		 * 
		 * @param lazy if true, will lazy add the delimiter only when the user starting
		 *             typing the next group section. Default is false.
		 */
		public void setDelimiterLazyShow(boolean lazy) {
			this.lazyDelimiter = lazy;
		}

		public boolean isDelimiterLazyShow() {
			return lazyDelimiter;
		}

		public ForceCase getForceCase() {
			return forceCase;
		}

		public void setForceCase(ForceCase forceCase) {
			this.forceCase = forceCase;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			setPrefix(prefix, true);
		}

		/**
		 * 
		 * @param prefix          Prefix to show
		 * @param showImmediately true if the prefix should be shown even before user
		 *                        has input anything to the field. Default for Options
		 *                        is true.
		 */
		public void setPrefix(String prefix, boolean showImmediately) {
			this.prefix = prefix;
			this.showPrefixImmediately = showImmediately;
		}

		public boolean isNumericOnly() {
			return numericOnly;
		}

		public void setNumericOnly(boolean numericOnly) {
			this.numericOnly = numericOnly;
		}
	}
}
