import java.util.List;
import java.util.Map;

import models.Device;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Json;
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