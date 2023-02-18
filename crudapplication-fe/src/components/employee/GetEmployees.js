import React from 'react'
import axios from 'axios'
import { baseUrl } from '../util/Constants'

const getEmployees = (token, setEmployees) => {
  axios
    .get(baseUrl + 'employees', {
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: `Bearer ${token}`,
      },
    })
    .then((response) => {
      setEmployees(response.data.data)
    })
}

export default getEmployees
