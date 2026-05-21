package deus.paperwork.gui.printer;

import deus.utils.define_ui.DefineUI;
import deus.utils.define_ui.core.Widget;
import deus.utils.define_ui.integration.ReactScreen;
import deus.utils.define_ui.widgets.ContainerWidget;
import deus.utils.react.State;
import net.minecraft.core.player.inventory.menu.MenuAbstract;

import static deus.utils.define_ui.DefineUI.*;

public class LoginScreen extends ReactScreen {

	private State<String> username;
	private State<String> password;
	private State<String> status;

	public LoginScreen(MenuAbstract menu) {
		super(menu);

		this.username = react.state("");
		this.password = react.state("");
		this.status = react.state("Idle");
	}

	@Override
	protected Widget build() {

		ContainerWidget form = column(
			10,

			label("Login Form")
				.color(0xFFFFFF)
				.shadow()
				.build(),

			divider(120, false)
				.color(0xFF666666)
				.build(),

			row(
				10,
				label("Username:")
					.color(0xFFE0E0E0)
					.build(),

				label(username)
					.color(0xFF00FF00)
					.build()
			),

			row(
				10,
				label("Password:")
					.color(0xFFE0E0E0)
					.build(),

				label(password)
					.color(0xFFFF5555)
					.build()
			),

			button("Login", () -> {
				if (username.getVal().isEmpty() || password.getVal().isEmpty()) {
					status.setVal("Missing credentials");
				} else {
					status.setVal("Logged in as " + username.getVal());
				}
			})
				.size(80, 20)
				.build(),

			label(status)
				.color(0xFFFFFF00)
				.build()
		);

		form.x = 20;
		form.y = 20;

		return form;
	}


}
