package org.vaadin.textfieldformatter;

import org.vaadin.formatter.ZenCustomStringBlockFormatter;
import org.vaadin.formatter.ZenCustomStringBlockFormatter.Options;
import org.vaadin.formatter.ZenCustomStringBlockFormatter.ForceCase;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;

public class ZenCSBFNumericAndPrefixWithBlocksUI extends AbstractTest {

	@Override
	public Component getTestComponent(UITestConfiguration configuration) {
		TextField tf = new TextField();
		Options fmtOptions = new Options();
		fmtOptions.setBlocks(7, 1, 2, 3);
		fmtOptions.setDelimiters(" ", "-", "-");
		fmtOptions.setForceCase(ForceCase.NONE);
		fmtOptions.setNumericOnly(true);
		fmtOptions.setPrefix("PREFIX:");
		ZenCustomStringBlockFormatter formatter = new ZenCustomStringBlockFormatter(fmtOptions);
		formatter.extend(tf);
		return tf;
	}
}
