# Quick Start Guide

This guide will help you get the MSR-23 Web Controller up and running in minutes.

## 1. Prerequisites Check

```bash
# Check Python version (should be 3.7+)
python --version

# Check Node.js version (should be 14+)
node --version

# Check npm version
npm --version
```

## 2. Quick Setup

### Backend Setup
```bash
# Clone repository (if you haven't already)
git clone <repository-url>
cd <repository-name>

# Setup Python environment and install dependencies
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
pip install -r requirements.txt

# Configure device IP
# Open app.py and update MODBUS_HOST with your device's IP address
```

### Frontend Setup
```bash
# Install dependencies
npm install
```

## 3. Start the Application

```bash
# Terminal 1 - Start Backend
source venv/bin/activate  # On Windows: venv\Scripts\activate
python app.py

# Terminal 2 - Start Frontend
npm start
```

## 4. Access the Application

1. Open your web browser
2. Go to `http://localhost:3000`
3. You should see the control interface for:
   - Ground Heat Exchanger (GWC)
   - Bypass System (BPS)
   - Heater

## 5. Test Connection

1. Click the "Refresh Status" button
2. Check if the component states are displayed
3. Try changing a component mode
4. Verify the change is reflected on the device

## Troubleshooting

If you encounter issues:

1. **No Connection to Device**
   - Verify device IP address in `app.py`
   - Ensure device is on the same network
   - Check if port 502 is accessible

2. **Frontend Not Loading**
   - Verify `npm install` completed successfully
   - Check browser console for errors
   - Ensure backend is running on port 5000

3. **Backend Errors**
   - Check Python virtual environment is activated
   - Verify all requirements are installed
   - Check terminal for error messages

## Next Steps

1. Read the full README.md for detailed documentation
2. Check the API documentation for custom integration
3. Review security considerations for production deployment 