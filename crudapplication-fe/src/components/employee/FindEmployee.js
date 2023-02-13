import React, { useState, useContext } from 'react'
import axios from 'axios'
import Button from '@mui/material/Button'
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Avatar from '@mui/material/Avatar'
import { TextField } from '@mui/material'
import { Link } from 'react-router-dom'
import { baseUrl } from '../util/Constants'
import SearchIcon from '@mui/icons-material/Search'
import { AuthContext } from '../AuthContextJWT/AuthContext'
import DynamicTable from '../DynamicTable'

const FindEmployee = () => {
  const [emplId, setEmplId] = useState('')
  const [name, setName] = useState('')
  const [surname, setSurname] = useState('')
  const [email, setEmail] = useState('')
  const [idRole, setIdRole] = useState('')
  const [employees, setEmployees] = useState([])
  const { token, setToken } = useContext(AuthContext)

  const findEmpoyees = (event) => {
    event.preventDefault()

    axios
      .post(
        baseUrl + 'employee',
        {
          emplId: !emplId || emplId.length === 0 ? '-1' : emplId,
          name: name,
          surname: surname,
          email: email,
          idRole: !idRole || idRole.length === 0 ? '-1' : idRole,
        },
        {
          headers: {
            'Access-Control-Allow-Origin': '*',
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then((response) => {
        setEmployees(response.data.data)
      })
      .catch((error) => {
        console.log(error.response.config)
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
            <SearchIcon />
          </Avatar>
          <h2>Find Employee</h2>
        </Grid>
        <form onSubmit={findEmpoyees}>
          <TextField
            style={{ marginTop: '15px' }}
            label="Employee ID"
            placeholder="Write the Employee ID"
            value={emplId}
            onChange={(e) => setEmplId(e.target.value)}
            fullWidth
          ></TextField>
          <TextField
            style={{ marginTop: '15px' }}
            label="Name"
            placeholder="Write the employee's name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            fullWidth
          ></TextField>
          <TextField
            style={{ marginTop: '15px' }}
            label="Surname"
            placeholder="Write your surname"
            value={surname}
            onChange={(e) => setSurname(e.target.value)}
            fullWidth
          ></TextField>
          <TextField
            style={{ marginTop: '15px' }}
            label="Email"
            placeholder="Write the employee's email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            fullWidth
          ></TextField>
          <TextField
            style={{ marginTop: '15px' }}
            label="ID Role"
            placeholder="Write the employee's ID role"
            value={idRole}
            onChange={(e) => setIdRole(e.target.value)}
            fullWidth
            type="text"
          ></TextField>
          <Button
            style={{ marginTop: '15px' }}
            type="submit"
            color="primary"
            fullWidth
            variant="contained"
          >
            Find
          </Button>
        </form>
        <Grid align="center">
          <Link to="/homepage">
            <Button
              style={{ marginTop: '15px' }}
              type="submit"
              color="primary"
              fullWidth
              variant="contained"
            >
              Homepage
            </Button>
          </Link>
        </Grid>
      </Paper>
      {!employees || employees.length === 0 ? (
        <></>
      ) : (
        <DynamicTable link="/homepage" data={employees} hpButton={false} />
      )}
    </Grid>
  )
}

export default FindEmployee
