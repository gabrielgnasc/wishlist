
### Rotas

| Descrição                                | Método | URL                                           |
|------------------------------------------|--------|-----------------------------------------------|
| Inserir um produto na wishlist           | POST   | /wishlist/{customerId}/products               |
| Remover um produto da wishlist           | DELETE | /wishlist/{customerId}/products/{productId}   |
| Buscar todos produtos da wishlist        | GET    | /wishlist/{customerId}/products               |
| Verificar se um produto está na wishlist | GET    | /wishlist/{customerId}?product_id={productId} |

