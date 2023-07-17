import { MyContainer, Title } from '@/components/Elements/utils/styles'
import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { RegisterWhiteBoxContainer, RegisterForm, Header, RedirectionFooter } from './styles'
import { Button, Checkbox, FormControl, FormControlLabel, TextField } from '@mui/material'
import { useForm } from 'react-hook-form'
import { Link } from 'react-router-dom'
import { useRegister } from '@/hooks/auth/useRegister'

function Register (): JSX.Element {
    const { t } = useTranslation()
    const { handleSubmit, control } = useForm()
    const [confirmPassword, setConfirmPassword] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const {register} = useRegister()

    const onRegister = (data: any): void => {
        if (data.password !== confirmPassword) {
            setPasswordError('Passwords do not match');
            return;
          } else {
            setPasswordError('');
            register(data.firstName, data.lastName, data.username, data.password, confirmPassword, data.isRestaurant)
            .then((response: any) => {
                console.log(response)
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
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        variant="standard"
                        error={passwordError !== ''}
                        helperText={passwordError}
                    />
                    <FormControlLabel
                        control={<Checkbox color="secondary" {...control.register('isRestaurant')} />}
                        label={t('Register.isRestaurant')}
                    />
                    <Button type="submit" variant="contained" color="primary">
                        {t('register')}
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
