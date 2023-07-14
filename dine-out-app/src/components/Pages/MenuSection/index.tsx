import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { MenuSectionForm, Header, MenuSectionWhiteBoxContainer } from './styles'
import { Button, Checkbox, FormControl, FormControlLabel, TextField, InputLabel, Select, MenuItem, ListItemText, SelectChangeEvent } from '@mui/material'
import { useForm } from 'react-hook-form'

interface MenuSectionFormInput {
    name: string
};

export default function MenuSectionCreation (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()
  const onSubmit = (data: any) => {
    console.log(data)
  };

  return (
    <MyContainer>
        <Title>{t('MenuSection.form.title')}</Title>
        <MenuSectionWhiteBoxContainer>
            <MenuSectionForm onSubmit={handleSubmit(onSubmit)}>
                <Header>{t('MenuSection.form.header')}</Header>
                {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                <FormControl component="form"
                             sx={{
                               '& .MuiButton-root': {
                                 width: '40%'
                               }
                             }}>
                    <TextField
                        label={t('MenuSection.form.name')}
                        fullWidth
                        margin="normal"
                        {...control.register('name')}
                        variant="standard"
                    />
                    <Button type="submit" variant="contained" color="primary">
                        {t('MenuSection.form.submit')}
                    </Button>
                </FormControl>
            </MenuSectionForm>
        </MenuSectionWhiteBoxContainer>
    </MyContainer>
  )
}
