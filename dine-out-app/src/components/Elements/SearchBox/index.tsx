import React from 'react'
import { useForm } from 'react-hook-form'
import { FormControl, IconButton, InputLabel, MenuItem, Select, TextField } from '@mui/material'
import { SearchBoxContainer, SearchBoxForm } from '@/components/Elements/SearchBox/styles'
import { useTranslation } from 'react-i18next'
import SearchIcon from '@mui/icons-material/Search'
import ClearIcon from '@mui/icons-material/Clear'

function SearchBox (): JSX.Element {
  const { register, handleSubmit, reset, setValue } = useForm()
  const { t } = useTranslation()

  const onSubmit = (data: any): void => {
    // Handle form submission
    console.log(data)
  }

  return (
        <SearchBoxContainer>
            {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
            <SearchBoxForm onSubmit={handleSubmit(onSubmit)}>
                <TextField label={t('SearchBox.search')} {...register('search')} />
                <FormControl sx={{ minWidth: 100 }}>
                    <InputLabel id="categoryLabel">{t('SearchBox.category')}</InputLabel>
                    <Select {...register('category')}
                            defaultValue=""
                            labelId="categoryLabel"
                            id="categorySelect"
                            autoWidth
                            fullWidth={true}
                            label={t('SearchBox.category')}
                    >
                        <MenuItem value="">
                            <em>None</em>
                        </MenuItem>
                        <MenuItem value="Jose Cruz">Jose Cruz</MenuItem>
                    </Select>
                </FormControl>
                <FormControl sx={{ minWidth: 100 }}>
                    <InputLabel id="whereLabel">{t('SearchBox.where')}</InputLabel>
                    <Select {...register('where')}
                            defaultValue=""
                            labelId="whereLabel"
                            id="whereSelect"
                            autoWidth
                            fullWidth={true}
                            label={t('SearchBox.where')}
                    >
                        <MenuItem value="">
                            <em>None</em>
                        </MenuItem>
                        <MenuItem value="4">4</MenuItem>
                        <MenuItem value="5">5</MenuItem>
                    </Select>
                </FormControl>
                <FormControl sx={{ minWidth: 100 }}>
                    <InputLabel id="whenLabel">{t('SearchBox.when')}</InputLabel>
                    <Select {...register('when')}
                            defaultValue=""
                            labelId="whenLabel"
                            id="whenSelect"
                            autoWidth
                            fullWidth={true}
                            label={t('SearchBox.when')}
                    >
                        <MenuItem value="">
                            <em>None</em>
                        </MenuItem>
                        <MenuItem value="6">6</MenuItem>
                        <MenuItem value="7">7</MenuItem>
                    </Select>
                </FormControl>
                <IconButton type="submit" color="primary" aria-label="search">
                    <SearchIcon/>
                </IconButton>
                <IconButton onClick={() => { // TODO: Fix this
                  reset()
                  setValue('category', '')
                  setValue('where', '')
                  setValue('when', '6')
                }} color="secondary" aria-label="clear">
                    <ClearIcon/>
                </IconButton>
            </SearchBoxForm>
        </SearchBoxContainer>
  )
}

export default SearchBox
