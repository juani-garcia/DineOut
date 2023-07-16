import React, { useEffect, useState } from 'react'
import RestaurantCard from '@/components/Elements/RestaurantCard'
import { MyContainer } from '@/components/Elements/utils/styles'
import SearchBox from '@/components/Elements/SearchBox'
import { useRestaurants } from '@/hooks/Restaurants/useRestaurants'
import type Restaurant from '@/types/models/Restaurant'
import { useSearchParams } from 'react-router-dom'
import { Button, CircularProgress, Pagination } from '@mui/material'
import { NoRestaurantsContainer, NoRestaurantsText } from '@/components/Pages/Restaurants/styles'
import RestaurantsMap from '@/components/Elements/RestaurantsMap'
import CustomGMapScriptLoad from '@/components/Elements/CustomGMapScriptLoad/CustomGMapScriptLoad'

function Restaurants (): JSX.Element {
  const { isLoading, restaurants } = useRestaurants()
  const [restaurantList, setRestaurantList] = useState<Restaurant[]>([])
  const [queryParams, setQueryParams] = useSearchParams() // I would add the page in the query params
  const [totalPages, setTotalPages] = useState(1)
  const [useMap, setUseMap] = useState(false)

  const toggleMap = (): void => {
    setUseMap(!useMap)
  }

  useEffect(() => {
    restaurants(queryParams).then((response) => {
      if (response.status !== 200) {
        setRestaurantList([])
        return
      } // TODO: Handle error

      setTotalPages(Number(response.headers['x-total-pages']))
      const existingParams = new URLSearchParams(queryParams.toString())

      if (existingParams.get('page') === null || existingParams.get('page') === '') {
        existingParams.set('page', String(1))
        setQueryParams(existingParams)
      }

      setRestaurantList(response.data as Restaurant[])
    }).catch((e) => {
      console.log(e.response)
    })
  }, [queryParams])

  const handlePageChange = (event: React.ChangeEvent<unknown>, page: number): void => {
    const existingParams = new URLSearchParams(queryParams.toString())
    existingParams.set('page', String(page))
    setQueryParams(existingParams)
  }

  return (
        <MyContainer>
            <CustomGMapScriptLoad>
                <SearchBox/>
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
                                                    {/* TODO: Move things around so the map is not loaded all over again after each pagination attempt */}
                                                    <div style={{ marginBottom: '30px' }}/>
                                                </>
                                            )
                                          : (
                                                <>
                                                    {
                                                        restaurantList.map((restaurant: Restaurant) => (
                                                            <RestaurantCard key={restaurant.self} name={restaurant.detail}/>
                                                        ))
                                                    }
                                                </>
                                            )
                                    }
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
                                </>
                          )
                    )
                }
            </CustomGMapScriptLoad>
        </MyContainer>
  )
}

export default Restaurants
