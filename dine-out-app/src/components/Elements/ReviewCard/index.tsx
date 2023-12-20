import React, { useEffect, useState } from 'react'
import type Restaurant from '@/types/models/Restaurant'
import { enqueueSnackbar } from 'notistack'
import { useTranslation } from 'react-i18next'
import { HttpStatusCode } from 'axios'
import {
  ActionButtonsContainer,
  Address, CardContent, CardImage, CardImageContainer, DeleteButton,
  Detail,
  Name,
  NameAndZone, ReservationWhiteBoxContainer
} from '@/components/Elements/ReservationCard/styles'
import DeleteIcon from '@mui/icons-material/Delete'
import ConfirmationModal from '@/components/Elements/ConfirmationModal'
import useRestaurantFromUri from '@/hooks/Restaurants/useRestaurantFromUri'
import type Review from '@/types/models/Review'
import { Rating, RatingContainer } from '@/components/Elements/RestaurantCard/styles'
import StarIcon from '@mui/icons-material/Star'
import StarBorderIcon from '@mui/icons-material/StarBorder'
import { useDeleteReview } from '@/hooks/Reviews/useDeleteReview'

interface ReviewCardProps {
  review: Review
  deleteCallback: (r: Review) => void
  forUser: boolean
}

const ReservationCard = ({ forUser, deleteCallback, review }: ReviewCardProps): React.JSX.Element => {
  const { t } = useTranslation()
  const [restaurant, setRestaurant] = useState<Restaurant | null>(null)
  const { isLoading: isLoadingRestaurant, requestRestaurant } = useRestaurantFromUri()
  const [isDeleteModalOpen, setDeleteModalOpen] = useState<boolean>(false)
  const { isLoading: isLoadingDelete, deleteReview } = useDeleteReview()

  useEffect(() => {
    requestRestaurant(review.restaurant)
      .then(response => {
        if (response.status === HttpStatusCode.Ok) {
          setRestaurant(response.data as Restaurant)
        } else {
          enqueueSnackbar(t('Errors.oops'), {
            variant: 'error'
          })
        }
      })
      .catch(() => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
      })
      .finally(() => {
        setDeleteModalOpen(false)
      })
  }, [])

  const openDeleteModal = (): void => {
    setDeleteModalOpen(true)
  }

  const closeDeleteModal = (): void => {
    setDeleteModalOpen(false)
  }

  const onDeleteReview = (): void => {
    deleteReview(review.self)
      .then(response => {
        if (response.status === HttpStatusCode.Ok || response.status === HttpStatusCode.NoContent) {
          enqueueSnackbar(t('Review.deleteSuccessful'), {
            variant: 'default'
          })
          deleteCallback(review)
        }
      })
      .catch(() => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
      }).finally(() => {
        closeDeleteModal()
      })
  }

  return (
    <>
      <ReservationWhiteBoxContainer>
        {!isLoadingRestaurant && restaurant?.image != null && restaurant.image !== ''
          ? (
            <CardImageContainer className="card-image">
              <CardImage src={restaurant.image}/>
            </CardImageContainer>
            )
          : (
            <></>
            )
        }
          <CardContent className="card-content">
            {restaurant !== null
              ? (
                <>
                  <NameAndZone>
                    <Name className={'restaurant-name'}>{restaurant.name}</Name>
                  </NameAndZone>
                  <Detail>
                    {restaurant.detail}
                  </Detail>
                  <Address>
                    {restaurant.address}
                  </Address>
                </>
                )
              : (
                <>
                </>
                )}
            <>
              <RatingContainer>
                <Rating>
                  {[...Array(review.rating)].map((_, index) => (
                      <StarIcon key={index} color="secondary"/>
                  ))}
                  {[...Array(5 - review.rating)].map((_, index) => (
                      <StarBorderIcon key={index} color="primary"/>
                  ))}
                </Rating>
              </RatingContainer>
              <Detail>
                {review.review}
              </Detail>
            </>
            { forUser
              ? (
                <ActionButtonsContainer>
                  <DeleteButton
                    className="delete-button"
                    onClick={openDeleteModal}
                    disabled={isLoadingDelete}>
                    <DeleteIcon/>
                  </DeleteButton>
                  <ConfirmationModal
                    title={t('Review.confirmDelete')}
                    open={isDeleteModalOpen}
                    onClose={closeDeleteModal}
                    onConfirm={onDeleteReview}/>
                </ActionButtonsContainer>
                )
              : (
                <></>
                )
            }
          </CardContent>
      </ReservationWhiteBoxContainer>
    </>
  )
}

export default ReservationCard
