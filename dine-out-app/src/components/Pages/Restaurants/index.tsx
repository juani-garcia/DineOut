import React, { useEffect, useState } from 'react'
import { MyContainer } from '@/components/Elements/utils/styles'
import SearchBox from '@/components/Elements/SearchBox'
import { useRestaurants } from '@/hooks/Restaurants/useRestaurants'
import type Restaurant from '@/types/models/Restaurant'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { CircularProgress } from '@mui/material'
import CustomGMapScriptLoad from '@/components/Elements/CustomGMapScriptLoad/CustomGMapScriptLoad'
import Error from '@/components/Pages/Error'
import { useSnackbar } from 'notistack'
import { useTranslation } from 'react-i18next'
import { DineOutHeaders } from '@/common/const'
import { HttpStatusCode } from 'axios'
import RestaurantCardLayout from '@/components/Elements/RestaurantCardLayout'

function Restaurants (): JSX.Element {
  const { t } = useTranslation()
  const { isLoading, restaurants } = useRestaurants()
  const [restaurantList, setRestaurantList] = useState<Restaurant[]>([])
  const [queryParams, setQueryParams] = useSearchParams()
  const [totalPages, setTotalPages] = useState(1)
  const navigate = useNavigate()
  const [error, setError] = useState<number | null>(null)
  const { enqueueSnackbar } = useSnackbar()

  useEffect(() => {
    restaurants(queryParams).then((response) => {
      if (response.status >= 500) {
        setRestaurantList([])
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
        setRestaurantList([])
      } else {
        setRestaurantList(response.data as Restaurant[])
      }
    }).catch((e) => {
      enqueueSnackbar(t('Errors.oops'), {
        variant: 'error'
      })
    })
  }, [queryParams])

  if (error !== null) return <Error errorProp={error}/>

  const handlePageChange = (event: React.ChangeEvent<unknown>, page: number): void => {
    const existingParams = new URLSearchParams(queryParams.toString())
    existingParams.set('page', String(page))
    setQueryParams(existingParams)
  }

  return (
        <MyContainer>
            <SearchBox/>
            <CustomGMapScriptLoad>
                {isLoading
                  ? (
                    <CircularProgress color="secondary" size="100px"/>
                    )
                  : (
                      <RestaurantCardLayout
                        restaurantList={restaurantList}
                        totalPages={totalPages}
                        currentPage={Number((queryParams.get('page') !== '') ? queryParams.get('page') : 1)}
                        pageChangeCallback={handlePageChange} />
                    )
                }
            </CustomGMapScriptLoad>
        </MyContainer>
  )
}

export default Restaurants
