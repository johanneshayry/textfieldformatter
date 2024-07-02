package org.vaadin.textfieldformatter;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

@Route
//@Theme(Lumo.class)
public class MainView extends HorizontalLayout implements RouterLayout {

	private VerticalLayout menu;
	private HasElement content;

	public MainView() {
		menu = new VerticalLayout();
		menu.setSpacing(true);
		menu.setMargin(true);
		setSizeFull();
		add(menu);

		for (RouteData route : RouteConfiguration.forApplicationScope().getAvailableRoutes()) {
			Class<? extends Component> target = route.getNavigationTarget();
			RouteParams routeParams = target.getAnnotation(RouteParams.class);
			if (routeParams != null) {
				for (Class<? extends UITestConfiguration> configuration : routeParams.value()) {
					String param = configuration.getSimpleName();
					String paramLegible = UITestConfiguration.getName(configuration);
					String url = RouteConfiguration.forApplicationScope().getUrl(route.getNavigationTarget());
					Anchor link = new Anchor(url + "/" + param, url + "/" + paramLegible);
					link.getElement().setAttribute("router-link", true);
					menu.add(link);
				}
			} else {
				menu.add(new RouterLink(route.getNavigationTarget()));
			}
		}
	}

	@Override
	public void showRouterLayoutContent(HasElement newContent) {
		if (content != null) {
			content.getElement().removeFromParent();
		}

		getElement().appendChild(newContent.getElement());
		content = newContent;
		setFlexGrow(1, content);
	}
}