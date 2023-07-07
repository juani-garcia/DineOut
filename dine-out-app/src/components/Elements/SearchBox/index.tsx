import React from 'react'
import { useForm } from 'react-hook-form'
import { Button, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material'
import { WhiteBoxContainer } from '@/components/Elements/utils/styles'

function SearchBox (): JSX.Element {
  const { register, handleSubmit } = useForm()

  const onSubmit = (data: any): void => {
    // Handle form submission
    console.log(data)
  }

  return (
        <WhiteBoxContainer>
            {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
            <form onSubmit={handleSubmit(onSubmit)}>
                <TextField label="Text" {...register('text')} /> {/* Text input field */}
                <FormControl>
                    <InputLabel>Select 1</InputLabel>
                    <Select {...register('select1')}>
                        <MenuItem value="1">1</MenuItem>
                        <MenuItem value="2">2</MenuItem>
                        <MenuItem value="3">3</MenuItem>
                    </Select>
                </FormControl>
                <FormControl>
                    <InputLabel>Select 2</InputLabel>
                    <Select {...register('select2')}>
                        <MenuItem value="4">4</MenuItem>
                        <MenuItem value="5">5</MenuItem>
                    </Select>
                </FormControl>
                <FormControl>
                    <InputLabel>Select 3</InputLabel>
                    <Select {...register('select3')}>
                        <MenuItem value="6">6</MenuItem>
                        <MenuItem value="7">7</MenuItem>
                    </Select>
                </FormControl>
                <Button type="submit" variant="contained" color="primary">
                    Submit
                </Button>
            </form>
        </WhiteBoxContainer>
  )
}

export default SearchBox
