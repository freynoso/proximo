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

import java.util.ArrayList;
import java.util.List;

import models.Device;
import models.Listener;
import models.MapEventSource;
import models.User;
import play.Logger;
import play.libs.EventSource;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import akka.actor.ActorRef;

@Security.Authenticated(Secured.class)
public class Devices extends Controller {

	final static ActorRef mapEventSource = MapEventSource.instance;

	@BodyParser.Of(BodyParser.Json.class)
	public static Result index() {
		return ok(Json.toJson(User.findByLogin(request().username()).devices));
	}

	public static Result lastPosition() {
		final List<Device> devices = User.findByLogin(request().username()).devices;
		final List<Long> devicesIds = new ArrayList<Long>();

		for (Device device : devices) {
			devicesIds.add(device.id);
		}

		Logger.info("Client suscribe to: " + Json.toJson(devicesIds).toString());
		EventSource eventSource = new EventSource() {
			public void onConnected() {
				mapEventSource.tell(new Listener(devicesIds, this), null);
			}
		};
		return ok(eventSource);
	}

}
