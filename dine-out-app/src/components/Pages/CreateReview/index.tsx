import { Button, MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useEffect } from 'react'
import { useTranslation } from 'react-i18next'
import { Header, ReviewForm, ReviewWhiteBoxContainer } from './styles'
import { CircularProgress, FormControl, Rating, TextField } from '@mui/material'
import { Controller, useForm } from 'react-hook-form'
import { useAuth } from '@/hooks/auth/useAuth'
import { localPaths, paths, roles } from '@/common/const'
import Error from '@/components/Pages/Error'
import { HttpStatusCode } from 'axios'
import { useNavigate, useParams } from 'react-router-dom'
import { useCreateReview } from '@/hooks/Reviews/useCreateReview'
import { useSnackbar } from 'notistack'

// interface ReviewCreationForm {
//   review: string
//   rating: number
// }

export default function Review (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()
  const { user } = useAuth()
  const params = useParams()
  const navigate = useNavigate()
  const { isLoading, createReview } = useCreateReview()
  const { enqueueSnackbar } = useSnackbar()

  if (user?.roles.includes(roles.DINER) === false) return <Error errorProp={HttpStatusCode.Unauthorized}/>

  useEffect(() => {
    if (user === null) {
      navigate('/login', {
        state: { from: localPaths.RESTAURANTS + '/' + (params.id === undefined ? '' : params.id.toString()) + '/review' }
      })
    }
  }, [user])

  const onSubmit = (data: any): void => {
    // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
    createReview(paths.API_URL + paths.REVIEWS, parseInt(params.id as string), data.rating, data.review).then((response) => {
      if (response.status >= 200 && response.status <= 300) {
        navigate(-1)
      } else {
        enqueueSnackbar(t('Errors.tryAgain'), {
          variant: 'warning'
        })
      }
    }).catch((e) => {
      enqueueSnackbar(t('Errors.oops'), {
        variant: 'error'
      })
    })
  }

  const handleCancel = (): void => {
    navigate('/restaurant/' + (params.id as string) + '/view')
  }

  return (
        <MyContainer>
            <Title>{t('Review.create.title')}</Title>
            <ReviewWhiteBoxContainer>
                {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                <ReviewForm onSubmit={handleSubmit(onSubmit)}>
                    <Header>{t('Review.create.header')}</Header>
                    {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                    <FormControl component="form"
                                 sx={{
                                   '& .MuiButton-root': {
                                     width: '40%'
                                   }
                                 }}>
                        <Controller control={control} name={'rating'} defaultValue={-1}
                                    render={({ field: { onChange, value } }) => <Rating
                                        name={'rating'}
                                        size='large'
                                        onChange={onChange}
                                        value={Number(value)}
                                    />
                                    }/>
                        <TextField
                            label={t('Review.create.review')}
                            fullWidth
                            margin="normal"
                            {...control.register('review')}
                            variant="standard"
                        />
                        <div style={{ display: 'flex', justifyContent: 'space-around' }}>
                            <Button color="error" onClick={handleCancel}>
                                {t('Review.cancel')}
                            </Button>
                            <Button type="submit" color="primary">
                                {
                                    isLoading
                                      ? (
                                            <CircularProgress color="secondary" size="30px"/>
                                        )
                                      : (
                                            <>{t('Review.submit')}</>
                                        )
                                }
                            </Button>
                        </div>
                    </FormControl>
                </ReviewForm>
            </ReviewWhiteBoxContainer>
        </MyContainer>
  )
}
