import React from 'react'
import { MyContainer, Title } from '@/components/Elements/utils/styles'
import { useTranslation } from 'react-i18next'
import { LoginForm, LoginWhiteBoxContainer, RedirectionFooter } from './styles'
import { Button, FormControl, TextField } from '@mui/material'
import { useForm } from 'react-hook-form'
import { useLogin } from '@/hooks/auth/useLogin'
import { Link, useNavigate } from 'react-router-dom'

function Login (): JSX.Element {
  const { t } = useTranslation()

  const { handleSubmit, control } = useForm()
  const { login } = useLogin()
  const navigate = useNavigate()

  const onLogin = (data: any): void => {
    console.log('Submitted values:', data)
    login(data.username, data.password).then((response) => {
      navigate(-1)
    }).catch((e) => {
      console.log(e.data) // TODO: Handle
    })
  }

  return (
        <MyContainer>
            <Title>{t('login')}</Title>
            <LoginWhiteBoxContainer>
                <LoginForm>
                    {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                    <FormControl component="form" onSubmit={handleSubmit(onLogin)}
                                 sx={{
                                   '& .MuiButton-root': {
                                     width: '40%',
                                     marginTop: '25px'
                                   }
                                 }}>
                        <TextField
                            label={t('email')}
                            fullWidth
                            margin="normal"
                            {...control.register('username')}
                            variant="standard"
                        />
                        <TextField
                            label={t('password')}
                            type="password"
                            fullWidth
                            margin="normal"
                            {...control.register('password')}
                            variant="standard"
                        />
                        <Button type="submit" variant="contained" color="primary">
                            {t('login')}
                        </Button>
                    </FormControl>
                </LoginForm>
                <RedirectionFooter>
                    <Link to="/forgot_my_password"><h4>{t('forgot-password')}</h4></Link>
                    <Link to="/register"><h4>{t('register')}</h4></Link>
                </RedirectionFooter>
            </LoginWhiteBoxContainer>
        </MyContainer>
  )
}

export default Login
