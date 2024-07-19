package org.vaadin.textfieldformatter.it;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.vaadin.textfieldformatter.DateFieldFormatterUI;

import com.vaadin.flow.component.textfield.testbench.TextFieldElement;


public class DateFieldFormatterIT extends AbstractCustomTestBenchTestCase {
	@Before
	public void init() {
		startBrowser();
	}

	@Test
	public void monthYear() {
		openUI(DateFieldFormatterUI.class, DateFieldFormatterUI.MonthYear.class);
		TextFieldElement tf = $(TextFieldElement.class).first();
		tf.sendKeys("112011");
		Assert.assertEquals("11/2011", tf.getValue());
	}

	@Test
	public void americanDate() {
		openUI(DateFieldFormatterUI.class, DateFieldFormatterUI.AmericanDate.class);
		TextFieldElement tf = $(TextFieldElement.class).first();
		tf.sendKeys("a2.4.2020");
		Assert.assertEquals("02/04/2020", tf.getValue());
	}

	@Test
	public void finnishDate() {
		openUI(DateFieldFormatterUI.class, DateFieldFormatterUI.FinnishDate.class);
		TextFieldElement tf = $(TextFieldElement.class).first();
		tf.sendKeys("a4/2/2020");
		Assert.assertEquals("04.02.2020", tf.getValue());
	}

	@Test
	public void withMinMax() {
		openUI(DateFieldFormatterUI.class, DateFieldFormatterUI.MinMax.class);
		TextFieldElement tf = $(TextFieldElement.class).first();
		tf.sendKeys("03022019");
		Assert.assertEquals("04/02/2019", tf.getValue());
		tf.sendKeys("05022019");
		Assert.assertEquals("04/02/2019", tf.getValue());
		tf.sendKeys("04022019");
		Assert.assertEquals("04/02/2019", tf.getValue());
	}

	@Test
	public void withMin() {
		openUI(DateFieldFormatterUI.class, DateFieldFormatterUI.Min.class);
		TextFieldElement tf = $(TextFieldElement.class).first();
		tf.sendKeys("03022019");
		Assert.assertEquals("04/02/2019", tf.getValue());
		tf.sendKeys("04022019");
		Assert.assertEquals("04/02/2019", tf.getValue());
	}

	@Test
	public void withMax() {
		openUI(DateFieldFormatterUI.class, DateFieldFormatterUI.Max.class);
		TextFieldElement tf = $(TextFieldElement.class).first();
		tf.sendKeys("04022019");
		Assert.assertEquals("03/02/2019", tf.getValue());
		tf.sendKeys("03022019");
		Assert.assertEquals("03/02/2019", tf.getValue());
	}
}
