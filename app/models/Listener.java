package models;

import java.util.List;

import play.libs.EventSource;

public class Listener {

	private final List<Long> devicesIds;
	private final EventSource eventSource;

	public Listener(List<Long> devicesIds, EventSource eventSource) {
		this.devicesIds = devicesIds;
		this.eventSource = eventSource;
	}

	public List<Long> getDevicesIds() {
		return devicesIds;
	}

	public EventSource getEventSource() {
		return eventSource;
	}

}
