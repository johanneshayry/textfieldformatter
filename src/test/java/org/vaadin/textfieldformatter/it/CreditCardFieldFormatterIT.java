package org.vaadin.textfieldformatter.it;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.vaadin.textfieldformatter.BasicCreditCardFieldFormatterUsageUI;
import org.vaadin.textfieldformatter.ZenBasicCreditCardFieldFormatterUsageUI;

import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;

public class CreditCardFieldFormatterIT extends AbstractCustomTestBenchTestCase {

	@Before
	public void init() {
		startBrowser();
	}

	@Test
	public void validVisaNumber() throws InterruptedException {
		openUI(BasicCreditCardFieldFormatterUsageUI.class);
		TextFieldElement tf = $(TextFieldElement.class).first();
		tf.sendKeys("4");
		NotificationElement notification = $(NotificationElement.class).onPage().first();
		Assert.assertEquals("VISA", notification.getText());
		tf.sendKeys("000000000000000");
		Assert.assertEquals("4000 0000 0000 0000", tf.getValue());
		tf.sendKeys("23445");
		Assert.assertEquals("4000 0000 0000 0000", tf.getValue());
	}
	@Test
	public void validVisaNumberZen() throws InterruptedException {
		openUI(ZenBasicCreditCardFieldFormatterUsageUI.class);
		TextFieldElement tf = $(TextFieldElement.class).first();
		tf.sendKeys("4");
		NotificationElement notification = $(NotificationElement.class).onPage().first();
		Assert.assertEquals("VISA", notification.getText());
		tf.sendKeys("000000000000000");
		Assert.assertEquals("4000 0000 0000 0000", tf.getValue());
		tf.sendKeys("23445");
		Assert.assertEquals("4000 0000 0000 0000", tf.getValue());
	}
}
