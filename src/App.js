import React, { useState, useEffect } from 'react';
import {
  Container,
  Card,
  CardContent,
  Typography,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Grid,
  Button,
} from '@mui/material';
import axios from 'axios';

const API_URL = 'http://localhost:5000/api';

const COMPONENTS = {
  gwc: {
    name: 'Ground Heat Exchanger (GWC)',
    modes: ['off', 'on', 'auto', 'auto_r'],
  },
  bps: {
    name: 'Bypass (BPS)',
    modes: ['off', 'on', 'auto'],
  },
  heat: {
    name: 'Heater',
    modes: ['off', 'he', 'hw', 'hc'],
  },
};

function App() {
  const [status, setStatus] = useState({
    gwc_mode: 0,
    bps_mode: 0,
    heat_mode: 0,
  });

  const fetchStatus = async () => {
    try {
      const response = await axios.get(`${API_URL}/status`);
      setStatus(response.data);
    } catch (error) {
      console.error('Error fetching status:', error);
    }
  };

  useEffect(() => {
    fetchStatus();
    const interval = setInterval(fetchStatus, 5000); // Poll every 5 seconds
    return () => clearInterval(interval);
  }, []);

  const handleModeChange = async (component, mode) => {
    try {
      await axios.post(`${API_URL}/control/${component}`, { mode });
      fetchStatus(); // Refresh status after change
    } catch (error) {
      console.error(`Error setting ${component} mode:`, error);
    }
  };

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        MSR-23 Controller
      </Typography>
      
      <Grid container spacing={3}>
        {Object.entries(COMPONENTS).map(([key, component]) => (
          <Grid item xs={12} md={4} key={key}>
            <Card>
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  {component.name}
                </Typography>
                
                <FormControl fullWidth>
                  <InputLabel>Mode</InputLabel>
                  <Select
                    value={status[`${key}_mode`]}
                    onChange={(e) => handleModeChange(key, component.modes[e.target.value])}
                  >
                    {component.modes.map((mode, index) => (
                      <MenuItem value={index} key={mode}>
                        {mode.toUpperCase()}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      <Button
        variant="contained"
        color="primary"
        onClick={fetchStatus}
        sx={{ mt: 3 }}
      >
        Refresh Status
      </Button>
    </Container>
  );
}

export default App; 