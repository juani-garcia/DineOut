import React from 'react'
import { ErrorTwoTone } from '@mui/icons-material'
import { ErrorContainer } from '@/components/Pages/Error/styles'

interface ErrorProps {
  error: string
}

function Error ({ error }: ErrorProps): JSX.Element {
  if (error == null || error === '') error = '404'
  return (
        <ErrorContainer>
            <ErrorTwoTone sx={{ transform: 'scale(20.5)' }} color={'error'}/>{error}
        </ErrorContainer>
  )
}

export default Error
