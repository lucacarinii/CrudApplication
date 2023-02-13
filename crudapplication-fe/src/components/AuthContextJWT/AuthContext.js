import React, { createContext, useState } from 'react'

export const AuthContext = createContext()

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(null)
  const [refreshToken, setRefreshToken] = useState(null)

  return (
    <AuthContext.Provider
      value={{ token, setToken, refreshToken, setRefreshToken }}
    >
      {children}
    </AuthContext.Provider>
  )
}
