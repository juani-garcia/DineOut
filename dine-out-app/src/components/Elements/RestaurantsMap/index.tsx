import React from 'react'
import { GoogleMap, InfoWindowF, LoadScript, MarkerF } from '@react-google-maps/api'
import RestaurantCard from '@/components/Elements/RestaurantCard'

function RestaurantsMap (): JSX.Element {
  const gmapsApiKey = process.env.REACT_APP_GOOGLE_MAPS_API_KEY ?? ''

  const containerStyle = {
    width: '90%',
    height: '600px'
  }

  const center = {
    lat: -34.587608, // Set the initial center of the map here
    lng: -58.437748
  }

  const [infoWindowOpen, setInfoWindowOpen] = React.useState(false)

  return (
        <LoadScript googleMapsApiKey={gmapsApiKey}>
            <GoogleMap mapContainerStyle={containerStyle} center={center} zoom={15}>
                <MarkerF
                    position={center}
                    onClick={() => {
                      setInfoWindowOpen(!infoWindowOpen)
                    }}
                />
                {infoWindowOpen && (
                    <InfoWindowF
                        position={center}
                        onCloseClick={() => {
                          setInfoWindowOpen(false)
                        }}
                    >
                        <RestaurantCard/>
                    </InfoWindowF>
                )}
            </GoogleMap>
        </LoadScript>
  )
}

export default RestaurantsMap
