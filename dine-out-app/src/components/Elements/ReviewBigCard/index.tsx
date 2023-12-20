import React, { useEffect, useState } from 'react'
import { ReviewBlackBoxContainer, ReviewContainer, ReviewText, ReviewTitle } from './styles'
import { Rating, RatingContainer } from '@/components/Elements/RestaurantCard/styles'
import StarIcon from '@mui/icons-material/Star'
import StarBorderIcon from '@mui/icons-material/StarBorder'
import { t } from 'i18next'
import { Divider, Pagination } from '@mui/material'
import type Review from '@/types/models/Review'
import useReviewList from '@/hooks/Reviews/useReviewList'
import LoadingCircle from '@/components/Elements/LoadingCircle'
import { DineOutHeaders } from '@/common/const'
import { HttpStatusCode } from 'axios'
import { useSearchParams } from 'react-router-dom'
import { NoReservationsText } from '@/components/Pages/Reviews/styles'

interface ReviewBigCardProps {
  reviewListURI: string
}

export default function ReviewBigCard ({ reviewListURI: initialreviewListURI }: ReviewBigCardProps): JSX.Element {
  const [reviewListURI, setReviewListURI] = useState<string>(initialreviewListURI)
  const { isLoading, requestReviewList } = useReviewList()
  const [reviewList, setReviewsList] = useState<Review[]>([])
  const [totalPages, setTotalPages] = useState<number>(0)
  const [queryParams, setQueryParams] = useSearchParams()

  useEffect(() => {
    if (getPageFromURI(reviewListURI) === 0) {
      setPage(1)
    }
    requestReviewList(reviewListURI).then(response => {
      if (response.status !== 200) {
        setReviewsList([])
        return
      }

      setTotalPages(Number(response.headers[DineOutHeaders.TOTAL_PAGES_HEADER]))
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-expect-error
      if (response.status === HttpStatusCode.NoContent) {
        setPage(1)
        setReviewsList([])
      } else {
        setReviewsList(response.data as Review[])
      }
    }).catch(e => {
      console.error(e.response)
    })
  }, [reviewListURI])

  const setPage = (page: number): void => {
    const url = new URL(reviewListURI)
    const searchParams = url.searchParams
    searchParams.set('page', String(page))
    setReviewListURI(url.toString())
    const existingParams = new URLSearchParams(queryParams.toString())
    existingParams.set('pageReview', String(page))
    setQueryParams(existingParams)
  }

  const handlePageChange = (event: React.ChangeEvent<unknown>, page: number): void => {
    setPage(page)
  }

  const getPageFromURI = (uri: string): number => {
    return Number(new URL(uri).searchParams.get('page'))
  }

  return (
        <ReviewBlackBoxContainer style={{ width: '100%', padding: 24, flex: 'column' }}>
            <ReviewTitle>{t('Reviews.title')}</ReviewTitle>
            <Divider/>
            {
                !isLoading
                  ? (
                      reviewList.length > 0
                        ? <>
                                {reviewList.map(review => (
                                    <>
                                    <ReviewContainer>
                                        <ReviewText>{review.review}</ReviewText>
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
                                    </ReviewContainer>
                                    <Divider/>
                                    </>
                                ))}
                                {
                                    totalPages > 1
                                      ? (
                                            <Pagination
                                                count={totalPages}
                                                page={getPageFromURI(reviewListURI)}
                                                onChange={handlePageChange}
                                                size="large"
                                                showFirstButton
                                                showLastButton
                                                siblingCount={1}
                                                sx={{
                                                  '& .MuiPaginationItem-root': {
                                                    color: '#FFFFFF'
                                                  }
                                                }}
                                            />
                                        )
                                      : (
                                            <></>
                                        )
                                }
                            </>
                        : <NoReservationsText>{t('Reviews.empty')}</NoReservationsText>
                    )
                  : (
                        <LoadingCircle/>
                    )
            }
        </ReviewBlackBoxContainer>
  )
}
