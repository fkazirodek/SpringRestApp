## Git clone
`https://github.com/fkazirodek/SpringRestApp.git`

## Install
run `mvn install`

## Configuration:
`application.properties`:
```
spring.sql.init.mode=always
spring.jpa.hibernate.ddl-auto=none
spring.jpa.hibernate.use-new-id-generator-mappings=false
```

## Request
### Get list of quotes
`GET /api/quotes`

### Response
```
    HTTP/1.1 200 OK
    Date: Sun, 15 Aug 2021 12:57:54 GMT
    Status: 200 OK
    Connection: keep-alive
    Content-Type: application/json
```
```
[
    {
    "id": 1,
    "version": 1,
    "content": "Wyobraźnia  jest ważniejsza od wiedzy. Wiedza jest ograniczona, a wyobraźnia otacza cały świat.",
    "author": {
        "id": 1,
        "version": 0,
        "firstname": "Albert",
        "lastname": "Einstein"
    }
    }
]
```

## Request
### Save new quote with author
#### (if there is no author in DB, new author will be added)
`POST /api/quotes`

#### request body
```
{
    "id": null,
    "version": null,
    "content": "Człowiek, który w wieku pięćdziesięciu lat widzi świat tak samo, jak widział go mając dwadzieścia lat, zmarnował trzydzieści lat życia",
        "author": {
            "id": null,
            "version": null,
            "firstname": "Muhammad ",
            "lastname": "Ali"
        }
 }
```

### Response
```
    HTTP/1.1 201 Created
    Date: Sun, 15 Aug 2021 12:57:54 GMT
    Status: 201 Created
    Connection: keep-alive
    Content-Type: application/json
```
```
{
    "id": 2,
    "version": 0,
    "content": "Człowiek, który w wieku pięćdziesięciu lat widzi świat tak samo, jak widział go mając dwadzieścia lat, zmarnował trzydzieści lat życia",
        "author": {
            "id": 2,
            "version": 1,
            "firstname": "Muhammad ",
            "lastname": "Ali"
        }
 }
```

## Request
### Update existing quote
`PUT /api/quotes`

#### request body
```
{
    "id": 2,
    "version": 0,
    "content": "UPDATED Człowiek, który w wieku pięćdziesięciu lat widzi świat tak samo, jak widział go mając dwadzieścia lat, zmarnował trzydzieści lat życia",
        "author": {
            "id": 2,
            "version": 0,
            "firstname": "Muhammad ",
            "lastname": "Ali"
        }
 }
```

### Response
```
    HTTP/1.1 200 OK
    Date: Sun, 15 Aug 2021 12:57:54 GMT
    Status: 200 OK
    Connection: keep-alive
    Content-Type: application/json
```
```
{
    "id": 2,
    "version": 1,
    "content": "UPDATED Człowiek, który w wieku pięćdziesięciu lat widzi świat tak samo, jak widział go mając dwadzieścia lat, zmarnował trzydzieści lat życia",
    "author": {
        "id": 2,
        "version": 0,
        "firstname": "Muhammad ",
        "lastname": "Ali"
    }
}
```

## Request
### Delete existing quote
`PUT /api/quotes`

#### request body
```
{
    "id": 2,
    "version": 0,
    "content": "UPDATED Człowiek, który w wieku pięćdziesięciu lat widzi świat tak samo, jak widział go mając dwadzieścia lat, zmarnował trzydzieści lat życia",
        "author": {
            "id": 2,
            "version": 0,
            "firstname": "Muhammad ",
            "lastname": "Ali"
        }
 }
```

### Response
```
    HTTP/1.1 204 No Content
    Date: Sun, 15 Aug 2021 12:57:54 GMT
    Status: 204 No Content
    Connection: keep-alive
```
