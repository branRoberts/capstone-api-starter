# EasyShop API

## Description
EasyShop is a Java REST API built with Spring Boot for an online store.
It handles categories, products, and shopping cart functionality.
The project involved implementing missing features, fixing bugs, and
building out the shopping cart from scratch.

## Features
- Browse and filter products by category, price, and color
- Admin can add, update, and delete categories
- Search returns all matching products (bug fixed)
- Product stock updates correctly when edited (bug fixed)
- Users can add items to their cart, update quantities, and clear their cart

## Phases Completed
- Phase 1 - Categories: implemented CategoriesController and CategoryService
- Phase 2 - Bug Fixes: fixed product search and stock update bugs
- Phase 3 - Shopping Cart: built out cart functionality from scratch

## Demo
![insomnia.gif](images/insomnia_demo.gif)
![easyshop_demo.gif](images/easyshop_demo.gif)
## Setup

### Prerequisites
- IntelliJ IDEA
- Java 17
- MySQL

### Running the Application
1. Open the project in IntelliJ
2. Run `create_database_easyshop.sql` in MySQL Workbench
3. Update `application.properties` with your MySQL credentials
4. Run `ECommerceApplication.java`
5. Use Insomnia or the frontend client to test

## Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- Maven

## Thanks
- Raymond 
- [Java Visual Learning Hub](https://raymaroun.github.io/yearup-java-visuals/)