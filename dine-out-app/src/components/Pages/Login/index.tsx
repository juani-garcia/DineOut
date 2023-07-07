import React from 'react'
import { MyContainer, Title, WhiteBoxContainer } from '../../Elements/utils/styles'
import { useTranslation } from 'react-i18next'
import { LoginForm } from './styles'
import { Button, Checkbox, FormControl, FormControlLabel, TextField } from '@mui/material'
import { useForm } from 'react-hook-form'
import { useLogin } from '@/hooks/auth/useLogin'

function Login (): JSX.Element {
  const { t } = useTranslation()

  const { handleSubmit, control } = useForm()
  const { login } = useLogin()

  const onLogin = (data: any): void => {
    console.log('Submitted values:', data)
    login(data.username, data.password).then((response) => {
      console.log('BOCAAAAAAAAAAAAAAAAAAAa')
      console.log(response)
    }).catch((e) => {
      console.log('ta to mal flaco')
      console.log(e.data)
    })
  }

  return (
        <MyContainer>
            <Title>{t('login')}</Title>
            <WhiteBoxContainer>
                <LoginForm>
                    {/* eslint-disable-next-line @typescript-eslint/no-misused-promises */}
                    <FormControl component="form" onSubmit={handleSubmit(onLogin)}>
                        <TextField
                            label={t('Login.email')}
                            fullWidth
                            margin="normal"
                            {...control.register('username')}
                        />
                        <TextField
                            label={t('Login.password')}
                            type="password"
                            fullWidth
                            margin="normal"
                            {...control.register('password')}
                        />
                        <FormControlLabel
                            control={<Checkbox {...control.register('rememberMe')} />}
                            label={t('Login.rememberMe')}
                        />
                        <Button type="submit" variant="contained" color="primary">
                            {t('login')}
                        </Button>
                    </FormControl>
                </LoginForm>
            </WhiteBoxContainer>
        </MyContainer>
  )
}

export default Login
