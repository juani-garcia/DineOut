import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React from 'react'
import { useTranslation } from 'react-i18next'
import { Header, MenuItemForm, MenuItemWhiteBoxContainer } from './styles'
import { Button, FormControl, MenuItem, Select, TextField } from '@mui/material'
import { Controller, useForm } from 'react-hook-form'

// interface MenuItemFormInput {
//   name: string
//   detail: string
//   price: number
//   section: string
//   image?: string
// }

const sections = [
  'a',
  'b',
  'c'
]

export default function MenuItemCreation (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()
  const onSubmit = (data: any) => {
    console.log(data)
  }

  return (
        <MyContainer>
            <Title>{t('MenuItem.form.title')}</Title>
            <MenuItemWhiteBoxContainer>
                <MenuItemForm onSubmit={handleSubmit(onSubmit)}>
                    <Header>{t('MenuItem.form.header')}</Header>
                    {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                    <FormControl component="form"
                                 sx={{
                                   '& .MuiButton-root': {
                                     width: '40%'
                                   }
                                 }}>
                        <TextField
                            label={t('MenuItem.form.name')}
                            fullWidth
                            margin="normal"
                            {...control.register('name')}
                            variant="standard"
                        />
                        <TextField
                            label={t('MenuItem.form.detail')}
                            fullWidth
                            margin="normal"
                            {...control.register('detail')}
                            variant="standard"
                        />
                        <TextField
                            label={t('MenuItem.form.price')}
                            fullWidth
                            margin="normal"
                            type="number"
                            {...control.register('price')}
                            variant="standard"
                        />
                        <FormControl fullWidth>
                            <Controller
                                name="section"
                                control={control}
                                rules={{ required: true }}
                                defaultValue=""
                                render={({ field }) => (
                                    <Select {...field} id="section-select" fullWidth label={t('MenuItem.form.section')}>
                                        {sections.map((option) => (
                                            <MenuItem key={option} value={option}>
                                                {option}
                                            </MenuItem>
                                        ))}
                                    </Select>
                                )}
                            />
                        </FormControl>
                        <TextField
                            label={t('MenuItem.form.image')}
                            fullWidth
                            margin="normal"
                            type="file"
                            {...control.register('image')}
                            variant="standard"
                        />
                        <Button type="submit" variant="contained" color="primary">
                            {t('MenuItem.form.submit')}
                        </Button>
                    </FormControl>
                </MenuItemForm>
            </MenuItemWhiteBoxContainer>
        </MyContainer>
  )
}
