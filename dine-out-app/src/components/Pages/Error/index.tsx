import React from 'react'
import { ErrorTwoTone } from '@mui/icons-material'
import { ErrorContainer } from '@/components/Pages/Error/styles'
import { useSearchParams } from 'react-router-dom'

function Error (): JSX.Element {
  const [queryParams] = useSearchParams()
  let error = queryParams.get('status')
  if (error == null || error === '') error = '404'
  return (
        <ErrorContainer>
            <ErrorTwoTone sx={{ transform: 'scale(20.5)' }} color={'error'}/>{error}
        </ErrorContainer>
  )
}

export default Error
