import React, { useEffect, useState } from 'react'
import RestaurantCard from '@/components/Elements/RestaurantCard'
import { MyContainer } from '@/components/Elements/utils/styles'
import SearchBox from '@/components/Elements/SearchBox'
import { useRestaurants } from '@/hooks/Restaurants/useRestaurants'
import type Restaurant from '@/types/models/Restaurant'
import { useSearchParams } from 'react-router-dom'
import { Pagination } from '@mui/material'

function Restaurants (): JSX.Element {
  const { isLoading, restaurants } = useRestaurants()
  const [restaurantList, setRestaurantList] = useState<Restaurant[]>([])
  const [queryParams, setQueryParams] = useSearchParams() // I would add the page in the query params
  const [totalPages, setTotalPages] = useState(1)

  useEffect(() => {
    restaurants(queryParams).then((response) => {
      if (response.status !== 200) return // TODO: Handle error
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
            <SearchBox/>
            {isLoading
              ? (
                    <div>Loading..</div>
                )
              : (
                  restaurantList.length === 0
                    ? (
                            <>Todo un tema</>)
                    : (
                        restaurantList.map((restaurant: Restaurant) => (
                                <RestaurantCard key={restaurant.self} name={restaurant.detail}/>
                        ))
                      )
                )
            }
            <Pagination
                count={totalPages}
                page={Number((queryParams.get('page') !== '') ? queryParams.get('page') : 1)}
                onChange={handlePageChange}
                size="large"
                showFirstButton
                showLastButton
                sx={{
                  '& .MuiPaginationItem-root': {
                    color: '#FFFFFF'
                  }
                }}
            />
        </MyContainer>
  )
}

export default Restaurants
