import React, { useState } from 'react'
import axios from 'axios'
import Button from '@mui/material/Button'
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Avatar from '@mui/material/Avatar'
import LockIcon from '@mui/icons-material/Lock'
import { TextField } from '@mui/material'
import { Link, useNavigate } from 'react-router-dom'

function Login() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const navigate = useNavigate()

  const baseUrl = 'http://0.0.0.0:8080/'

  const loginUser = (event) => {
    event.preventDefault()
    axios
      .post(
        baseUrl + 'login',
        {
          email: email,
          password: password,
        },
        {
          headers: {
            'Access-Control-Allow-Origin': '*',
          },
        }
      )
      .then((response) => {
        localStorage.setItem('token', response.data.data)
        localStorage.setItem('email', email)
        navigate('/homepage')
      })
  }

  return (
    <Grid>
      <Paper
        elevation={10}
        style={{
          padding: 10,
          height: '70vh',
          width: 380,
          margin: '20px auto',
        }}
      >
        <Grid align="center">
          <Avatar style={{ backgroundColor: 'green' }}>
            <LockIcon />
          </Avatar>
          <h2>Register</h2>
        </Grid>
        <form onSubmit={loginUser}>
          <TextField
            style={{ marginTop: '15px' }}
            label="Email"
            placeholder="Write your name"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            fullWidth
            required
          ></TextField>
          <TextField
            style={{ marginTop: '15px' }}
            label="Password"
            placeholder="Write your password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            fullWidth
            required
            type="password"
          ></TextField>
          <Button
            style={{ marginTop: '15px' }}
            type="submit"
            color="primary"
            fullWidth
            variant="contained"
          >
            Login
          </Button>
        </form>
        <Grid align="center">
          <Paper style={{ marginTop: '15px' }} elevation={0}>
            Don't you have an account? <Link to="/">Register</Link>
          </Paper>
        </Grid>
      </Paper>
    </Grid>
  )
}

export default Login
