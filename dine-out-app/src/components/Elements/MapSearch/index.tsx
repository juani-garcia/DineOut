import React, { useRef, useState } from 'react'
import { Autocomplete, GoogleMap, MarkerF } from '@react-google-maps/api'
import { MapSearchContainer } from '@/components/Elements/MapSearch/styles'
import { useTranslation } from 'react-i18next'

interface LocationType {
  lat: number
  lng: number
  address: string | undefined
}

function MapSearch (): JSX.Element {
  const { t } = useTranslation()
  const [selectedLocation, setSelectedLocation] = useState<LocationType>({ lat: 0, lng: 0, address: undefined })
  const autocompleteRef = useRef<google.maps.places.Autocomplete | null>(null)
  const mapRef = useRef<google.maps.Map | null>(null)

  const center = new google.maps.LatLng({
    lat: -34.587608, // Set the initial center of the map here
    lng: -58.437748
  })

  const containerStyle = {
    width: '80%',
    height: '350px',
    boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',
    borderRadius: '24px'
  }

  const onPlaceChanged = (): void => {
    if (autocompleteRef.current == null) return
    const place = autocompleteRef.current.getPlace()
    if (place.geometry != null) {
      const { lat, lng } = place.geometry.location ?? new google.maps.LatLng({ lat: 0, lng: 0 })
      console.log({
        lat: lat(),
        lng: lng(),
        address: place.formatted_address
      })
      setSelectedLocation({
        lat: lat(),
        lng: lng(),
        address: place.formatted_address
      })
      mapRef.current?.setCenter(new google.maps.LatLng({
        lat: lat(),
        lng: lng()
      }))
      mapRef.current?.setZoom(16)
    } else {
      setSelectedLocation({ lat: 0, lng: 0, address: undefined })
    }
  }

  return (
        <MapSearchContainer>
            <Autocomplete
                onLoad={(auto) => (autocompleteRef.current = auto)}
                onPlaceChanged={onPlaceChanged}
            >
                <input style={{
                  boxSizing: 'border-box',
                  border: '1px solid #ccc',
                  borderRadius: '4px',
                  fontSize: '16px',
                  height: '40px',
                  width: '500px',
                  padding: '10px',
                  marginBottom: '21px'
                }} type="text" placeholder={t('MapSearch.location') ?? ''}/>
            </Autocomplete>
            <GoogleMap
                mapContainerStyle={containerStyle}
                onLoad={(map) => {
                  mapRef.current = map
                  map.setCenter(center)
                  map.setZoom(11)
                }}
            >
                <MarkerF
                    position={{
                      lat: selectedLocation.lat,
                      lng: selectedLocation.lng
                    }}
                    animation={window.google.maps.Animation.DROP}
                />
            </GoogleMap>
        </MapSearchContainer>
  )
}

export default MapSearch
