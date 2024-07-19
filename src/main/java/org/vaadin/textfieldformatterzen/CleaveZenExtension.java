package org.vaadin.textfieldformatterzen;

import java.lang.ref.WeakReference;

import org.vaadin.textfieldformatter.CleaveConfiguration;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.shared.Registration;

@Tag("textfield-formatter")
@NpmPackage(value = "cleave-zen", version = "0.0.17")
@JsModule("./jh-textfield-formatter/textfield-formatter.ts")
public abstract class CleaveZenExtension extends Component {

	private WeakReference<Component> extended;
	private CleaveZenConfiguration configuration;
	private Registration attachRegistration = null;

	public CleaveZenExtension() {
		getConfiguration().copyDelimiter = true;
	}

	protected void extend(Component component) {
		if (!component.getUI().isPresent()) {
			attachRegistration = component.addAttachListener(event -> {
				extend(component, event.getUI());
			});

		} else {
			extend(component, component.getUI().get());
		}
	}

	private void extend(Component component, UI ui) {
		extended = new WeakReference<Component>(component);
		component.getElement().appendChild(getElement());
		getElement().setPropertyJson("conf", getConfiguration().toJson());
	}

	public void remove() {
		if (attachRegistration != null) {
			attachRegistration.remove();
			attachRegistration = null;
		}
		if (extended != null) {
			getElement().removeFromParent();
			extended.clear();
		}
	}

	protected CleaveZenConfiguration getConfiguration() {
		if (configuration == null) {
			configuration = new CleaveZenConfiguration();
		}
		return configuration;
	}
}
