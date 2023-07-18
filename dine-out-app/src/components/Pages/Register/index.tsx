import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { Header, RedirectionFooter, RegisterForm, RegisterWhiteBoxContainer } from './styles'
import { Button, Checkbox, CircularProgress, FormControl, FormControlLabel, TextField } from '@mui/material'
import { useForm } from 'react-hook-form'
import { Link, useNavigate } from 'react-router-dom'
import { useRegister } from '@/hooks/auth/useRegister'
import { useLogin } from '@/hooks/auth/useLogin'
import { useAuth } from '@/hooks/auth/useAuth'

function Register (): JSX.Element {
  const { t } = useTranslation()
  const { handleSubmit, control } = useForm()
  const [confirmPassword, setConfirmPassword] = useState('')
  const [passwordError, setPasswordError] = useState('')
  const { isLoading, register } = useRegister()
  const { isLoading: isLogginIn, login } = useLogin()
  const navigate = useNavigate()
  const { user } = useAuth()

  if (user !== null) navigate(-1)

  const onRegister = (data: any): void => {
    if (data.password !== confirmPassword) {
      setPasswordError('Passwords do not match')
    } else {
      setPasswordError('')
      register(data.firstName, data.lastName, data.username, data.password, confirmPassword, data.isRestaurant)
        .then((response) => {
          if (response.status === 200 || response.status === 201) {
            login(data.username, data.password).then((response) => {
              navigate(-1)
            }).catch((e) => {
              console.log(e.data)
            })
          } // TODO: Handle error on else
        }).catch((e: any) => {
          console.log(e)
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
                        />
                        <TextField
                            label={t('Register.lastName')}
                            fullWidth
                            margin="normal"
                            {...control.register('lastName')}
                            variant="standard"
                        />
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
