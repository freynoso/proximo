package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "devices")
public class Device extends Model {

	private static final long serialVersionUID = 1;

	@Id
	public Long id;

	@Column(unique = true)
	public String uniqueId;

	public String name;

	public static Finder<Long, Device> find = new Finder<Long, Device>(
			Long.class, Device.class);

}
