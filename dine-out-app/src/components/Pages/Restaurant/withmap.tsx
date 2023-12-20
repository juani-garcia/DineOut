import { MyContainer } from '@/components/Elements/utils/styles'
import React, { useRef, useState } from 'react'
import { type Zone } from '@/types/enums/Zone'
import CustomGMapScriptLoad from '@/components/Elements/CustomGMapScriptLoad/CustomGMapScriptLoad'
import { GoogleMap, MarkerF } from '@react-google-maps/api'
import type Restaurant from '@/types/models/Restaurant'

// interface RegisterRestaurantFormInput {
//     name: string,
//     image?: string,
//     address: string,
//     email: string,
//     detail: string,
//     categories: Category[],
//     shifts: Shift[]
// }

interface LocationType {
  lat: number
  lng: number
  address: string | undefined
  zone: Zone | undefined
}

interface RestaurantMapProps {
  restaurant: Restaurant
}

export default function WithMap ({ restaurant }: RestaurantMapProps): JSX.Element {
  const [selectedLocation] = useState<LocationType>({
    lat: parseFloat(restaurant.lat),
    lng: parseFloat(restaurant.lng),
    address: undefined,
    zone: undefined
  })
  const mapRef = useRef<google.maps.Map | null>(null)

  const center = new google.maps.LatLng({
    lat: parseFloat(restaurant.lat),
    lng: parseFloat(restaurant.lng)
  })

  const containerStyle = {
    width: '80%',
    height: '350px',
    boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',
    borderRadius: '24px'
  }

  return (
        <MyContainer>
                        <CustomGMapScriptLoad>
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
                        </CustomGMapScriptLoad>
        </MyContainer>
  )
}
