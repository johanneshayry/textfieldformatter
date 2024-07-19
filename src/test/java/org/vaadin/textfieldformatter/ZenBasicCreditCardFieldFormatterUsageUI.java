package org.vaadin.textfieldformatter;

import org.vaadin.textfieldformatterzen.ZenCreditCardFieldFormatter;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class ZenBasicCreditCardFieldFormatterUsageUI extends AbstractTest {

	@Override
	public Component getTestComponent(UITestConfiguration configuration) {
		TextField tf = new TextField();
		tf.setPlaceholder("Insert credit card number");
		ZenCreditCardFieldFormatter formatter = new ZenCreditCardFieldFormatter();
		formatter.extend(tf);
		formatter.addCreditCardChangedListener(l -> Notification.show("" + l.getCreditCardType()));
		tf.addValueChangeListener(e -> Notification.show("Value: " + e.getValue()));
		return tf;
	}

}
