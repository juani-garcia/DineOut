import React from 'react'
import RestaurantCard from '@/components/Elements/RestaurantCard'
import { MyContainer } from '@/components/Elements/utils/styles'
import SearchBox from '@/components/Elements/SearchBox'

function Restaurants (): JSX.Element {
  return (
        <MyContainer>
            <SearchBox/>
            <RestaurantCard/>
        </MyContainer>
  )
}

export default Restaurants
