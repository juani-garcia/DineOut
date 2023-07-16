import React from 'react'
import { GoogleMap, InfoWindowF, MarkerF } from '@react-google-maps/api'
import RestaurantMapCard from '@/components/Elements/RestaurantMapCard'
import type Restaurant from '@/types/models/Restaurant'

interface RestaurantsMapProps {
  restaurantList: Restaurant[] // Add the restaurantList prop
}

function RestaurantsMap ({ restaurantList }: RestaurantsMapProps): JSX.Element {
  // const gmapsApiKey = process.env.REACT_APP_GOOGLE_MAPS_API_KEY ?? ''

  const containerStyle = {
    width: '90%',
    height: '901px',
    boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',
    borderRadius: '24px'
  }

  const center = {
    lat: -34.587608, // Set the initial center of the map here
    lng: -58.437748
  }

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
            center={center}
            zoom={12}
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
                    <RestaurantMapCard name={selectedRestaurant.name}/>
                </InfoWindowF>
            )}
        </GoogleMap>
  )
}

export default RestaurantsMap
