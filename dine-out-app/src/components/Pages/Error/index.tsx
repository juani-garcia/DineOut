import React from 'react'
import { ErrorTwoTone } from '@mui/icons-material'
import { ErrorContainer } from '@/components/Pages/Error/styles'
import { useSearchParams } from 'react-router-dom'

interface ErrorProps {
  errorProp?: number
}

function Error ({ errorProp }: ErrorProps): JSX.Element { // TODO: add err msg
  const [queryParams] = useSearchParams()
  let error = queryParams.get('status')
  if (error == null || error === '') {
    if (errorProp !== null && errorProp !== undefined) {
      error = errorProp.toString()
    } else {
      error = '404'
    }
  }
  return (
        <ErrorContainer>
            <ErrorTwoTone sx={{ transform: 'scale(20.5)' }} color={'error'}/>{error}
        </ErrorContainer>
  )
}

export default Error
