import React from 'react'
import {
  CardContent,
  CardImage,
  CardImageContainer,
  RestaurantMapBoxContainer
} from '@/components/Elements/RestaurantMapCard/styles'

function RestaurantMapCard (props: any): JSX.Element {
  return (
        <RestaurantMapBoxContainer>
            <CardImageContainer className="card-image">
                <CardImage src={'http://pawserver.it.itba.edu.ar/paw-2022a-10/image/1'}/>
            </CardImageContainer>
            <CardContent className="card-content">{props.name}</CardContent>
        </RestaurantMapBoxContainer>
  )
}

export default RestaurantMapCard
