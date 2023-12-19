import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import Menu from '@/components/Elements/Menu'
import type Restaurant from '@/types/models/Restaurant'
import type MenuSection from '@/types/models/MenuSection'
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
import { useUpdateRestaurant } from '@/hooks/Restaurants/useUpdateRestaurant'

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
  const [menuSectionsOrder, setMenuSectionsOrder] = useState<string[]>([])
  const { updateRestaurant } = useUpdateRestaurant()

  const handleSectionDeletion = (menuSection: MenuSection): void => {
    const newMenuSectionsOder = menuSectionsOrder.filter(s => s !== menuSection.self)
    setMenuSectionsOrder(newMenuSectionsOder)
  }

  const handleSectionUp = (menuSection: MenuSection): void => {
    console.log(menuSectionsOrder)
    const newMenuSectionsOrder = [...menuSectionsOrder]
    const currentIndex = menuSectionsOrder.indexOf(menuSection.self)
    console.log('Moving section of position ' + String(currentIndex) + ' up')
    newMenuSectionsOrder[currentIndex - 1] = menuSection.self
    newMenuSectionsOrder[currentIndex] = menuSectionsOrder[currentIndex - 1]
    console.log(newMenuSectionsOrder)
    if (restaurant === null || restaurant === undefined) {
      console.error('No restaurant to update')
      return
    }
    updateRestaurant(restaurant.id, restaurant.name, restaurant.address, restaurant.mail, restaurant.detail, restaurant.zone, Number(restaurant.lat), Number(restaurant.lng), restaurant.categories, restaurant.shifts, newMenuSectionsOrder)
      .then((response) => {
        if (response.status === 200 || response.status === 201) {
          setMenuSectionsOrder(newMenuSectionsOrder)
        } else {
          enqueueSnackbar(t('Errors.tryAgain'), {
            variant: 'warning'
          })
        }
      }
      ).catch(
        (e: any) => {
          enqueueSnackbar(t('Errors.oops'), {
            variant: 'error'
          })
        }
      )
  }

  const handleSectionDown = (menuSection: MenuSection): void => {
    console.log(menuSectionsOrder)
    const newMenuSectionsOrder = [...menuSectionsOrder]
    const currentIndex = menuSectionsOrder.indexOf(menuSection.self)
    console.log('Moving section of position ' + String(currentIndex) + ' down')
    newMenuSectionsOrder[currentIndex + 1] = menuSection.self
    newMenuSectionsOrder[currentIndex] = menuSectionsOrder[currentIndex + 1]
    console.log(newMenuSectionsOrder)
    if (restaurant === null || restaurant === undefined) {
      console.error('No restaurant to update')
      return
    }
    updateRestaurant(restaurant.id, restaurant.name, restaurant.address, restaurant.mail, restaurant.detail, restaurant.zone, Number(restaurant.lat), Number(restaurant.lng), restaurant.categories, restaurant.shifts, newMenuSectionsOrder)
      .then((response) => {
        if (response.status === 200 || response.status === 201) {
          setMenuSectionsOrder(newMenuSectionsOrder)
        } else {
          enqueueSnackbar(t('Errors.tryAgain'), {
            variant: 'warning'
          })
        }
      }
      ).catch(
        (e: any) => {
          enqueueSnackbar(t('Errors.oops'), {
            variant: 'error'
          })
        }
      )
  }

  useEffect(() => {
    if (!Number.isInteger(parseInt(params.id as string))) {
      setError(400)
      return
    }

    if (restaurant != null && restaurant.id === parseInt(params.id as string)) {
      return
    } else if (location.state?.restaurant !== undefined && location.state?.restaurant != null && location.state?.restaurant.id === parseInt(params.id as string)) {
      setRestaurant(location.state.restaurant)
      setMenuSectionsOrder(location.state?.restaurant.menuSectionsOrder)
      return
    } else if (restaurantProp?.id !== undefined && restaurantProp.id === parseInt(params.id as string)) {
      setRestaurant(restaurantProp)
      setMenuSectionsOrder(restaurantProp.menuSectionsOrder)
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
      setMenuSectionsOrder((response.data as Restaurant).menuSectionsOrder)
      if ((response.data as Restaurant).id === undefined || (response.data as Restaurant).id == null) {
        setRestaurant(undefined)
        setError(404)
      }
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
      <>
        {isLoading
          ? (
                <LoadingCircle/>
            )
          : (
                <RestaurantContainer>
                  <RestaurantDetailSection>
                    <RestaurantDetailContainer>
                      <RestaurantBigCard restaurant={restaurant}/>
                      <ReviewBigCard reviewListURI={restaurant.reviews}/>
                    </RestaurantDetailContainer>
                  </RestaurantDetailSection>
                  <MenuContainer>
                    <Menu menuSectionsURI={restaurant.menuSections} editable={isOwner} menuSectionsOrder={menuSectionsOrder} onDelete={handleSectionDeletion} onUp={handleSectionUp} onDown={handleSectionDown}/>
                  </MenuContainer>
                </RestaurantContainer>
            )
        }
      </>
  )
}
