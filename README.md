# socketbridge

A simple program who creates a bridge between two sockets connections. Each new connection is a new thread who attach a new socket client to send and receive the packets

## Parameters in .ini file

|  Parameter | Description                                                  |
|------------|--------------------------------------------------------------|
| APP_PORT   | Port who the application will open with a new connection     |
| SERVER_IP  | Sever to wich the application will send and receive the data |
| SERVER_PORT| Server tcp port                                              |



