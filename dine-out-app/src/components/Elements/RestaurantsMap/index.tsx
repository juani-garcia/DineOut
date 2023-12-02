import React, { useRef } from 'react'
import { GoogleMap, InfoWindowF, MarkerF } from '@react-google-maps/api'
import RestaurantMapCard from '@/components/Elements/RestaurantMapCard'
import type Restaurant from '@/types/models/Restaurant'

interface RestaurantsMapProps {
  restaurantList: Restaurant[] // Add the restaurantList prop
}

function RestaurantsMap ({ restaurantList }: RestaurantsMapProps): JSX.Element {
  const mapRef = useRef<google.maps.Map | null>(null)

  const containerStyle = {
    width: '100%',
    height: '901px',
    boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',
    borderRadius: '24px'
  }

  const center = new google.maps.LatLng({
    lat: -34.587608, // Set the initial center of the map here
    lng: -58.437748
  })

  const [selectedRestaurant, setSelectedRestaurant] = React.useState<Restaurant | null>(null)

  const handleMarkerClick = (restaurant: Restaurant): void => {
    setSelectedRestaurant(restaurant)
  }

  const handleCloseInfoWindow = (): void => {
    setSelectedRestaurant(null)
  }

  return (
        <GoogleMap
            mapContainerStyle={containerStyle}
            zoom={12}
            onLoad={(map) => {
              mapRef.current = map
              map.setCenter(center)
            }}
            onClick={handleCloseInfoWindow}
        >
            {
                restaurantList.map((restaurant: Restaurant) => (
                        <MarkerF
                            key={restaurant.self} // Use a unique key for each marker
                            position={{
                              lat: parseFloat(restaurant.lat),
                              lng: parseFloat(restaurant.lng)
                            }} // Convert lat and lng to float values
                            onClick={() => {
                              handleMarkerClick(restaurant)
                            }}
                            animation={window.google.maps.Animation.DROP}
                        />
                )
                )
            }
            {(selectedRestaurant != null) && (
                <InfoWindowF
                    position={{
                      lat: parseFloat(selectedRestaurant.lat),
                      lng: parseFloat(selectedRestaurant.lng)
                    }}
                    onCloseClick={handleCloseInfoWindow}
                >
                    <RestaurantMapCard restaurant={selectedRestaurant}/>
                </InfoWindowF>
            )}
        </GoogleMap>
  )
}

export default RestaurantsMap
