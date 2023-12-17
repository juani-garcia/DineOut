import React from 'react'
import {
  CardContent,
  CardImage,
  CardImageContainer,
  RestaurantMapBoxContainer
} from '@/components/Elements/RestaurantMapCard/styles'
import type Restaurant from '@/types/models/Restaurant'
import { Zone } from '@/types/enums/Zone'
import {
  Address,
  CategoriesAndRating,
  CategoriesContainer,
  CategoriesHolder,
  CategoriesTitle,
  Category as CategoryChip,
  Detail,
  Name,
  NameAndZone,
  Rating,
  RatingContainer,
  RatingCount,
  ZoneContainer
} from '@/components/Elements/RestaurantCard/styles'
import StarIcon from '@mui/icons-material/Star'
import StarBorderIcon from '@mui/icons-material/StarBorder'
import { useNavigate } from 'react-router-dom'
import Category from '@/types/enums/Category'
import { useTranslation } from 'react-i18next'

interface RestaurantCardProps {
  restaurant: Restaurant
}

function RestaurantMapCard ({ restaurant }: RestaurantCardProps): JSX.Element {
  if (restaurant == null) return (<></>)
  let restaurantZoneName = null
  const { t } = useTranslation()
  if (restaurant.zone !== null && restaurant.zone !== undefined) {
    restaurantZoneName = Zone.values.find(otherZ => otherZ.name === restaurant.zone)?.description
  }

  const navigate = useNavigate()

  const handleGoToRestaurantView = (): void => {
    navigate('/restaurant/' + restaurant.id.toString() + '/view', { state: { restaurant } })
  }

  const MAX_RATING = 5
  const secondaryColorStars = restaurant.rating
  const primaryColorStars = MAX_RATING - restaurant.rating

  return (
        <RestaurantMapBoxContainer onClick={handleGoToRestaurantView}>
            {restaurant.image == null || restaurant.image === ''
              ? (
                    <></>
                )
              : (
                    <CardImageContainer className="card-image">
                        <CardImage src={restaurant.image}/>
                    </CardImageContainer>
                )
            }
            <CardContent className="card-content">
                {/* Title / zone */}
                <NameAndZone>
                    <Name className={'restaurant-name'}>{restaurant.name}</Name>
                    {restaurantZoneName === null
                      ? <></>
                      : <ZoneContainer className={'restaurant-zone'}>&#128205;{restaurantZoneName}</ZoneContainer>
                    }
                </NameAndZone>
                {/* detail */}
                <Detail>{restaurant.detail}</Detail>
                {/* address */}
                <Address>{restaurant.address}</Address>
                {/* categories list / rating & rating amount */}
                <CategoriesAndRating>
                    <CategoriesContainer>
                        <CategoriesTitle>Categories:</CategoriesTitle>
                        <CategoriesHolder>
                            {
                                restaurant.categories.map(Category.fromName).map(category => (
                                  (category != null) &&
                                    <CategoryChip key={category.name}>{t(category.description)}</CategoryChip>
                                ))
                            }
                        </CategoriesHolder>
                    </CategoriesContainer>
                    <RatingContainer>
                        <Rating>
                            {[...Array(secondaryColorStars)].map((_, index) => (
                                <StarIcon key={index} color="secondary"/>
                            ))}
                            {[...Array(primaryColorStars)].map((_, index) => (
                                <StarBorderIcon key={index} color="primary"/>
                            ))}
                        </Rating>
                        <RatingCount>({restaurant.ratingCount})</RatingCount>
                    </RatingContainer>
                </CategoriesAndRating>
            </CardContent>
        </RestaurantMapBoxContainer>
  )
}

export default RestaurantMapCard
