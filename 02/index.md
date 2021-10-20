# Domáca úloha 2
## HTTP Application Protocol - telnet, openssl, cURL
V [súbore HW2_Terminal Saved Output.txt](HW2_Terminal%20Saved%20Output.txt) sa nachádza kompletný log z príkazového riadku. 
V nasledujúcich bodoch sú vypísané requesty, ktoré boli zasielané.
### Http GET request
    telnet 147.32.233.18 8888
    GET /NI-AM1-ApplicationProtocols/httpTelnet1 HTTP/1.1
    Host: 147.32.233.18
    User-Agent: fit-telnet
    Accept: text/html
    Accept-Language: en-US
    Accept-Charset: UTF-8

### Http POST request
    telnet 147.32.233.18 8888
    POST /NI-AM1-ApplicationProtocols/httpTelnet2 HTTP/1.1
    Host: 147.32.233.18
    Referer: mi-mdw
    Content-Type: application/x-www-form-urlencoded;  charset=UTF-8
    Content-Length: 8
    
    data=fit

### cURL
#### 1 
    curl http://147.32.233.18:8888/NI-AM1-ApplicationProtocols/protocol/welcome
#### 2
    curl --header "Accept: text/plain" -v "http://147.32.233.18:8888/NI-AM1-ApplicationProtocols/protocol/get?name=publicly"
#### 3
    curl --data "" --header "Accept: text/plain" --header "Accept-Language: en-US" -v "http://147.32.233.18:8888/NI-AM1-ApplicationProtocols/protocol/post?name=consumer" 
#### 4
    curl --data "" --header "Accept: text/html" --header "Accept-Language: en-US" --header "Referer: discriminate" -v "http://147.32.233.18:8888/NI-AM1-ApplicationProtocols/protocol/referer"
#### 5
    curl --data "" --header "Accept: text/html" --header "Accept-Language: en-US" --header "User-Agent: resumed" -v "http://147.32.233.18:8888/NI-AM1-ApplicationProtocols/protocol/useragent"
#### 6
    curl --data "" --header "Accept: text/html" --header "Accept-Language: en-US" --cookie "name=pets" -v "http://147.32.233.18:8888/NI-AM1-ApplicationProtocols/protocol/cookie" 
#### 7
    curl --data "" --header "Accept: text/html" --header "Accept-Language: en-US" --user mouse:ancient -v "http://147.32.233.18:8888/NI-AM1-ApplicationProtocols/protocol/auth" 

### cURL finálna odpoveď
journal
