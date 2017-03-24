package org.vaadin.textfieldformatter;

import com.vaadin.shared.JavaScriptExtensionState;

public class TextFieldFormatterState extends JavaScriptExtensionState {
	public boolean formatCreditCard = false;
	public int[] formatBlocks;
	public String[] delimeters;
	public String delimeter;
	public boolean uppercase = false;
	public boolean lowercase = false;
}
