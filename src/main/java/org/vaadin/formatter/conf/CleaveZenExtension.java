package org.vaadin.formatter.conf;

import java.lang.ref.WeakReference;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.data.value.HasValueChangeMode;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;

@Tag("textfield-formatter")
@NpmPackage(value = "cleave-zen", version = "0.0.17")
@JsModule("./textfield-formatter.ts")
public abstract class CleaveZenExtension<CONF extends AbstractCleaveZenConfiguration> extends Component {

	private WeakReference<Component> extended;
	private CONF configuration;
	private Registration attachRegistration = null;

	public CleaveZenExtension() {
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
		extended = new WeakReference<>(component);
		component.getElement().appendChild(getElement());
		if (component instanceof HasValueChangeMode) {
			getElement().executeJs("$0.updateValueChangeEvent($1);", this,
					ValueChangeMode.eventForMode(
							((HasValueChangeMode) component).getValueChangeMode(),
							"input")
			);
		}
		getElement().executeJs("$0.updateConf($1, $2);", this, getConfiguration().toJson(), getConfiguration().getFormatType());
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

	protected CONF getConfiguration() {
		if (configuration == null) {
			configuration = createDefaultConfiguration();
		}
		return configuration;
	}
	protected abstract CONF createDefaultConfiguration();
}
