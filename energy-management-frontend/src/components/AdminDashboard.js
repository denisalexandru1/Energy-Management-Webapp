import React, { useState, useEffect } from 'react';
import { Box, Grid } from '@mui/material';
import { useLocation } from "react-router-dom";
import UsersOperations from './UsersOperationsPanel';
import DevicesOperations from './DevicesOperationsPanel';


export default function AdminDashboard(props) {
  const location = useLocation();

  //get isAdmin from localStorage
  const isAdmin = localStorage.getItem('isAdmin');
  console.log(isAdmin);

  return (
    ( isAdmin === 'false' || isAdmin === null) ? (
      <Box sx={{ p: 3 }}>
        <Grid container spacing={2} justifyContent="center" alignItems="center">
          <h1>Access Denied. You are not an admin</h1>
        </Grid>
      </Box>
    ) :
      <Box sx={{ p: 3 }}>
        <Grid container spacing={2} justifyContent="center" alignItems="center">
          <UsersOperations/>
          <DevicesOperations/>
        </Grid>
      </Box>
  );
}