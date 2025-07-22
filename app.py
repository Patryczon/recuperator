from flask import Flask, jsonify, request
from flask_cors import CORS
from pymodbus.client import ModbusTcpClient
from pymodbus.exceptions import ModbusException

app = Flask(__name__)
CORS(app)  # Enable CORS for all routes

# Modbus client configuration
MODBUS_HOST = "192.168.1.100"  # Replace with your device IP
MODBUS_PORT = 502
client = ModbusTcpClient(MODBUS_HOST, port=MODBUS_PORT)

# Register addresses
REGISTERS = {
    'gwc': 4,    # GWC control register
    'bps': 12,   # BPS control register
    'heat': 17,  # Heater control register
}

# Device modes
GWC_MODES = {
    'off': 0,
    'on': 1,
    'auto': 2,
    'auto_r': 3
}

BPS_MODES = {
    'off': 0,
    'on': 1,
    'auto': 2
}

HEATER_MODES = {
    'off': 0,
    'he': 1,
    'hw': 2,
    'hc': 3
}

@app.route('/api/status', methods=['GET'])
def get_status():
    try:
        # Read multiple registers
        result = client.read_holding_registers(0, 20)  # Read first 20 registers
        if result.isError():
            return jsonify({'error': 'Failed to read registers'}), 400
            
        return jsonify({
            'gwc_mode': result.registers[REGISTERS['gwc']],
            'bps_mode': result.registers[REGISTERS['bps']],
            'heat_mode': result.registers[REGISTERS['heat']]
        })
    except ModbusException as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/control/<component>', methods=['POST'])
def control_component(component):
    try:
        data = request.get_json()
        mode = data.get('mode')
        
        if component not in REGISTERS:
            return jsonify({'error': 'Invalid component'}), 400
            
        # Validate mode based on component
        if component == 'gwc' and mode not in GWC_MODES:
            return jsonify({'error': 'Invalid GWC mode'}), 400
        elif component == 'bps' and mode not in BPS_MODES:
            return jsonify({'error': 'Invalid BPS mode'}), 400
        elif component == 'heat' and mode not in HEATER_MODES:
            return jsonify({'error': 'Invalid heater mode'}), 400
            
        # Write to appropriate register
        register = REGISTERS[component]
        mode_value = globals()[f'{component.upper()}_MODES'][mode]
        result = client.write_register(register, mode_value)
        
        if result.isError():
            return jsonify({'error': 'Failed to write to register'}), 400
            
        return jsonify({'success': True, 'mode': mode})
        
    except ModbusException as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True) 