import React, { useEffect, useState } from 'react'
import {
  ExploreRestaurantsText,
  NoReservationsText,
  ReservationCardContainer,
  ReservationCardHolder, ReservationCardsContainer,
  ReservationMainContainer,
  ReservationTitle
} from '@/components/Pages/Reservations/styles'
import { Link, useNavigate, useSearchParams } from 'react-router-dom'
import { useAuth } from '@/hooks/auth/useAuth'
import Error from '@/components/Pages/Error'
import { CircularProgress, Pagination } from '@mui/material'
import { useSnackbar } from 'notistack'
import { useTranslation } from 'react-i18next'
import { DineOutHeaders, localPaths, roles } from '@/common/const'
import type Review from '@/types/models/Review'
import { useReviews } from '@/hooks/Reviews/useReviews'
import { HttpStatusCode } from 'axios'
import ReviewCard from '@/components/Elements/ReviewCard'
import { MySpecialContainer, MyVerySpecialContainer } from '@/components/Pages/Reviews/styles'

function Reviews (): JSX.Element {
  const { t } = useTranslation()
  const [reviewList, setReviewList] = useState<Review[]>([])
  const [queryParams, setQueryParams] = useSearchParams()
  const [totalPages, setTotalPages] = useState(1)
  const navigate = useNavigate()
  const [error, setError] = useState<number | null>(null)
  const { isLoading, reviews } = useReviews()
  const { user } = useAuth()
  const { enqueueSnackbar } = useSnackbar()

  useEffect(() => {
    if (user === null) {
      navigate('/login', {
        state: { from: localPaths.REVIEWS }
      })
    } else {
      const existingParams = new URLSearchParams(queryParams.toString())

      if (user?.roles.includes(roles.RESTAURANT)) {
        if (user?.restaurantId === undefined) {
          navigate('/restaurant/register', {
            state: { from: localPaths.REVIEWS }
          })
          return
        }
        setQueryParams({
          page: existingParams.get('page') ?? '1',
          // TODO: Check: byUser: user?.userId.toString() ?? '',
          forRestaurant: user?.restaurantId.toString() ?? ''
        })
      } else {
        setQueryParams({
          page: existingParams.get('page') ?? '1',
          byUser: user?.userId.toString() ?? ''
        })
      }
    }
  }, [user])

  useEffect(() => {
    if (queryParams.has('byUser') || queryParams.has('forRestaurant')) {
      reviews(queryParams).then((response) => {
        if (response.status >= 400 && user !== null && user.roles.includes(roles.RESTAURANT)) {
          navigate('/restaurant/register', {
            state: { from: localPaths.REVIEWS }
          })
        }

        if (response.status >= 500) {
          setReviewList([])
          navigate('/error?status=' + response.status.toString())
        }

        if (response.status >= 400) {
          setError(response.status)
          return
        }

        setTotalPages(Number(response.headers[DineOutHeaders.TOTAL_PAGES_HEADER]))
        if (response.status === HttpStatusCode.NoContent) {
          const existingParams = new URLSearchParams(queryParams.toString())
          existingParams.set('page', '1')
          setQueryParams(existingParams)
          setReviewList([])
        } else {
          setReviewList(response.data as Review[])
        }
      }).catch(e => {
        enqueueSnackbar(t('Errors.oops'), {
          variant: 'error'
        })
        console.log(e)
      })
    }
  }, [queryParams])

  const onDeleteReview = (deleted: Review): void => {
    const newList = reviewList.filter(review => review.id !== deleted.id)
    setReviewList(newList)
  }

  if (error !== null) return <Error errorProp={error}/>

  const handlePageChange = (event: React.ChangeEvent<unknown>, page: number): void => {
    const existingParams = new URLSearchParams(queryParams.toString())
    existingParams.set('page', String(page))
    setQueryParams(existingParams)
  }

  return (
    <MySpecialContainer>
      <ReservationMainContainer>
        <ReservationCardHolder>
          <ReservationCardContainer>
            <ReservationTitle>
              { t('Reviews.title') }
            </ReservationTitle>
          </ReservationCardContainer>
        </ReservationCardHolder>
      </ReservationMainContainer>
      <ReservationCardsContainer>
        {isLoading
          ? (
            <CircularProgress color="secondary" size="100px"/>
            )
          : (
              reviewList.length === 0
                ? (
                      <ReservationMainContainer>
                        <ReservationCardHolder>
                          <ReservationCardContainer>
                            <NoReservationsText>
                              {t('Reviews.empty')}
                            </NoReservationsText>
                            {((user?.roles.includes(roles.DINER)) === true)
                              ? (
                                <ExploreRestaurantsText as={Link} to={localPaths.RESTAURANTS}>
                                  {t('Restaurant.exploreRestaurants')}
                                </ExploreRestaurantsText>
                                )
                              : (
                                <></>
                                )
                            }
                          </ReservationCardContainer>
                        </ReservationCardHolder>
                      </ReservationMainContainer>
                  )
                : (
                    <>
                      <MyVerySpecialContainer>
                      {reviewList.map((review) => (
                        <ReviewCard
                            key={review.self}
                            review={review}
                            deleteCallback={onDeleteReview}
                            forUser={user?.roles != null
                              ? user.roles.includes(roles.DINER)
                              : false }/>
                      ))}
                      </MyVerySpecialContainer>
                      {totalPages > 1
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
                            }}/>
                          )
                        : (
                          <></>
                          )}
                    </>
                  )
            )}
      </ReservationCardsContainer>
    </MySpecialContainer>
  )
}

export default Reviews
