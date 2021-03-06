/**
 * 
 *  Copyright 2013 Francisco Omar Reynoso <franole@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package controllers;

import static play.data.Form.form;
import models.MapEventSource;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import akka.actor.ActorRef;

public class Application extends Controller {

	final static ActorRef mapEventSource = MapEventSource.instance;

	/**
	 * Login page.
	 */
	public static Result login() {
		return ok(login.render(form(Login.class)));
	}

	/**
	 * Handle login form submission.
	 */
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			// TODO improve security for stateless server
			// http://security.stackexchange.com/questions/30707/demystifying-web-authentication-stateless-session-cookies
			// http://stackoverflow.com/questions/9416964/where-is-session-id-in-play-2-0
			// http://stackoverflow.com/questions/4495950/how-do-stateless-servers-work
			// http://www.javacodegeeks.com/2012/04/dzoneprotect-rest-service-using-hmac.html
			// http://appsandsecurity.blogspot.com.ar/2011/04/rest-and-stateless-session-ids.html
			session("login", loginForm.get().login);
			return redirect(routes.Maps.index());
		}
	}

	/**
	 * Logout and clean the session.
	 */
	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.Application.login());
	}

	/**
	 * Login authentication
	 */
	public static class Login {

		public String login;
		public String password;

		public String validate() {
			if (User.authenticate(login, password) == null) {
				return "Invalid login name or password";
			}
			return null;
		}

	}

}
