# AM1 - Úloha 7
## Restful - Conditional GET

Úlohou bolo navrhnúť a implementovať webovú služby na získanie zoznamu zájazdov (Tours).
Služba má podporovať podmienené GET (Conditional GET) požiadavky.
Preto je potrebné implementovať hlavičky Last-Modified a ETag.

Weak ETag je MD5 hash vypočítaný podľa zoznamu zájazdov. Strong ETag je MD5 hash vypočítaný podľa zoznamu zájazdov s účastníkmi.
Ak sa ETag nezmení od poslednej požiadavky, je odoslaná odpoveď 304 NOT MODIFIED.
### Java triedy
1. **Tour** - reprezentuje jeden zájazd, obsahuje id, názov a zoznam zákazníkov
2. **Customer** - zákazník, obsahuje meno a priezvisko
3. **WebServiceRepository** - repozitár všetkých zájazdov, obsahuje navyše informáciu o poslednej úprave
4. **WebServiceController** - REST controller, obsahuje metódy:
   1. getToursWeak - zoznam zájazdov, kontroluje weak ETag
   2. getToursStrong - zoznam zájazdov, kontroluje strong ETag
   3. getToursLastModified - zoznam zájazdov, ktorý kontroluje čas poslednej zmeny
   4. addTour - pridanie zájazdu do repozitára
   5. addCustomer - pridanie zákazníka do zájazdu
   

### Overenie funkčnosti pomocou cURL
Log z testovania sa nachádza v [súbore](Terminal_Saved_Output_Uloha_7).

Získanie zoznamu všetkých zájazdov:

` curl http://localhost:8080/tours -v
`
```
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /tours HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
>
< HTTP/1.1 200
< Last-Modified: Mon, 29 Nov 2021 13:02:41 GMT
< ETag: "W/F91448960449F57298DE6784793B6E90"
< Content-Type: application/hal+json
< Transfer-Encoding: chunked
< Date: Mon, 29 Nov 2021 13:02:51 GMT
<
* Connection #0 to host localhost left intact
  {"_embedded":{"tourList":[{"id":1,"name":"Tour1","customers":[{"name":"Ondrej","surname":"Jarina"}],"_links":{"ADD":{"href":"http://localhost:8080/tours/1/add-customer"}}}]},"_links":{"LIST":[{"href":"http://localhost:8080/tours/strong"},{"href":"http://localhost:8080/tours/last-modified"}],"ADD":{"href":"http://localhost:8080/tours/add-tour"}}}* Closing connection 0
```
V hlavičke ETag sa nachádza weak ETag. Po jeho odoslaní v hlavičke If-None-Match pri opakovanej požiadavke dostaneme odpoveď 304, pretože žiadna zmena neprebehla.

`curl http://localhost:8080/tours -H "If-None-Match: W/F91448960449F57298DE6784793B6E90" -v
`
```
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /tours HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> If-None-Match: W/F91448960449F57298DE6784793B6E90
>
< HTTP/1.1 304
< ETag: "W/F91448960449F57298DE6784793B6E90"
< Last-Modified: Mon, 29 Nov 2021 13:02:41 GMT
< Date: Mon, 29 Nov 2021 13:03:03 GMT
<
* Connection #0 to host localhost left intact
* Closing connection 0
```

Ak sa pokúsime odoslať rovnaký weak eTag do metódy, ktorá očakáva strong eTag, dostaneme odpoveď 200, pretože eTagy sa nezhodujú

`curl http://localhost:8080/tours/strong -H "If-None-Match: W/F91448960449F57298DE6784793B6E90" -v
`
````*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /tours/strong HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> If-None-Match: W/F91448960449F57298DE6784793B6E90
>
< HTTP/1.1 200
< Last-Modified: Mon, 29 Nov 2021 13:02:41 GMT
< ETag: "D28A7024117839E963CC9AF9DAA1DDFF"
< Content-Type: application/hal+json
< Transfer-Encoding: chunked
< Date: Mon, 29 Nov 2021 13:03:18 GMT
<
* Connection #0 to host localhost left intact
  {"_embedded":{"tourList":[{"id":1,"name":"Tour1","customers":[{"name":"Ondrej","surname":"Jarina"}],"_links":{"ADD":{"href":"http://localhost:8080/tours/1/add-customer"}}}]},"_links":{"LIST":[{"href":"http://localhost:8080/tours"},{"href":"http://localhost:8080/tours/last-modified"}],"ADD":{"href":"http://localhost:8080/tours/add-tour"}}}* Closing connection 0
````
Strong eTag navyše neobsahuje prefix W/.

Pomocou hlavičky If-Modified-Since vieme skontrolovať, či od určitého času prebehla nejaká zmena.

`curl http://localhost:8080/tours/last-modified -H "If-Modified-Since: Mon, 29 Nov 2021 13:02:41 GMT" -v
`
```
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /tours/last-modified HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> If-Modified-Since: Mon, 29 Nov 2021 13:02:41 GMT
>
< HTTP/1.1 304
< Last-Modified: Mon, 29 Nov 2021 13:02:41 GMT
< Date: Mon, 29 Nov 2021 13:04:20 GMT
<
* Connection #0 to host localhost left intact
* Closing connection 0

```
#### Pridanie nového zájazdu (Tour)

`url -X POST http://localhost:8080/tours/add-tour -H "Content-Type: application/json" -d '{
"id": 12,
"name": "Polsko"
}' -v`

```*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> POST /tours/add-tour HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> Content-Type: application/json
> Content-Length: 38
>
* upload completely sent off: 38 out of 38 bytes
  < HTTP/1.1 200
  < Content-Type: application/hal+json
  < Transfer-Encoding: chunked
  < Date: Mon, 29 Nov 2021 13:04:33 GMT
  <
* Connection #0 to host localhost left intact
  {"id":12,"name":"Polsko","customers":[],"_links":{"LIST":[{"href":"http://localhost:8080/tours"},{"href":"http://localhost:8080/tours/strong"},{"href":"http://localhost:8080/tours/last-modified"}],"ADD":{"href":"http://localhost:8080/tours/12/add-customer"}}}* Closing connection 0
```

Zmenil sa weak eTag

`curl http://localhost:8080/tours -H "If-None-Match: W/F91448960449F57298DE6784793B6E90" -v
`
```
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /tours HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> If-None-Match: W/F91448960449F57298DE6784793B6E90
>
< HTTP/1.1 200
< Last-Modified: Mon, 29 Nov 2021 13:04:33 GMT
< ETag: "W/75AA0EB9B65194548904078F2D9D3DE7"
< Content-Type: application/hal+json
< Transfer-Encoding: chunked
< Date: Mon, 29 Nov 2021 13:04:51 GMT
<
* Connection #0 to host localhost left intact
  {"_embedded":{"tourList":[{"id":1,"name":"Tour1","customers":[{"name":"Ondrej","surname":"Jarina"}],"_links":{"ADD":{"href":"http://localhost:8080/tours/1/add-customer"}}},{"id":12,"name":"Polsko","customers":[],"_links":{"ADD":{"href":"http://localhost:8080/tours/12/add-customer"}}}]},"_links":{"LIST":[{"href":"http://localhost:8080/tours/strong"},{"href":"http://localhost:8080/tours/last-modified"}],"ADD":{"href":"http://localhost:8080/tours/add-tour"}}}* Closing connection 0
  ondrej@ntb-o ~ % curl http://localhost:8080/tours/strong -H "If-None-Match: D28A7024117839E963CC9AF9DAA1DDFF" -v
```
Zmenil sa strong eTag

`curl http://localhost:8080/tours/strong -H "If-None-Match: D28A7024117839E963CC9AF9DAA1DDFF" -v 
` 
```
* Connected to localhost (::1) port 8080 (#0)
> GET /tours/strong HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> If-None-Match: D28A7024117839E963CC9AF9DAA1DDFF
>
< HTTP/1.1 200
< Last-Modified: Mon, 29 Nov 2021 13:04:33 GMT
< ETag: "C446CDF277049AB65EEC98FF63626D26"
< Content-Type: application/hal+json
< Transfer-Encoding: chunked
< Date: Mon, 29 Nov 2021 13:05:35 GMT
<
* Connection #0 to host localhost left intact
  {"_embedded":{"tourList":[{"id":1,"name":"Tour1","customers":[{"name":"Ondrej","surname":"Jarina"}],"_links":{"ADD":{"href":"http://localhost:8080/tours/1/add-customer"}}},{"id":12,"name":"Polsko","customers":[],"_links":{"ADD":{"href":"http://localhost:8080/tours/12/add-customer"}}}]},"_links":{"LIST":[{"href":"http://localhost:8080/tours"},{"href":"http://localhost:8080/tours/last-modified"}],"ADD":{"href":"http://localhost:8080/tours/add-tour"}}}* Closing connection 0
```

Zmenil sa čas Last-Modified 

`curl http://localhost:8080/tours/last-modified -H "If-Modified-Since: Mon, 29 Nov 2021 13:02:41 GMT" -v 
`
```* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /tours/last-modified HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> If-Modified-Since: Mon, 29 Nov 2021 13:02:41 GMT
>
< HTTP/1.1 200
< Last-Modified: Mon, 29 Nov 2021 13:04:33 GMT
< Content-Type: application/hal+json
< Transfer-Encoding: chunked
< Date: Mon, 29 Nov 2021 13:05:40 GMT
<
* Connection #0 to host localhost left intact
  {"_embedded":{"tourList":[{"id":1,"name":"Tour1","customers":[{"name":"Ondrej","surname":"Jarina"}],"_links":{"ADD":{"href":"http://localhost:8080/tours/1/add-customer"}}},{"id":12,"name":"Polsko","customers":[],"_links":{"ADD":{"href":"http://localhost:8080/tours/12/add-customer"}}}]},"_links":{"LIST":[{"href":"http://localhost:8080/tours/strong"},{"href":"http://localhost:8080/tours"}],"ADD":{"href":"http://localhost:8080/tours/add-tour"}}}* Closing connection 0
```

#### Pridanie nového zákazníka (Customer) do zájazdu (Tour)

`curl -X POST http://localhost:8080/tours/12/add-customer -H "Content-Type: application/json" -d '{
"name": "Prvy",
"surname": "Testovatel"
}' -v`


```
* Connected to localhost (::1) port 8080 (#0)
> POST /tours/12/add-customer HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> Content-Type: application/json
> Content-Length: 51
>
* upload completely sent off: 51 out of 51 bytes
  < HTTP/1.1 200
  < Content-Type: application/hal+json
  < Transfer-Encoding: chunked
  < Date: Mon, 29 Nov 2021 13:12:18 GMT
  <
* Connection #0 to host localhost left intact
  {"id":12,"name":"Polsko","customers":[{"name":"Prvy","surname":"Testovatel"}],"_links":{"LIST":[{"href":"http://localhost:8080/tours"},{"href":"http://localhost:8080/tours/strong"},{"href":"http://localhost:8080/tours/last-modified"}],"ADD":{"href":"http://localhost:8080/tours/add-tour"}}}* Closing connection 0
```

Weak eTag ostal rovnaký = Odpoveď 304

`curl http://localhost:8080/tours -H "If-None-Match: W/75AA0EB9B65194548904078F2D9D3DE7" -v
`
```
* Connected to localhost (::1) port 8080 (#0)
> GET /tours HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> If-None-Match: W/75AA0EB9B65194548904078F2D9D3DE7
>
< HTTP/1.1 304
< ETag: "W/75AA0EB9B65194548904078F2D9D3DE7"
< Last-Modified: Mon, 29 Nov 2021 13:12:18 GMT
< Date: Mon, 29 Nov 2021 13:13:01 GMT
<
* Connection #0 to host localhost left intact
* Closing connection 0
```

Strong eTag sa zmenil

`curl http://localhost:8080/tours/strong -H "If-None-Match: D28A7024117839E963CC9AF9DAA1DDFF" -v
`
```* Connected to localhost (::1) port 8080 (#0)
> GET /tours/strong HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> If-None-Match: D28A7024117839E963CC9AF9DAA1DDFF
>
< HTTP/1.1 200
< Last-Modified: Mon, 29 Nov 2021 13:12:18 GMT
< ETag: "0470CA9B777784D74CD474A989091EC9"
< Content-Type: application/hal+json
< Transfer-Encoding: chunked
< Date: Mon, 29 Nov 2021 13:13:40 GMT
<
* Connection #0 to host localhost left intact
  {"_embedded":{"tourList":[{"id":1,"name":"Tour1","customers":[{"name":"Ondrej","surname":"Jarina"}],"_links":{"ADD":{"href":"http://localhost:8080/tours/1/add-customer"}}},{"id":12,"name":"Polsko","customers":[{"name":"Prvy","surname":"Testovatel"}],"_links":{"ADD":{"href":"http://localhost:8080/tours/12/add-customer"}}}]},"_links":{"LIST":[{"href":"http://localhost:8080/tours"},{"href":"http://localhost:8080/tours/last-modified"}],"ADD":{"href":"http://localhost:8080/tours/add-tour"}}}* Closing connection 0
```

Zmenil sa aj Last-Modified

`curl http://localhost:8080/tours/last-modified -H "If-Modified-Since: Mon, 29 Nov 2021 13:10:41 GMT" -v
`

```* Connected to localhost (::1) port 8080 (#0)
> GET /tours/last-modified HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> If-Modified-Since: Mon, 29 Nov 2021 13:10:41 GMT
>
< HTTP/1.1 200
< Last-Modified: Mon, 29 Nov 2021 13:12:18 GMT
< Content-Type: application/hal+json
< Transfer-Encoding: chunked
< Date: Mon, 29 Nov 2021 13:14:19 GMT
<
* Connection #0 to host localhost left intact
  {"_embedded":{"tourList":[{"id":1,"name":"Tour1","customers":[{"name":"Ondrej","surname":"Jarina"}],"_links":{"ADD":{"href":"http://localhost:8080/tours/1/add-customer"}}},{"id":12,"name":"Polsko","customers":[{"name":"Prvy","surname":"Testovatel"}],"_links":{"ADD":{"href":"http://localhost:8080/tours/12/add-customer"}}}]},"_links":{"LIST":[{"href":"http://localhost:8080/tours/strong"},{"href":"http://localhost:8080/tours"}],"ADD":{"href":"http://localhost:8080/tours/add-tour"}}}* Closing connection 0
```