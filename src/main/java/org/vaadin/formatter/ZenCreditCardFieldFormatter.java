package org.vaadin.formatter;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.formatter.conf.CleaveZenExtension;
import org.vaadin.formatter.conf.FormatCreditCardOptions;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.textfield.TextField;

public class ZenCreditCardFieldFormatter extends CleaveZenExtension<FormatCreditCardOptions> {

	private final List<CreditCardChangedListener> listeners = new ArrayList<>();

	public ZenCreditCardFieldFormatter() {
		this(false);
	}

	public ZenCreditCardFieldFormatter(boolean support19DigitPAN) {
		getConfiguration().creditCardStrictMode = support19DigitPAN;
	}

	@Override
	protected FormatCreditCardOptions createDefaultConfiguration() {
		return new FormatCreditCardOptions();
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

	public void addCreditCardChangedListener(CreditCardChangedListener listener) {
		listeners.add(listener);
	}

	@ClientCallable
	public void onCreditCardChanged(String type) {
		final CreditCardType cardType = (type != null) ? CreditCardType.valueOf(type.toUpperCase())
				: CreditCardType.UNKNOWN;
		listeners
				.forEach(l -> l.creditCardChanged(new CreditCardChangedEvent(ZenCreditCardFieldFormatter.this, cardType)));
	}

	public void removeCreditCardChangedListener(CreditCardChangedListener listener) {
		listeners.remove(listener);
	}

	public interface CreditCardChangedListener {
		void creditCardChanged(CreditCardChangedEvent event);
	}

	public class CreditCardChangedEvent {
		private final CreditCardType creditCardType;
		private final ZenCreditCardFieldFormatter source;

		public CreditCardChangedEvent(ZenCreditCardFieldFormatter source, CreditCardType creditCardType) {
			this.source = source;
			this.creditCardType = creditCardType;
		}

		public CreditCardType getCreditCardType() {
			return creditCardType;
		}

		public ZenCreditCardFieldFormatter getSource() {
			return source;
		}
	}

	public enum CreditCardType {
		UNKNOWN, GENERAL, AMEX, MAESTRO, MASTERCARD, VISA, DINERS, DISCOVER, JCB, DANKORT, INSTAPAYMENT, UATP, MIR, UNIONPAY;
	}
}
