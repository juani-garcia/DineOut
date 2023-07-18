import React, { useEffect, useState } from 'react'
import type Restaurant from '@/types/models/Restaurant'
import { ReviewBlackBoxContainer, ReviewTitle } from './styles'
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
import { Button, Divider, Pagination } from '@mui/material'
import Review from '@/types/models/Review'
import useReviewList from '@/hooks/Reviews/useReviewList'
import LoadingCircle from '@/components/Elements/LoadingCircle'
import { useSearchParams } from 'react-router-dom'

interface ReviewBigCardProps {
    reviewListURI: string
}

export default function ReviewBigCard ({ reviewListURI: initialreviewListURI }: ReviewBigCardProps): JSX.Element {
    const [reviewListURI, setReviewListURI] = useState<string>(initialreviewListURI)
    const { isLoading, requestReviewList } = useReviewList()
    const [ reviewList, setReviewsList ] = useState<Review[]>([])
    const [totalPages, setTotalPages] = useState<number>(0)

    useEffect(() => {
        if (getPageFromURI(reviewListURI) === 0) {
            setPage(1)
        }
        requestReviewList(reviewListURI).then(response => {
            if (response.status !== 200) {
                setReviewsList([])
                return
            }

            setTotalPages(Number(response.headers['x-total-pages']))
            setReviewsList(response.data as Review[])
        }).catch(e => {
            console.error(e.response)
        })
    }, [reviewListURI])

    const setPage = (page: number): void => {
        const url = new URL(reviewListURI)
        const searchParams = url.searchParams
        searchParams.set('page', String(page))
        setReviewListURI(url.toString())
    }

    const handlePageChange = (event: React.ChangeEvent<unknown>, page: number): void => {
        setPage(page)
    }

    const getPageFromURI = (uri: string): number => {
        return Number(new URL(uri).searchParams.get('page'))
    }

    return (
        <ReviewBlackBoxContainer style={{ width: '100%', padding: 24, flex: 'column'}}>
            <ReviewTitle>{t('Reviews.title')}</ReviewTitle>
            <Divider/>
            {
                !isLoading
                ? 
                    reviewList.length > 0
                    ?
                        <>
                            {reviewList.map(review => (
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
                                    <h1>{review.review}</h1>
                                    <Divider/>
                                </>
                            ))}
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
                        </>
                    : <h1>{t('Reviews.empty')}</h1>
                : <LoadingCircle/>
            }
        </ReviewBlackBoxContainer>
    )
}
