# MSR-23 Modbus Protocol Summary

## Basic Information
- Protocol: Modbus TCP
- Default Port: 502
- Poll Interval: 500ms
- Input Buffer Size: 213 bytes

## Register Map

### Control Registers
| Register | Component | Description | Values |
|----------|-----------|-------------|---------|
| 4  | GWC | Ground Heat Exchanger | 0=OFF, 1=ON, 2=AUTO, 3=AUTO_R |
| 12 | BPS | Bypass System | 0=OFF, 1=ON, 2=AUTO |
| 17 | Heat | Heater Control | 0=OFF, 1=HE, 2=HW, 3=HC |
| 37 | Mode | Operation Mode | 0=Manual, 1=Vent, 2=Auto |
| 50 | Fireplace | Fireplace Mode | 0=OFF, 1=ON |

### Status Registers
| Register | Description | Data Type |
|----------|-------------|----------|
| 76 | Fan Speed | UINT8 |
| 82 | Operating Mode | UINT8 |
| 95-96 | Tz Temperature | INT16 |
| 97-98 | Tg Temperature | INT16 |
| 99-100 | Tw Temperature | INT16 |
| 101-102 | Tn Temperature | INT16 |
| 108 | Fireplace Status | UINT8 |

## Message Structure

### Read Request (Function 03)
```
[0-4]  : 0x00 (5 bytes header)
[5]    : 0x06 (length)
[6]    : Controller ID
[7]    : 0x03 (function code)
[8]    : 0x00
[9]    : Register address
[10-11]: Register count
```

### Write Request (Function 06)
```
[0-4]  : 0x00 (5 bytes header)
[5]    : 0x06 (length)
[6]    : Controller ID
[7]    : 0x06 (function code)
[8]    : 0x00
[9]    : Register address
[10]   : Value high byte
[11]   : Value low byte
```

### Write Multiple Request (Function 16)
```
[0-4]  : 0x00 (5 bytes header)
[5]    : Length ((reg_count * 2) + 7)
[6]    : Controller ID
[7]    : 0x10 (function code)
[8]    : 0x00
[9]    : Register address
[10]   : 0x00
[11]   : Register count
[12]   : Byte count (reg_count * 2)
[13+]  : Data bytes
```

## Error Handling

### Error Codes
| Code | Description |
|------|-------------|
| 1 | Function Error |
| 2 | Register Error |
| 3 | Data Error |

### Error Response Format
```
[5]    : 0x03
[7]    : Function Code + 0x80
[8]    : Error Code
```

## Temperature Data

All temperature values are stored as INT16 with 0.1°C resolution:
- Range: -50.0°C to 60.0°C
- Storage: Value * 10 (e.g., 23.5°C = 235)
- Invalid reading: Values outside -500 to 600

## Controller ID Calculation

The controller ID is derived from the password:
1. Sum ASCII values of password characters
2. Divide by 6
3. Result is used as controller ID in Modbus messages

## Connection Management

- Connection timeout: 10 polls (5 seconds)
- Reconnection attempt: After 15 timeouts
- Maximum error count: 100
- Online status check: Every 500ms
- Error reset on successful communication 