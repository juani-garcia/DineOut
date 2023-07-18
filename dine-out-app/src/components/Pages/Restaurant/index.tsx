import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import Menu from '@/components/Elements/Menu'
import Restaurant from '@/types/models/Restaurant'
import RestaurantBigCard from '@/components/Elements/RestaurantBigCard'
import LoadingCircle from '@/components/Elements/LoadingCircle'
import useRestaurant from '@/hooks/Restaurants/useRestaurant'
import ReviewBigCard from '@/components/Elements/ReviewBigCard'

interface RestaurantProps {
  restaurant?: Restaurant
}

export default function RestaurantDetailPage ({ restaurant: restaurantProps }: RestaurantProps): JSX.Element {
  const { isLoading, restaurant: requestRestaurant } = useRestaurant()
  const [ restaurant, setRestaurant ] = useState<Restaurant>()
  const params = useParams()

  useEffect(() => {
    if (restaurant) {
      return
    }
    requestRestaurant(Number(params.id)).then(response => {
      if (response.status === 404) {
        // TODO: go to error page
        setRestaurant(undefined)
        return
      }
      setRestaurant(response.data as Restaurant)
    }).catch(e => {
      console.error(e.response)
    })
  }, [restaurant, params])

  if (isLoading || !restaurant) {
    return <LoadingCircle/>
  }
  // isLoading = false, restaurant defined
  return (
    <div style={{width: '100%', display: 'flex', flexDirection: 'row', justifyContent: 'space-evenly'}}>
      <div style={{width: '40%'}}>
        <RestaurantBigCard restaurant={restaurant}/>
        <ReviewBigCard reviewListURI={restaurant.reviews}/>
      </div>
      <div style={{width: '40%'}}>
        <Menu menuSectionsURI={restaurant.menuSections} />
      </div>
    </div>
  ) // TODO: Check errors, 404, 403, etc.
}
