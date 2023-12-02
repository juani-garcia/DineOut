import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
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
import { useTranslation } from 'react-i18next'
import { useSnackbar } from 'notistack'
import { useAuth } from '@/hooks/auth/useAuth'

interface RestaurantProps {
  restaurant?: Restaurant
}

export default function RestaurantDetailPage ({ restaurant: restaurantProp }: RestaurantProps): JSX.Element {
  const { t } = useTranslation()
  const { isLoading, restaurant: requestRestaurant } = useRestaurant()
  const [restaurant, setRestaurant] = useState<Restaurant>()
  const params = useParams()
  const [error, setError] = useState<number | null>(null)
  const location = useLocation()
  const navigate = useNavigate()
  const { enqueueSnackbar } = useSnackbar()
  const { user } = useAuth()
  const isOwner = Boolean(user?.restaurantId === restaurant?.id)

  useEffect(() => {
    if (restaurant != null && restaurant.id === parseInt(params.id as string)) {
      return
    } else if (location.state?.restaurant !== undefined && location.state?.restaurant != null && location.state?.restaurant.id === parseInt(params.id as string)) {
      setRestaurant(location.state.restaurant)
      return
    } else if (restaurantProp?.id !== undefined && restaurantProp.id === parseInt(params.id as string)) {
      setRestaurant(restaurantProp)
      return
    }
    requestRestaurant(Number(params.id)).then(response => {
      if (response.status >= 500) {
        setRestaurant(undefined)
        navigate('/error?status=' + response.status.toString())
      }

      if (response.status >= 400) {
        setRestaurant(undefined)
        setError(response.status)
        return
      }
      setRestaurant(response.data as Restaurant)
    }).catch(e => {
      enqueueSnackbar(t('Errors.oops'), {
        variant: 'error'
      })
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
                <Menu menuSectionsURI={restaurant.menuSections} editable={isOwner}/>
            </MenuContainer>
        </RestaurantContainer>
  )
}
