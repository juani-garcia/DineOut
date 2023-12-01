import React from 'react'
import { MyContainer, Title } from '@/components/Elements/utils/styles'
import { useTranslation } from 'react-i18next'
import { LinkTo, LoginForm, LoginWhiteBoxContainer, RedirectionFooter } from './styles'
import { Button, FormControl, TextField } from '@mui/material'
import { useForm } from 'react-hook-form'
import { useLogin } from '@/hooks/auth/useLogin'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '@/hooks/auth/useAuth'
import { HttpStatusCode } from 'axios'
import { useSnackbar } from 'notistack'

import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'

function Login (): JSX.Element {
  const { t } = useTranslation()

  const schema = z.object({
    username: z.string()
      .max(64, t('Login.usernameTooLong').toString())
      .email(t('Login.invalidUsername').toString()),
    password: z.string()
      .min(1, t('Login.passwordTooShort').toString())
      .max(64, t('Login.passwordTooLong').toString())
  })

  const { handleSubmit, control, formState: { errors } } = useForm({
    resolver: zodResolver(schema)
  })
  const { login } = useLogin()
  const navigate = useNavigate()
  const location = useLocation()
  const { user } = useAuth()
  const { enqueueSnackbar } = useSnackbar()

  if (user !== null) navigate(-1)

  const onLogin = (data: any): void => {
    login(data.username, data.password).then((response) => {
      if (response.status === HttpStatusCode.Accepted || response.status === HttpStatusCode.Ok) {
        if (location.state?.from === null || location.state?.from === '' || location.state?.from === undefined) {
          navigate('/')
        } else {
          navigate(location.state.from, { replace: true })
        }
      } else {
        enqueueSnackbar(t('Errors.invalidCredentials'), {
          variant: 'warning'
        })
      }
    }).catch((e) => {
      enqueueSnackbar(t('Errors.oops'), {
        variant: 'error'
      })
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
                            helperText={errors?.username?.message?.toString()}
                        />
                        <TextField
                            label={t('password')}
                            type="password"
                            fullWidth
                            margin="normal"
                            {...control.register('password')}
                            variant="standard"
                            helperText={errors?.password?.message?.toString()}
                        />
                        <Button type="submit" variant="contained" color="primary">
                            {t('login')}
                        </Button>
                    </FormControl>
                </LoginForm>
                <RedirectionFooter>
                    <LinkTo as={Link} to="/forgot_my_password">{t('forgot-password')}</LinkTo>
                    <LinkTo as={Link} to="/register">{t('register')}</LinkTo>
                </RedirectionFooter>
            </LoginWhiteBoxContainer>
        </MyContainer>
  )
}

export default Login
