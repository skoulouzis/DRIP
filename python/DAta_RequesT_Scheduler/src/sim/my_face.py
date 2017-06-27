import json
import os
import socket
import sys
import time
from pyndn import Name
from pyndn import Data
from pyndn import Interest

class MyFace(object):
    
    def __init__(self, is_server):
        self.is_server = is_server
        self.interest_list = []
        self.server_address = '/tmp/unix_socket'
        
        if self.is_server:
            try:
                os.unlink(self.server_address)
            except OSError:
                if os.path.exists(self.server_address):
                    raise             
            if os.path.exists(self.server_address):
                os.remove(self.server_address)
            
            self.sock = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
            self.sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
            self.sock.bind(self.server_address)
        else:
            self.sock = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
            self.sock.connect(self.server_address)
    
    def processEvents(self):
        if self.is_server:
            self.rec()

        
    
    def expressInterest(self, interest, _onData, _onTimeout):
        self._onTimeout = _onTimeout
        self.sock.sendall(interest)
        data = self.sock.recv(1024)
        amount_received = 0
        if data:
            amount_received += len(data)
            _onData(interest, data)
        
   
    
    def rec(self):        
        self.sock.listen(1)
        while True:
            connection, client_address = self.sock.accept()
            try:
                # Receive the data in small chunks and retransmit it
                while True:
                    data = connection.recv(1024)
                    if data:
                        data = json.loads(data)
                        name = Name(data['name'])
                        if self.prefix.getPrefix( self.prefix.size()).equals(name.getPrefix(-1)):
                            interest = Interest(name)   
                            self.onInterest(self.prefix, interest, connection, "registeredPrefixId")
                    else:
                        break

            finally:
                # Clean up the connection
                connection.close()
                
            
        
    def registerPrefix(self, prefix, onInterest, onRegisterFailed):
        self.prefix = prefix
        self.onInterest = onInterest
        self.onRegisterFailed = onRegisterFailed
        
        