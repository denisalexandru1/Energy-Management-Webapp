import React, { useState, useEffect } from 'react';
import { Box, Grid } from '@mui/material';
import { useLocation } from "react-router-dom";
import DevicesOfUser from './DevicesOfUser';
import ChatComponent from './ChatComponent';

export default function UserDashboard(props) {
  const location = useLocation();
  const isAdmin = localStorage.getItem('isAdmin');
  const loggedUserId = localStorage.getItem('loggedUserId');
  const loggedUsername = localStorage.getItem('loggedUsername');
  
  // Use state to manage the admins array
  const [admins, setAdmins] = useState([]);

  useEffect(() => {
    // Fetch admins when the component mounts
    fetchAdmins();
  }, []);

  const fetchAdmins = () => {
    fetch('http://localhost:8080/user/admins', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem('jwtToken')
      }
    })
      .then(res => res.json())
      .then(data => {
        setAdmins(data);
      })
      .catch(err => {
        console.log(err);
      });
  };

  return (
    (isAdmin === null) ? (
      <Box sx={{ p: 3 }}>
        <Grid container spacing={2} justifyContent="center" alignItems="center">
          <h1>Access Denied. You are not logged in</h1>
        </Grid>
      </Box>
    ) : (
      <Box sx={{ p: 3 }}>
        <Grid container spacing={2} justifyContent="center" alignItems="center" direction="column">
          <h1>Welcome {loggedUsername}</h1>
          <DevicesOfUser userId={loggedUserId}/>
          <ChatComponent senderId={loggedUserId} receiverId={admins[0]?.uuid} />
        </Grid>
      </Box>
    )
  );
}
