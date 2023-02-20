import React from 'react'
import { baseUrl } from '../util/Constants'
import axios from 'axios'

const deleteEmployee = (emplId, token, getEmployees, setEmployees) => {
  axios
    .delete(baseUrl + 'employees/' + emplId, {
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: `Bearer ${token}`,
      },
    })
    .then((response) => {
      console.log(response.data)
      getEmployees(token, setEmployees)
    })
    .catch((error) => {
      console.log(error)
      return false
    })
}

export default deleteEmployee
