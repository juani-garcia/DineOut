import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import Menu from '@/components/Elements/Menu'
import type Restaurant from '@/types/models/Restaurant'
import RestaurantBigCard from '@/components/Elements/RestaurantBigCard'
import LoadingCircle from '@/components/Elements/LoadingCircle'
import useRestaurant from '@/hooks/Restaurants/useRestaurant'
import ReviewBigCard from '@/components/Elements/ReviewBigCard'
import Error from '@/components/Pages/Error'

interface RestaurantProps {
  restaurant?: Restaurant
}

export default function RestaurantDetailPage ({ restaurant: restaurantProps }: RestaurantProps): JSX.Element {
  const { isLoading, restaurant: requestRestaurant } = useRestaurant()
  const [restaurant, setRestaurant] = useState<Restaurant>()
  const params = useParams()
  const [error, setError] = useState<number | null>(null)

  useEffect(() => {
    if (restaurant != null) {
      return
    }
    requestRestaurant(Number(params.id)).then(response => {
      if (response.status === 404) {
        setRestaurant(undefined)
        setError(404)
        return
      }
      setRestaurant(response.data as Restaurant)
    }).catch(e => {
      console.error(e.response)
    })
  }, [restaurant, params])

  if (error !== null) return <Error errorProp={error}/>

  if (isLoading || (restaurant == null)) {
    return <LoadingCircle/>
  }
  // isLoading = false, restaurant defined
  return (
        <div style={{ width: '100%', display: 'flex', flexDirection: 'row', justifyContent: 'space-evenly' }}>
            <div style={{ width: '40%' }}>
                <RestaurantBigCard restaurant={restaurant}/>
                <ReviewBigCard reviewListURI={restaurant.reviews}/>
            </div>
            <div style={{ width: '40%' }}>
                <Menu menuSectionsURI={restaurant.menuSections}/>
            </div>
        </div>
  )
}
