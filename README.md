
# HMedia

It's a Hypermedia-Driven REST demo app (web service) using 
the Spring HATEOAS project. This demo app is only for demonstration 
and/or educational purposes.

HATEOAS (Hypermedia As The Engine Of Application State) 
is a constraint of the REST software architectural style.

### Business case (Context)

Basic manipulations of product (goods) stocks in a warehouse. 
Accepting and shipment of the products (goods).


### Techstack

* [Spring Framework.](https://spring.io/)
* [Spring Data JPA.](https://spring.io/projects/spring-data-jpa)
* [Hibernate.](https://hibernate.org/)
* [Spring HATEOAS.](https://spring.io/projects/spring-hateoas)
* [H2 Database.](https://www.h2database.com/html/main.html)
* [Lombok.](https://projectlombok.org/)


### Database

In IDE (IntelliJ IDEA) run

`src/main/java/com/example/Spring_Boot_HMedia/HMediaApplication.java`.

In IDE console, among other information, should appear

`...: H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:test_db'`

The `test_db` is database for manual testing of the app. It's in in-memory mode.

**To check functionality of the database**, start in Web-browser

`localhost:8080/h2-console`

H2 console login page appears. The page has:

```text

JDBC URL: jdbc:h2:mem:test_db
User Name: root
Password: (empty field)

```

Click button `Connect`. H2 database console should appear. 
This console already has table `PRODUCTS`.

Click on the table `PRODUCTS`. In SQL statement section should appear

```sql
SELECT * FROM PRODUCTS 
```

Click `Run`. Beneath of the SQL statement section appears table `PRODUCTS`. 
It's empty for now.

Data of the table will be changed after each REST-request fulfilled.
You can check it to perform above-mentioned `SELECT` query.


### REST API

**For REST API testing** you can use [Postman](https://www.postman.com/) as a testing tool.

This REST API allows to manipulate with products data in DB:

| Method  | URL                               | Action                    |
|---------|-----------------------------------|---------------------------|
| GET     | `/api/v1/products`                | Get all products list     | 
| GET     | `/api/v1/products/{id}`           | Get a products by id      |
| POST    | `/api/v1/products`                | Add new product           |
| PUT     | `/api/v1/products/{id}`           | Update a product by id    |
| DELETE  | `/api/v1/products/{id}`           | Delete a product by id    |
| PATCH   | `/api/v1/products/{id}/accepts`   | Increase product quantity | 
| PATCH   | `/api/v1/products/{id}/shipments` | Decrease product quantity | 

Let's **get all data**.

Request:
```text
GET http://localhost:8080/api/v1/products
```
Response (there are no data yet):
```json
{
    "_links": {
        "products": {
            "href": "http://localhost:8080/api/v1/products"
        }
    }
}
```

**Add data** to the database.

Request:
```text
POST http://localhost:8080/api/v1/products
```
Request body:
```json
{
  "name": "banana",
  "measure": "kg",
  "stock": 75
}
```
Response:
```json
{
    "id": 1,
    "name": "banana",
    "measure": "kg",
    "stock": 75.0,
    "_links": {
        "collection": {
            "href": "http://localhost:8080/api/v1/products"
        },
        "self": {
            "href": "http://localhost:8080/api/v1/products/1"
        }
    }
}
```

There are **additional data in the Response body**: links to products collection 
and this product itself.

We can try to **get all data again**. Response body content will change:
```json
{
  "_embedded": {
    "productList": [
      {
        "id": 1,
        "name": "banana",
        "measure": "kg",
        "stock": 75.0,
        "_links": {
          "collection": {
            "href": "http://localhost:8080/api/v1/products"
          },
          "self": {
            "href": "http://localhost:8080/api/v1/products/1"
          }
        }
      }
    ]
  },
  "_links": {
    "products": {
      "href": "http://localhost:8080/api/v1/products"
    }
  }
}
```
Also, we can **check data in database table** with H2 console.

We can **add more products** in the database with POST request. 
In this section, different JSON-object is different product 
which is added with a different single POST request:
```text
{
	"name": "mango",
	"measure": "pcs.",
	"stock": 63
}

{
	"name": "kiwi",
	"measure": "box",
	"stock": 32
}

{
	"name": "orange",
	"measure": "kg",
	"stock": 49
}

```

And also **get all** of them
```text
GET http://localhost:8080/api/v1/products
```
**or by specific `id`** (i.o. primary key), e.g. `2`
```text
GET http://localhost:8080/api/v1/products/2
```

**To update** specific product by `id` (here it's `3`) we make 
PUT request in Postman
```text
PUT http://localhost:8080/api/products/3
```
Request body:
```json
{
  "name": "kiwi",
  "measure": "box",
  "stock": 19
}
```

Response body will show changed product data and above-mentioned links 
(to products collection and this product itself).

**Accepting and shipment** of the product changes stock quantity.

Product accepting by specific `id`, e.g. `2`:
```text
PATCH http://localhost:8080/api/v1/products/2/accepts

{
    "quota": 17
}
```

Product shipment by specific `id`, e.g. `2`:
```text
PATCH http://localhost:8080/api/v1/products/2/shipments

{
	"quota": 26
}
```

After REST requests of accepting and shipment of products we will get 
according responses, that show changed product (resource) data and 
above-mentioned links (to products collection and product itself).

**To delete** product by specific `id`, e.g. `4`:
```text
DELETE http://localhost:8080/api/v1/products/4
```

All product (resource) data changes, we **can also see in database table** 
with H2 console.


### Unit-testing

Unit tests of service layer can be found [here](src/test/java/com/example/Spring_Boot_HMedia/service/ProductServiceImplTest.java).

### UML

UML class-diagram image can be found [here](docs/app_class_diagram.png).


### Resources

* https://en.wikipedia.org/wiki/HATEOAS
* https://en.wikipedia.org/wiki/Hypermedia
* https://spring.io/projects/spring-hateoas
* https://www.postman.com/
* https://martinfowler.com/articles/richardsonMaturityModel.html
* https://stateless.group/hal_specification.html

