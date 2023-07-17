import React, { useEffect, useState } from 'react'
import RestaurantCard from '@/components/Elements/RestaurantCard'
import { MyContainer } from '@/components/Elements/utils/styles'
import SearchBox from '@/components/Elements/SearchBox'
import { useRestaurants } from '@/hooks/Restaurants/useRestaurants'
import type Restaurant from '@/types/models/Restaurant'

function Restaurants (): JSX.Element {
  const { isLoading, restaurants } = useRestaurants()
  const [restaurantList, setRestaurantList] = useState<Restaurant[]>([])

  useEffect(() => {
    restaurants().then((response) => {
      console.log(response.data as Restaurant[])
      setRestaurantList(response.data as Restaurant[])
      console.log(response.data.length)
    }).catch((e) => {
      console.log(e.response)
    })
  }, [])

  return (
        <MyContainer>
            <SearchBox/>
            {isLoading
              ? (
                    <div>Loading..</div>
                )
              : (
                  restaurantList.map((restaurant: Restaurant) => (
                        <RestaurantCard key={restaurant.self} name={restaurant.name}/>
                  ))
                )}
        </MyContainer>
  )
}

export default Restaurants
