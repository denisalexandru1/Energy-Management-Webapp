import React, { useState } from 'react';
import { Box, Grid } from '@mui/material';
import UserLoginPanel from './UserLoginPanel';
import { useNavigate } from 'react-router-dom';

function LogInHome() {
  localStorage.clear();
  const [loggedIn, setLoggedIn] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [loggedUsername, setLoggedUsername] = useState('');
  const [loggedUserId, setLoggedUserId] = useState('');
  const navigate = useNavigate();

  function handleLogin() {
    setLoggedIn(true);
  }

  function handleSetIsAdmin(isAdmin) {
    setIsAdmin(isAdmin);
  }

  function handleSetLoggedUserId(id) {
    setLoggedUserId(id);
  }

  function handleSetLoggedUsername(username) {
    setLoggedUsername(username);
  }

  if (!loggedIn) {
    return (
      <Box sx={{ p: 3 }}>
        <Grid container spacing={2} justifyContent="center" alignItems="center">
          <UserLoginPanel setLoggedIn={handleLogin} setIsAdmin={handleSetIsAdmin} setLoggedUserId={handleSetLoggedUserId} setLoggedUsername={handleSetLoggedUsername}/>
        </Grid>
      </Box>
    );
  }
  
  localStorage.setItem('isAdmin', isAdmin);
  localStorage.setItem('loggedUserId', loggedUserId);
  localStorage.setItem('loggedUsername', loggedUsername);
  const path = isAdmin ? "/admin-view" : "/user-view";

  navigate(path)

}

export default LogInHome;