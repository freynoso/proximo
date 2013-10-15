var map;
var eventSource;
var currentBounds = [];
var devices = {};

function eventHandler(e) {
	var location = jQuery.parseJSON(e.data.replace(/\\"/g, '"'));
	var latLng = new L.LatLng(location.latitude, location.longitude);

	if (devices[location.deviceId].marker === null) {
		initMarker(location.deviceId, latLng);
		if (devices[location.deviceId].show === true) {
			devices[location.deviceId].marker.addTo(map);
		}
	} else {
		devices[location.deviceId].marker.setLatLng(latLng);
	}

}

function initMarker(deviceId, latLng) {
	devices[deviceId].marker = new L.marker(latLng);
	devices[deviceId].marker.on('move', function(e) {
		if (!map.getBounds().contains(e.latlng)) {
			centerMap();
		}
	});
}

function initDevices() {
	$.ajax({
		dataType : "json",
		url : "/devices",
		context : document.body
	}).done(function(remoteDevices) {
		var deviceData = [];
		$.each(remoteDevices, function(key, val) {
			// prepare tree data structure
			deviceData.push({
				"key" : val.name,
				"name" : val.id
			});

			// add event listener
			eventSource.addEventListener(val.id, eventHandler);

			// fill show devices var
			devices[val.id] = {
				"name" : val.name,
				"uniqueId" : val.uniqueId,
				"show" : true,
				"marker" : null
			};
		});

		var devicesTreeData = [];
		devicesTreeData.push({
			"key" : "Devices",
			"values" : deviceData
		});
		$('.listTree').listTree(devicesTreeData, {
			"startCollapsed" : true
		});

		bindDevices();
	});
}

function initMap() {
	// set up the map
	map = new L.Map('map');

	// create the tile layer with correct attribution
	var osmUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
	var osmAttrib = 'Map data © OpenStreetMap contributors';
	var osm = new L.TileLayer(osmUrl, {
		minZoom : 2,
		maxZoom : 18,
		attribution : osmAttrib
	});

	map.addLayer(osm);
	map.setView([ 0, 0 ], 2);
	map.on('layeradd', function(e) {
		centerMap();
	});
	map.on('layerremove', function(e) {
		centerMap();
	});

}

function bindDevices() {
	$('input:checkbox').change(function() {
		if (this.name) {
			if (this.checked) {
				eventSource.addEventListener(this.name, eventHandler);
				if (devices[this.name].marker !== null) {
					map.addLayer(devices[this.name].marker);
				}
			} else {
				eventSource.removeEventListener(this.name, eventHandler);
				if (devices[this.name].marker !== null) {
					map.removeLayer(devices[this.name].marker);
				}
			}
		}
	});
}

function centerMap() {

	var currentMarkersLatLong = [];

	$.each(devices, function(key, device) {
		if (device.show === true && device.marker !== null) {
			currentMarkersLatLong.push(device.marker.getLatLng());
		}
	});

	var latLngBounds = new L.LatLngBounds(currentMarkersLatLong);
	latLngBounds.pad(0.10);
	map.fitBounds(latLngBounds);

}

// https://github.com/twbs/bootstrap/issues/1671
$(window).resize(
		function() {
			$('#map').height(
					$(window).height() - $(".navbar").height()
							- parseInt($(".navbar").css("margin-bottom"))
							- parseInt($(".navbar").css("margin-top"))
							- parseInt($('#map').css("margin-top"))
							- parseInt($('#map').css("margin-bottom")) - 2);

		})
$(window).resize();

if (typeof (EventSource) !== "undefined") {
	eventSource = new EventSource("/devices/lastPosition");
} else {
	alert("No SSE!");
}

initDevices();
initMap();
