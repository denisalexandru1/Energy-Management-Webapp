import { Box, Grid } from '@mui/material';
import { useLocation } from "react-router-dom";
import DevicesOfUser from './DevicesOfUser';

export default function UserDashboard(props) {
  const location = useLocation();
  const isAdmin = localStorage.getItem('isAdmin');
  const loggedUserId = localStorage.getItem('loggedUserId');
  const loggedUsername = localStorage.getItem('loggedUsername');

  return (
    ( isAdmin === null) ? (
      <Box sx={{ p: 3 }}>
        <Grid container spacing={2} justifyContent="center" alignItems="center">
          <h1>Access Denied. You are not logged in</h1>
        </Grid>
      </Box>
    ) :
    <Box sx={{ p: 3 }}>
      <Grid container spacing={2} justifyContent="center" alignItems="center" direction="column">
        <h1>Welcome {loggedUsername}</h1>
        <DevicesOfUser userId={loggedUserId}/>
      </Grid>
    </Box>
    )
}