import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { Header, RedirectionFooter, RegisterForm, RegisterWhiteBoxContainer } from './styles'
import { Button, Checkbox, CircularProgress, FormControl, FormControlLabel, TextField } from '@mui/material'
import { useForm } from 'react-hook-form'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import { useRegister } from '@/hooks/auth/useRegister'
import { useLogin } from '@/hooks/auth/useLogin'
import { useAuth } from '@/hooks/auth/useAuth'
import { useSnackbar } from 'notistack'
import { localPaths } from '@/common/const'

import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'

function Register (): JSX.Element {
  const { t } = useTranslation()

  const schema = z.object({
    firstName: z.string()
      .min(1, t('Register.firstNameEmpty').toString())
      .max(64, t('Register.firstTooLong').toString()),
    lastName: z.string()
      .min(1, t('Register.lastNameEmpty').toString())
      .max(64, t('Register.lastNameTooLing').toString()),
    username: z.string()
      .email(t('Register.invalidUsername').toString())
      .max(64, t('Reguster.usernameTooLong').toString()),
    password: z.string()
      .min(1, t('Register.passwordEmpty').toString())
      .max(64, t('Register.passwordTooLong').toString())
      .refine((password) => {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/
        return regex.test(password)
      }, t('Register.invalidPassword').toString()),
    isRestaurant: z.boolean()
  })

  const { handleSubmit, control, formState: { errors } } = useForm({
    resolver: zodResolver(schema)
  })
  const [confirmPassword, setConfirmPassword] = useState('')
  const [passwordError, setPasswordError] = useState('')
  const { isLoading, register } = useRegister()
  const { isLoading: isLogginIn, login } = useLogin()
  const navigate = useNavigate()
  const { user } = useAuth()
  const location = useLocation()
  const { enqueueSnackbar } = useSnackbar()

  if (user !== null) navigate('/')

  const onRegister = (data: any): void => {
    if (data.password !== confirmPassword) {
      setPasswordError(t('Register.passwordsDoNotMatch').toString())
    } else {
      setPasswordError('')
      console.log(data)
      register(data.firstName, data.lastName, data.username, data.password, confirmPassword, data.isRestaurant)
        .then((response) => {
          if (response.status === 200 || response.status === 201) {
            login(data.username, data.password).then((response) => {
              console.log(user)
              console.log(response)
              if (data.isRestaurant === true) {
                console.log('1')
                navigate(localPaths.RESTAURANTS + '/register', {
                  state: { from: window.location.pathname.replace('/paw-2022a-10', '') + window.location.search }
                })
                return
              }
              if (location.state?.from === null || location.state?.from === '' || location.state?.from === undefined) {
                console.log('2')
                navigate(-1)
              } else {
                navigate(location.state.from, { replace: true })
              }
            }).catch((e) => {
              enqueueSnackbar(t('Errors.oops'), {
                variant: 'error'
              })
            })
          } else {
            enqueueSnackbar(t('Errors.tryAgain'), {
              variant: 'warning'
            })
          }
        }).catch((e: any) => {
          enqueueSnackbar(t('Errors.oops'), {
            variant: 'error'
          })
        })
    }
  }

  return (
        <MyContainer>
            <Title>{t('register')}</Title>
            <RegisterWhiteBoxContainer>
                <RegisterForm>
                    <Header>{t('Register.header')}</Header>
                    {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                    <FormControl component="form" onSubmit={handleSubmit(onRegister)}
                                 sx={{
                                   '& .MuiButton-root': {
                                     width: '40%'
                                   }
                                 }}>
                        <TextField
                            label={t('Register.firstName')}
                            fullWidth
                            margin="normal"
                            {...control.register('firstName')}
                            variant="standard"
                            helperText={errors?.firstName?.message?.toString()}
                        />
                        <TextField
                            label={t('Register.lastName')}
                            fullWidth
                            margin="normal"
                            {...control.register('lastName')}
                            variant="standard"
                            helperText={errors?.lastName?.message?.toString()}
                        />
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
                        <TextField
                            label={t('Register.confirmPassword')}
                            type="password"
                            fullWidth
                            margin="normal"
                            value={confirmPassword}
                            onChange={(e) => {
                              setConfirmPassword(e.target.value)
                            }}
                            variant="standard"
                            error={passwordError !== ''}
                            helperText={passwordError}
                        />
                        <FormControlLabel
                            control={<Checkbox color="secondary" {...control.register('isRestaurant')} />}
                            label={t('Register.isRestaurant')}
                        />
                        <Button type="submit" variant="contained" color="primary">
                            {
                                isLoading || isLogginIn
                                  ? (
                                        <CircularProgress color="secondary" size="30px"/>
                                    )
                                  : (
                                        <>{t('register')}</>
                                    )
                            }
                        </Button>
                    </FormControl>
                    <RedirectionFooter>
                        <h4>{t('Register.redirectionToLogin')}</h4><Link to="/login"><h4>{t('login')}</h4></Link>
                    </RedirectionFooter>
                </RegisterForm>
            </RegisterWhiteBoxContainer>
        </MyContainer>
  )
}

export default Register
