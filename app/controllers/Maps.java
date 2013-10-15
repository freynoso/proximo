package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.map;

@Security.Authenticated(Secured.class)
public class Maps extends Controller {

	public static Result index() {
		return ok(map.render());
	}

}
