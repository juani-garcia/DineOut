import React from 'react'
import { NeedsLogInContainer } from '@/components/Elements/NeedsLogIn/styles'
import { Button } from '@mui/material'
import { useNavigate } from 'react-router-dom'

function NeedsLogIn (): JSX.Element {
  const navigate = useNavigate()
  return (
        <NeedsLogInContainer>
            <Button onClick={() => {
              navigate('/login')
            }}>Gotologin</Button>
        </NeedsLogInContainer>
  )
}

export default NeedsLogIn
