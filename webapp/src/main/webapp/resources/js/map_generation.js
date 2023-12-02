function truncateToFloat(value) {
    let multiplier = Math.pow(10, 6 || 0);
    return Math.round(value * multiplier) / multiplier;
}

function initAutocomplete() {
    if (document.getElementById('map') == null) return;
    var map = new google.maps.Map(document.getElementById('map'), {
        center: {
            lat: parseFloat('-34.603722'),
            lng: parseFloat('-58.381592')
        },
        zoom: 11
    });

    if (document.getElementById('pac-input') == null) return;
    var searchBox = new google.maps.places.SearchBox(document.getElementById('pac-input'));
    // map.controls[google.maps.ControlPosition.TOP_CENTER].push(document.getElementById('pac-input'));
    google.maps.event.addListener(searchBox, 'places_changed', function () {
        searchBox.set('map', null);


        var places = searchBox.getPlaces();
        var place = null;
        if (places != null) place = places[0];
        else return;

        let zoneInput = document.getElementById("zone_input");
        if (zoneInput != null) {
            zoneInput.value = place.vicinity;
        }

        let latInput = document.getElementById("lat_input");
        if (latInput != null) {
            latInput.value = truncateToFloat(place.geometry.location.lat());
        }

        let lngInput = document.getElementById("lng_input");
        if (lngInput != null) {
            lngInput.value = truncateToFloat(place.geometry.location.lng());
        }

        var bounds = new google.maps.LatLngBounds();

        var marker = new google.maps.Marker({

            position: place.geometry.location
        });
        marker.bindTo('map', searchBox, 'map');
        google.maps.event.addListener(marker, 'map_changed', function () {
            if (!this.getMap()) {
                this.unbindAll();
            }
        });
        bounds.extend(place.geometry.location);

        map.fitBounds(bounds);
        searchBox.set('map', map);
        map.setZoom(Math.min(map.getZoom(), 14));

    });
}

document.addEventListener('DOMContentLoaded', initAutocomplete);