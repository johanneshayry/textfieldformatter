package org.vaadin.textfieldformatter;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class BasicCreditCardFieldFormatterUsageUI extends AbstractTest {

	@Override
	public Component getTestComponent(UITestConfiguration configuration) {
		TextField tf = new TextField();
		tf.setPlaceholder("Insert credit card number");
		tf.addValueChangeListener(e -> Notification.show("Value: " + e.getValue()));
		CreditCardFieldFormatter formatter = new CreditCardFieldFormatter();
		formatter.extend(tf);
		formatter.addCreditCardChangedListener(l -> Notification.show("" + l.getCreditCardType()));
		return tf;
	}

}
