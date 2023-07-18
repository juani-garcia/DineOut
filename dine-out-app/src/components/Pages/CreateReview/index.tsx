import { Button, MyContainer, Title } from '@/components/Elements/utils/styles'
import React from 'react'
import { useTranslation } from 'react-i18next'
import { Header, ReviewForm, ReviewWhiteBoxContainer } from './styles'
import { FormControl, Rating, TextField } from '@mui/material'
import { Controller, useForm } from 'react-hook-form'

// interface ReviewCreationForm {
//   review: string
//   rating: number
// }

export default function Review (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()

  const onSubmit = (data: any): void => {
    console.log(data)
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
                            <Button color="error">
                                {t('Review.cancel')}
                            </Button>
                            <Button type="submit" color="primary">
                                {t('Review.submit')}
                            </Button>
                        </div>
                    </FormControl>
                </ReviewForm>
            </ReviewWhiteBoxContainer>
        </MyContainer>
  )
}
