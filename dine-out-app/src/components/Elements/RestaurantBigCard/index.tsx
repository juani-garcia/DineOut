import React from 'react'
import type Restaurant from '@/types/models/Restaurant'
import { RestaurantBlackBoxContainer, RestaurantTitle } from './styles'
import {
    Rating,
    RatingContainer,
    RatingCount,
    CardImageContainer,
    CardImage,
    ZoneContainer,
    CategoriesContainer,
    CategoriesHolder,
    Category as CategoryChip
} from '@/components/Elements/RestaurantCard/styles'
import StarIcon from '@mui/icons-material/Star'
import StarBorderIcon from '@mui/icons-material/StarBorder'
import Category from '@/types/enums/Category'
import { Shift } from '@/types/enums/Shift'
import { t } from 'i18next'
import RestaurantMenuIcon from '@mui/icons-material/RestaurantMenu'
import { Link } from 'react-router-dom'
import { WhiteButton as Button } from './styles'

interface RestaurantBigCardProps {
  restaurant: Restaurant
}

export default function RestaurantBigCard ({ restaurant }: RestaurantBigCardProps): JSX.Element {
    return (
    <RestaurantBlackBoxContainer style={{ width: '100%', padding: 24, flex: 'column', justifyContent: 'center', alignItems: 'center'}}>
        <RestaurantTitle>{restaurant.name}</RestaurantTitle>
        <RatingContainer>
            <Rating>
                {[...Array(restaurant.rating)].map((_, index) => (
                    <StarIcon key={index} color="secondary"/>
                ))}
                {[...Array(5 - restaurant.rating)].map((_, index) => (
                    <StarBorderIcon key={index} color="primary"/>
                ))}
            </Rating>
            <RatingCount>({restaurant.ratingCount})</RatingCount>
        </RatingContainer>
        <CardImageContainer className="card-image">
                <CardImage src={restaurant.image}/>
        </CardImageContainer>
        <RestaurantMenuIcon fontSize='large' color='primary'/>
        <h1>{restaurant.detail}</h1>
        <ZoneContainer className={'restaurant-zone'}>&#128205;{restaurant.address}</ZoneContainer>
        <CategoriesContainer>
            <CategoriesHolder>
                {
                    restaurant.categories.map(Category.fromName).map(category => (
                        category && <CategoryChip key={category.name}>{category.description}</CategoryChip>
                    ))
                }
            </CategoriesHolder>
        </CategoriesContainer>
        <div>
            <h1>{t('Restaurant.opening-hours')}</h1>
            {
                restaurant.shifts.map(Shift.fromName).map(shift => (
                    shift && <p>{t('Shift.full-description', {
                        description: t(shift.description),
                        startingHour: shift.startingHour,
                        closingHour: shift.closingHour
                    })}</p>
                ))
            }
        </div>
        <div style={{display: 'flex', flexDirection: 'row', justifyContent: 'space-around'}}>
            <Button as={Link} to={`/restaurant/${restaurant.id}/review`}>
                {t('Review.prompt')}
            </Button>
            <Button as={Link} to={`/reserve/${restaurant.id}`}>
                {t('Reservation.prompt')}
            </Button>
        </div>
    </RestaurantBlackBoxContainer>
    )
}
