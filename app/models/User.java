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
package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "users")
public class User extends Model {

	private static final long serialVersionUID = 1;

	@Id
	public Long id;

	@Column(unique = true)
	public String login;

	public String password;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Device> devices = new ArrayList<Device>();

	public static Finder<Long, User> find = new Finder<Long, User>(Long.class,
			User.class);

	public static User authenticate(String login, String password) {
		return find.where().eq("login", login).eq("password", password)
				.findUnique();
	}

	public static Boolean canAccess(Long userID, Long deviceID) {
		return find.where().eq("id", userID).eq("device.id", deviceID)
				.findRowCount() > 0;
	}

	public static User findByLogin(String login) {
		return find.where().eq("login", login).findUnique();
	}

}