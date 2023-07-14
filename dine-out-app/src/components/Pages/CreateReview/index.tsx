import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { ReviewWhiteBoxContainer, ReviewForm, Header } from './styles'
import { Button, Checkbox, FormControl, FormControlLabel, TextField, InputLabel, Select, MenuItem, ListItemText, SelectChangeEvent, Rating } from '@mui/material'
import { useForm, Controller } from 'react-hook-form'
import internal from 'stream'

interface ReviewCreationForm {
    review: string,
    rating: number
};

export default function Review (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()

  const onSubmit = (data: any) => {
    console.log(data)
  };

  return (
    <MyContainer>
        <Title>{t('Review.create.title')}</Title>
        <ReviewWhiteBoxContainer>
            <ReviewForm onSubmit={handleSubmit(onSubmit)}>
                <Header>{t('Review.create.header')}</Header>
                {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                <FormControl component="form"
                             sx={{
                               '& .MuiButton-root': {
                                 width: '40%'
                               }
                             }}>
                    <Controller control={control} name={"rating"} defaultValue={-1} render={({field: {onChange, value} })=> <Rating
                        name={"rating"}
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
                    <div style={{display: 'flex', justifyContent: 'space-around'}}>
                        <Button variant="contained" color="error">
                            {t('Review.cancel')}
                        </Button>
                        <Button type="submit" variant="contained" color="primary">
                            {t('Review.submit')}
                        </Button>
                    </div>
                </FormControl>
            </ReviewForm>
        </ReviewWhiteBoxContainer>
    </MyContainer>
  )
}
