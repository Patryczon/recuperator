# Decompiled MSR-23 Controller Files

This directory contains the decompiled source code of the MSR-23 Controller Android application. The application is designed to control an MSR-23 device via Modbus TCP protocol over WiFi.

## File Overview

### Core Application Files

#### MainActivity.java (719 lines)
The main controller of the application that handles:
- Modbus TCP communication (port 502)
- Device status monitoring
- Real-time updates
- UI state management
- Component control (GWC, BPS, Heater)

Key features:
- Socket-based communication
- Periodic status polling
- Error handling and reconnection logic
- Temperature display
- Mode switching (Auto/Manual/Vent)

#### ConfigNetworkActivity.java (104 lines)
Handles network configuration:
- Local IP configuration
- Static IP configuration
- Device password/ID setup
- Network settings persistence
- Connection validation

### Component Configuration Activities

#### GwcConfigActivity.java (87 lines)
Ground Heat Exchanger (GWC) configuration:
- Operating modes:
  - OFF (0)
  - ON (1)
  - AUTO (2)
  - AUTO_R (3) - Regulated Auto mode
- Comfort settings
- Temperature thresholds

#### BpsConfigActivity.java (70 lines)
Bypass System (BPS) configuration:
- Operating modes:
  - OFF (0)
  - ON (1)
  - AUTO (2)
- Window dimensions: 800x1100
- Position settings: x=0, y=-200

#### HeaterConfigActivity.java (86 lines)
Heater control configuration:
- Operating modes:
  - OFF (0)
  - HE (1) - Heater Electric
  - HW (2) - Hot Water
  - HC (3) - Heating Control
- Temperature control
- Mode switching logic

#### DinConfigActivity.java (145 lines)
Digital Input configuration:
- Input/Output settings
- Signal processing
- State management
- Configuration persistence

#### SetupControllerActivity.java (270 lines)
Main setup and configuration hub:
- Component initialization
- Parameter configuration
- System status display
- Navigation between component settings
- Configuration data transfer

### Support Files

#### BuildConfig.java (12 lines)
Build configuration constants:
- Version information
- Debug flags
- Application ID
- Build type

#### R.java (2957 lines)
Auto-generated resource index:
- Layout IDs
- String resources
- Drawable resources
- View IDs
- Style definitions

## Communication Protocol

The application uses Modbus TCP protocol with the following characteristics:
- Port: 502 (default Modbus TCP port)
- Function codes:
  - 03: Read Holding Registers
  - 06: Write Single Register
  - 16: Write Multiple Registers

Register mapping:
- GWC Control: Register 4
- BPS Control: Register 12
- Heater Control: Register 17

## Security Notes

1. Network Communication:
   - Plain TCP/IP communication
   - No encryption on Modbus level
   - Password converted to numeric ID
   - Should be used in secure networks only

2. Data Storage:
   - Settings stored in SharedPreferences
   - Network configuration persistence
   - No encryption for stored data

## Device Interaction

The application maintains constant communication with the device:
- 500ms polling interval
- Automatic reconnection on failure
- Status monitoring
- Error detection and handling
- Component state synchronization

## UI Components

The interface provides:
- Real-time temperature displays
- Mode selection buttons
- Status indicators
- Configuration menus
- Error notifications
- Connection status

## Error Handling

The application implements several error handling mechanisms:
- Network connection monitoring
- Communication timeout detection
- Invalid data handling
- User feedback through toast messages
- Automatic reconnection attempts

## Development Notes

These files were obtained through decompilation and may:
- Lack original comments
- Have generated variable names
- Miss some original code structure
- Contain decompiler artifacts

For development purposes, refer to the original documentation and protocol specifications when making modifications. 