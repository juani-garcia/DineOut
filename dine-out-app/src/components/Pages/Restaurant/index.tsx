import React, { useEffect, useState } from 'react'
import { useLocation, useParams } from 'react-router-dom'
import Menu from '@/components/Elements/Menu'
import type Restaurant from '@/types/models/Restaurant'
import RestaurantBigCard from '@/components/Elements/RestaurantBigCard'
import LoadingCircle from '@/components/Elements/LoadingCircle'
import useRestaurant from '@/hooks/Restaurants/useRestaurant'
import ReviewBigCard from '@/components/Elements/ReviewBigCard'
import Error from '@/components/Pages/Error'
import {
  MenuContainer,
  RestaurantContainer,
  RestaurantDetailContainer,
  RestaurantDetailSection
} from '@/components/Pages/Restaurant/styles'

interface RestaurantProps {
  restaurant?: Restaurant
}

export default function RestaurantDetailPage ({ restaurant: restaurantProps }: RestaurantProps): JSX.Element {
  const { isLoading, restaurant: requestRestaurant } = useRestaurant()
  const [restaurant, setRestaurant] = useState<Restaurant>()
  const params = useParams()
  const [error, setError] = useState<number | null>(null)
  const location = useLocation()

  useEffect(() => {
    if (restaurant != null) {
      return
    } else if (location.state?.restaurant !== undefined && location.state?.restaurant != null) {
      setRestaurant(location.state.restaurant)
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
        <RestaurantContainer>
            <RestaurantDetailSection>
                <RestaurantDetailContainer>
                    <RestaurantBigCard restaurant={restaurant}/>
                    <ReviewBigCard reviewListURI={restaurant.reviews}/>
                </RestaurantDetailContainer>
            </RestaurantDetailSection>
            <MenuContainer>
                <Menu menuSectionsURI={restaurant.menuSections}/>
            </MenuContainer>
        </RestaurantContainer>
  )
}
