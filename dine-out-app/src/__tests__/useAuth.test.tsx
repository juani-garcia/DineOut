import { expect, test, assert } from 'vitest'
import { useAuth, AuthProvider } from '../hooks/auth/useAuth'
import { fireEvent, render, screen } from '@testing-library/react'

const token = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb28uZGluZW91dC5kaW5lckBnbWFpbC5jb20iLCJleHAiOjE3MDMwMTUwMTIsInVzZXJJZCI6MTEsInJvbGVzIjoiRElORVI7In0.JpbSsLKi6yjUV9poTKgA5osDlz38PdvFLEhNjHoIejw'
const userString = '{"sub":"foo.dineout.diner@gmail.com","exp":1703015012,"userId":11,"roles":"DINER;"}'

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
const CustomTest = () => {
  const { logout, setToken, getToken, user } = useAuth()

  return (
      // eslint-disable-next-line react/react-in-jsx-scope
      <div>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <div data-testid="getToken">{JSON.stringify(getToken())}</div>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <div data-testid="user">{JSON.stringify(user)}</div>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <button onClick={() => { setToken(token) }} aria-label="login">
          Login
        </button>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <button onClick={() => {
          logout()
        }} aria-label="logout">
          LogOut
        </button>
      </div>
  )
}

test('Should render initial values', () => {
  render(
      // eslint-disable-next-line react/react-in-jsx-scope
      <AuthProvider>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <CustomTest />
      </AuthProvider>
  )
  assert.include(screen.getAllByTestId('getToken')[0].innerHTML, '')
  assert.include(screen.getAllByTestId('user')[0].innerHTML, '')
})

test('Should Login', () => {
  render(
      // eslint-disable-next-line react/react-in-jsx-scope
      <AuthProvider>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <CustomTest />
      </AuthProvider>
  )
  const loginButton = screen.getAllByRole('button', { name: 'login' })
  fireEvent.click(loginButton[0])
  assert.include(screen.getAllByTestId('getToken')[0].innerHTML, token)
  assert.include(screen.getAllByTestId('user')[0].innerHTML, userString)
})

test('Should Logout', () => {
  render(
      // eslint-disable-next-line react/react-in-jsx-scope
      <AuthProvider>
        {/* eslint-disable-next-line react/react-in-jsx-scope */}
        <CustomTest />
      </AuthProvider>
  )
  const loginButton = screen.getAllByRole('button', { name: 'login' })
  fireEvent.click(loginButton[0])
  assert.include(screen.getAllByTestId('getToken')[0].innerHTML, token)
  assert.include(screen.getAllByTestId('user')[0].innerHTML, userString)

  const logoutButton = screen.getAllByRole('button', { name: 'logout' })
  fireEvent.click(logoutButton[0])
  assert.include(screen.getAllByTestId('getToken')[0].innerHTML, '')
  assert.include(screen.getAllByTestId('user')[0].innerHTML, '')
})
