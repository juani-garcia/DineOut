import React from 'react'
import {
  CardContent,
  CardImage,
  CardImageContainer,
  RestaurantWhiteBoxContainer
} from '@/components/Elements/RestaurantCard/styles'

function RestaurantCard (): JSX.Element {
  return (
        <RestaurantWhiteBoxContainer>
            <CardImageContainer className="card-image">
                <CardImage src={'http://pawserver.it.itba.edu.ar/paw-2022a-10/image/1'}/>
            </CardImageContainer>
            <CardContent className="card-content">Fiumba</CardContent>
        </RestaurantWhiteBoxContainer>
  )
}

export default RestaurantCard
