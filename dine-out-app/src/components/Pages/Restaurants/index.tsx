import React, { useEffect, useState } from 'react'
import RestaurantCard from '@/components/Elements/RestaurantCard'
import { MyContainer } from '@/components/Elements/utils/styles'
import SearchBox from '@/components/Elements/SearchBox'
import { useRestaurants } from '@/hooks/Restaurants/useRestaurants'
import type Restaurant from '@/types/models/Restaurant'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { Button, CircularProgress, Pagination } from '@mui/material'
import {
  NoRestaurantsContainer,
  NoRestaurantsText,
  RestaurantCardsContainer
} from '@/components/Pages/Restaurants/styles'
import RestaurantsMap from '@/components/Elements/RestaurantsMap'
import CustomGMapScriptLoad from '@/components/Elements/CustomGMapScriptLoad/CustomGMapScriptLoad'
import Error from '@/components/Pages/Error'
import { useSnackbar } from 'notistack'
import { useTranslation } from 'react-i18next'
import { DineOutHeaders } from '@/common/const'

function Restaurants (): JSX.Element {
  const { t } = useTranslation()
  const { isLoading, restaurants } = useRestaurants()
  const [restaurantList, setRestaurantList] = useState<Restaurant[]>([])
  const [queryParams, setQueryParams] = useSearchParams()
  const [totalPages, setTotalPages] = useState(1)
  const [useMap, setUseMap] = useState(JSON.parse(localStorage.getItem('useMap') ?? 'false') as boolean)
  const navigate = useNavigate()
  const [error, setError] = useState<number | null>(null)
  const { enqueueSnackbar } = useSnackbar()

  const toggleMap = (): void => {
    localStorage.setItem('useMap', JSON.stringify(!useMap))
    setUseMap(!useMap)
  }

  useEffect(() => {
    restaurants(queryParams).then((response) => {
      if (response.status >= 500) {
        setRestaurantList([])
        navigate('/error?status=' + response.status.toString())
      }

      if (response.status >= 400) {
        setError(response.status)
        return
      }

      setTotalPages(Number(response.headers[DineOutHeaders.TOTAL_PAGES_HEADER]))

      setRestaurantList(response.data as Restaurant[])
    }).catch((e) => {
      enqueueSnackbar(t('Errors.oops'), {
        variant: 'error'
      })
    })
  }, [queryParams])

  if (error !== null) return <Error errorProp={error}/>

  const handlePageChange = (event: React.ChangeEvent<unknown>, page: number): void => {
    const existingParams = new URLSearchParams(queryParams.toString())
    existingParams.set('page', String(page))
    setQueryParams(existingParams)
  }

  return (
        <MyContainer>
            <SearchBox/>
            <CustomGMapScriptLoad>
                {isLoading
                  ? (
                        <CircularProgress color="secondary" size="100px"/>
                    )
                  : (
                      restaurantList.length === 0
                        ? (
                                <NoRestaurantsContainer>
                                    <NoRestaurantsText>
                                        No encontramos ningún restaurante
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
                                                    page={Number((queryParams.get('page') !== '') ? queryParams.get('page') : 1)}
                                                    onChange={handlePageChange}
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
            </CustomGMapScriptLoad>
        </MyContainer>
  )
}

export default Restaurants
