
### Executando os testes:
```bash 
./mvnw test
```

### Executando a aplicação:

#### Necesário:
- Docker
- docker-compose

#### Comando:
```bash 
docker-compose up --build
```
#### Links:
- [Api](http://localhost:8082/)
- [Api-Doc (Swagger)](http://localhost:8082/swagger-ui.html)
- [Prometheus](http://localhost:9090/)
- [Grafana](http://localhost:3000/)  [user= admin, password= admin]

#### Observações:
A api está utilizando um load Balancing com nginx,
existem 3 réplicas para a aplicação.
demais configurações podem ser encontradas em:

| configuração               | arquivo                                                |
|----------------------------|--------------------------------------------------------|
| Dockerfile para spring     | [backend.Dockerfile](./Dockerfiles/backend.Dockerfile) |
| load balancing nginx       | [nginx](./Dockerfiles/nginx.conf)                      |
| configuração do prometheus | [prometheus.yml](./Dockerfiles/prometheus.yml)         |


### Rotas

| Descrição                                | Método | URL                                           |
|------------------------------------------|--------|-----------------------------------------------|
| Inserir um produto na wishlist           | POST   | /wishlist/{customerId}/products               |
| Remover um produto da wishlist           | DELETE | /wishlist/{customerId}/products/{productId}   |
| Buscar todos produtos da wishlist        | GET    | /wishlist/{customerId}/products               |
| Verificar se um produto está na wishlist | GET    | /wishlist/{customerId}?product_id={productId} |

