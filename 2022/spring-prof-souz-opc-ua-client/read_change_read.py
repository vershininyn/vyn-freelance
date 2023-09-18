import websocket
import json
import sys

if len(sys.orig_argv) != 3:
   exit('The user must specify host address')

ws = websocket.create_connection('ws://'+ sys.orig_argv[2] +':8081')

def send_json(ws_connection, msg, log_msg):
    ws_connection.send(json.dumps(msg))
    response = ws_connection.recv()
    
    response_data = json.loads(response)
    print(log_msg)
    print(response_data)

msg = {
    'Header': {
      'MessageType':'READ_REQUEST',
      'ClientHandle':'1'
    },
    'Body': { 'Variable' : 'UInt32'}
 }

send_json(ws, msg, 'FIRST_READ_REUEST')

msg = {
   'Header': {
    'ClientHandle': '1',
    'MessageType': 'WRITE_REQUEST'
   },
   'Body': {
     'Variable': 'UInt32',
     'Value': {
       'Value': {
         'Body': '555',
         'Type': '8'
       }
     }
   }
} 

send_json(ws, msg, 'WRITE_REQUEST')

msg = {
    'Header': {
      'MessageType':'READ_REQUEST',
      'ClientHandle':'1'
    },
    'Body': { 'Variable' : 'UInt32'}
 }

send_json(ws, msg, 'SECOND_WRITE_REQUEST')

