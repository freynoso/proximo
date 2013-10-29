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
import java.util.List;
import java.util.Map;

import models.Device;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		InitialData.insert(app);
	}

	static class InitialData {

		public static void insert(Application app) {
			if (Ebean.find(User.class).findRowCount() == 0) {

				@SuppressWarnings("unchecked")
				Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml
						.load("initial-data.yml");

				// Insert devices
				Ebean.save(all.get("devices"));

				// Insert users
				Ebean.save(all.get("users"));

				// Insert users*-*devices
				for (Object usersDevices : all.get("usersDevices")) {

					@SuppressWarnings("unchecked")
					Map<String, Object> usersDevicesMap = (Map<String, Object>) usersDevices;
					User user = User.find.byId(Long
							.valueOf((Integer) usersDevicesMap.get("userId")));

					@SuppressWarnings("unchecked")
					List<Object> devicesIds = (List<Object>) usersDevicesMap
							.get("devicesIds");
					for (Object deviceId : devicesIds) {
						Device device = Device.find.byId(Long
								.valueOf((Integer) deviceId));
						user.devices.add(device);
					}
					user.saveManyToManyAssociations("devices");

				}

			}
		}

	}

}