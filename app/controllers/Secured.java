package controllers;

import models.User;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("login");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.Application.login());
	}

	// Access rights
	public boolean canAccess(Long deviceID) {
		return User.canAccess(
				User.findByLogin(Context.current().request().username()).id,
				deviceID);
	}

}
