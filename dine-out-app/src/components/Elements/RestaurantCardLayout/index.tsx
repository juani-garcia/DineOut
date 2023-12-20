import React, { useState } from 'react'
import {
  NoRestaurantsContainer,
  NoRestaurantsText,
  RestaurantCardsContainer
} from '@/components/Elements/RestaurantCardLayout/styles'
import { Button, Pagination } from '@mui/material'
import RestaurantsMap from '@/components/Elements/RestaurantsMap'
import type Restaurant from '@/types/models/Restaurant'
import RestaurantCard from '@/components/Elements/RestaurantCard'
import { useTranslation } from 'react-i18next'

interface RestaurantCardLayoutProps {
  restaurantList: Restaurant[]
  totalPages: number
  currentPage: number
  pageChangeCallback: (event: React.ChangeEvent<unknown>, page: number) => void
}

const RestaurantCardLayout = ({ restaurantList, totalPages, currentPage, pageChangeCallback }: RestaurantCardLayoutProps): React.JSX.Element => {
  const { t } = useTranslation()
  const [useMap, setUseMap] = useState(JSON.parse(localStorage.getItem('useMap') ?? 'false') as boolean)
  const toggleMap = (): void => {
    localStorage.setItem('useMap', JSON.stringify(!useMap))
    setUseMap(!useMap)
  }

  return (
    restaurantList.length === 0
      ? (
        <NoRestaurantsContainer>
          <NoRestaurantsText>
            { t('Restaurants.noRestaurants') }
          </NoRestaurantsText>
        </NoRestaurantsContainer>
        )
      : (
        <>
          <Button onClick={toggleMap}>Toggle map</Button>
          {
            useMap
              ? (
                <>
                  <div style={{ marginTop: '30px' }}/>
                  <RestaurantsMap restaurantList={restaurantList}/>
                  <div style={{ marginBottom: '30px' }}/>
                </>
                )
              : (
                <RestaurantCardsContainer>
                  {
                    restaurantList.map((restaurant: Restaurant) => (
                      <RestaurantCard key={restaurant.self} restaurant={restaurant}/>
                    ))
                  }
                </RestaurantCardsContainer>
                )
          }
          {
            totalPages > 1
              ? (
                <Pagination
                  count={totalPages}
                  page={currentPage}
                  onChange={pageChangeCallback}
                  size="large"
                  showFirstButton
                  showLastButton
                  siblingCount={3}
                  boundaryCount={3}
                  sx={{
                    '& .MuiPaginationItem-root': {
                      color: '#FFFFFF'
                    }
                  }}
                />
                )
              : (
                <></>
                )
          }
        </>
        )
  )
}

export default RestaurantCardLayout
